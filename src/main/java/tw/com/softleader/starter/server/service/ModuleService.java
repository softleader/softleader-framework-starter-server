package tw.com.softleader.starter.server.service;

import static java.util.Objects.*;
import static java.util.stream.Collectors.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import tw.com.softleader.commons.function.Tests;
import tw.com.softleader.commons.function.Unchecked;
import tw.com.softleader.data.dao.CrudDao;
import tw.com.softleader.domain.AbstractCrudService;
import tw.com.softleader.starter.server.dao.ModuleDao;
import tw.com.softleader.starter.server.entity.Module;
import tw.com.softleader.starter.server.entity.Source;
import tw.com.softleader.starter.server.enums.IDE;
import tw.com.softleader.starter.server.enums.Wizard;
import tw.com.softleader.starter.server.io.Component;
import tw.com.softleader.starter.server.io.Datasource;
import tw.com.softleader.starter.server.io.Pom;
import tw.com.softleader.starter.server.io.SnippetSource;
import tw.com.softleader.starter.server.io.WebApplicationInitializer;
import tw.com.softleader.starter.server.pojo.Snippet;

@Slf4j
@Service
public class ModuleService extends AbstractCrudService<Module, Long> {

  @Value("${source.root}")
  private String sourceRoot;

  @Value("${ignore.file.names}")
  private String ignores;

  private Collection<String> ignoreFileNames;

  @PostConstruct
  public void init() {
    if (ignores != null) {
      ignoreFileNames = Arrays.asList(ignores.split(","));
    }
  }

  @Autowired
  private ModuleDao dao;

  public Map<ZipArchiveEntry, InputStream> collectSnippets(Wizard wizard, Snippet snippet)
      throws JsonProcessingException {
    List<Module> snippets = dao.findByWizardAndArtifactIsNull(wizard);
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

  class ArchiveEntries {

    private final Snippet starter;
    private final Collection<Module> snippets;
    private final Function<String, String> contentFormatter;
    private final Function<String, String> pathFormatter;

    ArchiveEntries(Snippet starter, Collection<Module> snippets) {
      super();
      this.starter = requireNonNull(starter, "starter");
      this.snippets = requireNonNull(snippets, "snippets");
      this.starter.getProject().collectGlobalInfo(this.snippets);
      this.contentFormatter = new SnippetSource(this.starter);
      this.pathFormatter =
          this.contentFormatter.andThen(path -> FilenameUtils.normalize(path, true));
    }

    Map<ZipArchiveEntry, InputStream> collect() {
      Map<ZipArchiveEntry, InputStream> archives = new HashMap<>();

      starter.getProject().getDirs().stream().map(contentFormatter).forEach(dir -> {
        String entryName = pathFormatter.apply(dir + "/");
        if (!starter.getIde().getFileFilter().test(entryName)) {
          return;
        }
        archives.put(new ZipArchiveEntry(entryName), null);
        log.debug("Added dir [{}] to zip", entryName);
      });

      snippets.stream().map(Module::getSources).flatMap(Collection::stream).map(src -> {
        src.setRoot(sourceRoot);
        return src;
      }).forEach(Unchecked.accept(src -> collect(archives, src, starter.getIde())));

      return archives;
    }

    private void collect(Map<ZipArchiveEntry, InputStream> archives, Source src, IDE ide)
        throws IOException, ClassNotFoundException, URISyntaxException {
      Path path = Paths.get(src.getFullPath());
      if (Files.notExists(path)) {
        throw new NoSuchFileException("[" + src.getFullPath() + "] is not a exist path");
      }
      if (!Files.isReadable(path)) {
        throw new IllegalStateException("[" + src.getFullPath() + "] is not a readable path");
      }
      if (Files.isDirectory(path)) {
        collectRecursive(archives, src.getFullPath(), path, ide);
      } else {
        String entryName = pathFormatter
            .apply(requireNonNull(src.getEntryName(), "Source [" + src.getModule().getArtifact()
                + "] - [" + src.getPath() + "] did not determine entryName"));
        if (!ide.getFileFilter().test(entryName)) {
          return;
        }
        readContent(path).ifPresent(content -> {
          archives.put(new ZipArchiveEntry(entryName), content);
          log.debug("Added file [{}] to zip", entryName);
        });
      }
    }

    private void collectRecursive(Map<ZipArchiveEntry, InputStream> archives, String root,
        Path path, IDE ide) throws IOException {
      if (Files.isDirectory(path)) {
        if (Files.list(path).iterator().hasNext()) {
          Files.list(path).forEach(Unchecked.accept(p -> collectRecursive(archives, root, p, ide)));
        } else {
          String absolutePath = path.toAbsolutePath().toString() + "/";
          log.trace("Removing '{}' in file path: [{}]", root, absolutePath);
          String entryName = pathFormatter.apply(absolutePath).replace(root, "");
          if (!ide.getFileFilter().test(entryName)) {
            return;
          }
          archives.put(new ZipArchiveEntry(entryName), null);
          log.debug("Added empty dir [{}] to zip", entryName);
        }
      } else {
        String absolutePath = path.toAbsolutePath().toString();
        log.trace("Removing '{}' in file path: [{}]", root, absolutePath);
        String entryName = pathFormatter.apply(absolutePath).replace(root, "");
        if (!ide.getFileFilter().test(entryName)) {
          return;
        }
        readContent(path).ifPresent(content -> {
          archives.put(new ZipArchiveEntry(entryName), content);
          log.debug("Added file [{}] to zip", entryName);
        });
      }
    }

    private Optional<ByteArrayInputStream> readContent(Path path) throws IOException {
      Function<String, String> converter = contentFormatter;
      if (ignoreFileNames.contains(path.getFileName().toString())) {
        return Optional.empty();
      } else if (path.getFileName().endsWith("pom.xml")) {
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
        return Optional.of(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
      } catch (Exception e) {
        throw new RuntimeException("Reading [" + path.getFileName() + "] faild", e);
      }
    }

  }

}
