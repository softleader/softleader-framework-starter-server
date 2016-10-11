package tw.com.softleader.starter.server.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tw.com.softleader.commons.json.jackson.JacksonObjectMapper;
import tw.com.softleader.domain.config.DefaultDomainConfiguration;
import tw.com.softleader.starter.server.config.DataSourceConfig;
import tw.com.softleader.starter.server.config.ServiceConfig;
import tw.com.softleader.starter.server.config.WebMvcConfig;

@WebAppConfiguration
@WithMockUser("matt")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, DataSourceConfig.class,
    DefaultDomainConfiguration.class, WebMvcConfig.class})
public class WebappControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private JacksonObjectMapper mapper;

  private MockMvc mvc;

  @Before
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .alwaysDo(MockMvcResultHandlers.print()).build();
  }

  @Test
  public void testGetZip() throws Exception {
    // final Starter starter = new Starter();
    // ProjectDetails project = new ProjectDetails();
    // project.setName("Hello world");
    // project.setPkg("org.hello.world");
    // project.setGroupId("tw.com.softleader");
    // project.setArtifactId("hello-world");
    // project.setVersion("0.0.1-SNAPSHOT");
    // starter.setProject(project);
    // starter.setVersion(new Version("1.1.0.SNAPSHOT", "2.0.3.RELEASE"));
    // Database database = new Database();
    // database.setName("postgres");
    // database.setDriverClass("org.postgresql.Driver");
    // database.setUrl("jdbc:postgresql://softleader.com.tw:5432/i519Payment");
    // database.setUsername("sa");
    // starter.setDatabase(database);
    // starter.setDependencies(
    // Lists.newArrayList(new Dependency("tw.com.softleader", "softleader-web-mvc"),
    // new Dependency("tw.com.softleader", "softleader-security"),
    // new Dependency("tw.com.softleader", "softleader-data-jpa")));

    String body =
        "{\"database\":{\"artifactId\":\"postgresql\",\"driverClass\":\"org.postgresql.Driver\",\"groupId\":\"org.postgresql\",\"name\":\"PostgreSQL\",\"password\":null,\"scope\":null,\"url\":\"jdbc:postgresql:[<//host>[:<5432>/]]<database>\",\"username\":\"a\",\"version\":null},\"dependencies\":[{\"artifactId\":\"softleader-util\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"},{\"artifactId\":\"softleader-commons\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"},{\"artifactId\":\"softleader-web\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"},{\"artifactId\":\"softleader-domain\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"},{\"artifactId\":\"softleader-data\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"},{\"artifactId\":\"softleader-web-mvc\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"},{\"artifactId\":\"softleader-security\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"},{\"artifactId\":\"softleader-test\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"},{\"artifactId\":\"softleader-data-jpa\",\"groupId\":\"tw.com.softleader\",\"scope\":null,\"version\":\"${softleader-framework.version}\"}],\"project\":{\"artifactId\":\"softleader-aaa\",\"groupId\":\"tw.com.softleader\",\"name\":\"aaa\",\"pkg\":\"tw.com.softleader.aaa\",\"pkgPath\":\"tw/com/softleader/aaa\",\"version\":\"0.0.1-SNAPSHOT\"},\"version\":{\"softleaderFramework\":\"1.1.0.SNAPSHOT\",\"springIoPlatform\":\"2.0.3.RELEASE\"}}";

    MockHttpServletResponse respone = mvc
        .perform(post("/webapp/zip").contentType(MediaType.APPLICATION_JSON)
            .accept("application/zip").content(body))
        .andExpect(status().isOk()).andReturn().getResponse();

    byte[] bytes = respone.getContentAsByteArray();
    Assert.assertNotNull(bytes);

    // ZipStream.toMap(new ByteArrayInputStream(bytes), ArchiveEntry::getName,
    // out -> new String(out.toByteArray(), StandardCharsets.UTF_8), ByteArrayOutputStream::new)
    // .forEach((k, v) -> log.info(k));

    Files.write(Paths.get("/Users/Matt/temp/test.zip"), bytes);
  }

}
