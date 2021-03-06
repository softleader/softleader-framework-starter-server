package tw.com.softleader.starter.server.service;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tw.com.softleader.commons.collect.Lists;
import tw.com.softleader.commons.compress.ArchiveStream;
import tw.com.softleader.starter.server.config.ApplicationConfig;
import tw.com.softleader.starter.server.entity.Module;
import tw.com.softleader.starter.server.enums.Wizard;
import tw.com.softleader.starter.server.pojo.*;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@WithMockUser("matt")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
public class ModuleServiceTest {

  Snippet starter;

  @Autowired
  private ModuleService snippetService;

  @Before
  public void setUp() {
    starter = new Snippet();
    ProjectDetails project = new ProjectDetails();
    project.setName("starter-server-test");
    project.setPkg("tw.com.softleader.starter.server.test");
    project.setGroupId("tw.com.softleader");
    project.setArtifactId("starter-server-test");
    project.setVersion("0.0.1-SNAPSHOT");
    starter.setProject(project);
    starter.setVersion(new Version("1.1.0.SNAPSHOT", "2.0.3.RELEASE"));
    Database database = new Database();
    database.setName("postgres");
    database.setDriverClass("org.postgresql.Driver");
    database.setUrl("jdbc:postgresql://softleader.com.tw:5432/i519Payment");
    database.setUsername("sa");
    starter.setDatabase(database);
    starter.setDependencies(
            Lists.newArrayList(new Dependency("tw.com.softleader", "softleader-data-jpa")));
  }

  @Test
  public void testCollectSnippets() throws FileNotFoundException, IOException, ArchiveException {
    Map<ZipArchiveEntry, InputStream> archives = snippetService.collectSnippets(Wizard.WEBAPP, starter);
    File archive = new File("/Users/Matt/temp/test.zip");
    ArchiveStream.of(new FileOutputStream(archive)).compress(archives);
    System.out.println(archive.getPath() + " created!");
    System.out.println(archive.exists());
  }

  @Test
  public void testCollectSnippetsAndSnippets()
          throws FileNotFoundException, IOException, ArchiveException {
    Collection<Module> snippets = new ArrayList<>();
    Module s = new Module();
    s.setArtifact("tw.com.softleader:softleader-web");
    s.setRootConfigs(
            Lists.newArrayList("tw.com.softleader.data.config.DataSourceConfiguration.class",
                    "tw.com.softleader.domain.config.DefaultDomainConfiguration.class",
                    "WebSecurityConfig.class", "ServiceConfig.class"));
    s.setRemoveRootConfigs(Lists.newArrayList());
    s.setServletConfigs(Lists.newArrayList("WebMvcConfig.class"));
    s.setRemoveServletConfigs(Lists.newArrayList());
    s.setServletFilters(Lists.newArrayList());
    s.setRemoveServletFilters(Lists.newArrayList());
    s.setDirs(Lists.newArrayList("src/main/java/{pkgPath}/security/service",
            "src/main/java/{pkgPath}/index/web", "src/main/resources", "src/main/webapp/WEB-INF/pages",
            "src/test/java/{pkgPath}", "src/test/resources"));
    s.setSources(Lists.newArrayList());
    snippets.add(s);

    Map<ZipArchiveEntry, InputStream> archives =
            new ModuleService().new ArchiveEntries(starter, snippets).collect();
    File zip = new File("/Users/Matt/temp/test.zip");
    ArchiveStream.of(new FileOutputStream(zip)).compress(archives);
    System.out.println(zip.getPath() + " created!");
    System.out.println(zip.exists());
  }

}
