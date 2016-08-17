package tw.com.softleader.starter.server.service;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import tw.com.softleader.commons.function.Tests;
import tw.com.softleader.commons.function.Unchecked;
import tw.com.softleader.data.dao.CrudDao;
import tw.com.softleader.domain.AbstractCrudService;
import tw.com.softleader.starter.server.dao.ModuleDao;
import tw.com.softleader.starter.server.entity.Module;
import tw.com.softleader.starter.server.io.Component;
import tw.com.softleader.starter.server.io.Datasource;
import tw.com.softleader.starter.server.io.Pom;
import tw.com.softleader.starter.server.io.SnippetSource;
import tw.com.softleader.starter.server.io.WebApplicationInitializer;
import tw.com.softleader.starter.server.pojo.Snippet;

@Slf4j
@Service
public class ModuleService extends AbstractCrudService<Module, Long> {

  @Autowired
  private ModuleDao dao;

  public Map<ZipArchiveEntry, InputStream> collectSnippets(Snippet snippet)
      throws JsonProcessingException {
    List<Module> snippets = dao.findByArtifactIsNull();
    log.debug("Found {} global snippets", snippets.size());

    Set<Module> selected = snippet.getDependencies().stream()
        .flatMap(d -> dao.findByArtifact(d.getGroupId() + ":" + d.getArtifactId()).stream())
        .filter(Tests.not(Objects::isNull)).collect(toSet());

    log.debug("Found {} selected snippets", selected.size());

    Set<Module> requires = selected.stream().map(Module::getRequires).flatMap(Collection::stream)
        .filter(Tests.not(Objects::isNull))
        .filter(require -> selected.stream().map(Module::getArtifact)
            .noneMatch(artifact -> artifact.equals(require)))
        .map(dao::findByArtifact).flatMap(Collection::stream).collect(toSet());

    log.debug("Found {} requires snippets", requires.size());

    snippets.addAll(selected);
    snippets.addAll(requires);

    return new ArchiveEntries(snippet, snippets).collect();
  }

  @Override
  public CrudDao<Module, Long> getDao() {
    return dao;
  }

  static class ArchiveEntries {

    private final Snippet starter;
    private final Collection<Module> snippets;
    private final Function<String, String> formatter;

    ArchiveEntries(Snippet starter, Collection<Module> snippets) {
      super();
      this.starter = requireNonNull(starter, "starter");
      this.snippets = requireNonNull(snippets, "snippets");
      this.starter.getProject().collectGlobalInfo(this.snippets);
      this.formatter = new SnippetSource(this.starter);
    }

    Map<ZipArchiveEntry, InputStream> collect() {
      Map<ZipArchiveEntry, InputStream> archives = new HashMap<>();

      starter.getProject().getDirs().stream().map(formatter).forEach(dir -> {
        String entryName = formatter.apply(dir + "/");
        entryName = FilenameUtils.normalize(entryName, true);
        archives.put(new ZipArchiveEntry(entryName), null);
        log.debug("Added dir [{}] to zip", entryName);
      });

      snippets.stream().map(Module::getSnippets).flatMap(Collection::stream)
          .forEach(Unchecked.accept(src -> collect(archives, src)));

      return archives;
    }

    private void collect(Map<ZipArchiveEntry, InputStream> archives, String src)
        throws IOException, ClassNotFoundException, URISyntaxException {
      Path path = Paths.get(src);
      if (Files.notExists(path)) {
        throw new NoSuchFileException("[" + src + "] is not a exist path");
      }
      if (!Files.isReadable(path)) {
        throw new IllegalStateException("[" + src + "] is not a readable path");
      }
      if (Files.isDirectory(path)) {
        collectRecursive(archives, src, path);
      } else {
        String entryName = formatter.apply(path.getFileName().toString());
        entryName = FilenameUtils.normalize(entryName, true);
        archives.put(new ZipArchiveEntry(entryName), readContent(path));
        log.debug("Added file [{}] to zip", entryName);
      }
    }

    private void collectRecursive(Map<ZipArchiveEntry, InputStream> archives, String root,
        Path path) throws IOException {
      if (Files.isDirectory(path)) {
        if (Files.list(path).iterator().hasNext()) {
          Files.list(path).forEach(Unchecked.accept(p -> collectRecursive(archives, root, p)));
        } else {
          String entryName =
              formatter.apply(path.toAbsolutePath().toString().replace(root, "") + "/");
          entryName = FilenameUtils.normalize(entryName, true);
          archives.put(new ZipArchiveEntry(entryName), null);
          log.debug("Added empty dir [{}] to zip", entryName);
        }
      } else {
        String entryName = formatter.apply(path.toAbsolutePath().toString().replace(root, ""));
        entryName = FilenameUtils.normalize(entryName, true);
        archives.put(new ZipArchiveEntry(entryName), readContent(path));
        log.debug("Added file [{}] to zip", entryName);
      }
    }

    private ByteArrayInputStream readContent(Path path) throws IOException {
      Function<String, String> converter = formatter;
      if (path.getFileName().endsWith("pom.xml")) {
        converter = converter.compose(new Pom(starter));
      } else if (path.getFileName().endsWith("WebApplicationInitializer.java")) {
        converter = converter.compose(new WebApplicationInitializer(starter));
      } else if (path.getFileName().endsWith(".component")) {
        converter = converter.compose(new Component(starter));
      } else if (path.getFileName().endsWith("datasource.properties")) {
        converter = converter.compose(new Datasource(starter));
      }
      try {
        String content = Files.readAllLines(path).stream().collect(joining("\n"));
        content = converter.apply(content);
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
      } catch (Exception e) {
        throw new RuntimeException("Reading [" + path.getFileName() + "] faild", e);
      }
    }

  }

}
