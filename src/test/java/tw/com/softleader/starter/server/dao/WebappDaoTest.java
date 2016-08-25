package tw.com.softleader.starter.server.dao;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import tw.com.softleader.domain.config.DefaultDomainConfiguration;
import tw.com.softleader.starter.server.config.DataSourceConfig;
import tw.com.softleader.starter.server.config.ServiceConfig;
import tw.com.softleader.starter.server.entity.Webapp;
import tw.com.softleader.starter.server.entity.WebappDatabase;
import tw.com.softleader.starter.server.entity.WebappDependency;
import tw.com.softleader.starter.server.entity.WebappModule;
import tw.com.softleader.starter.server.entity.WebappVersion;
import tw.com.softleader.starter.server.enums.SwtLayout;
import tw.com.softleader.starter.server.enums.SwtStyle;

@WithMockUser("matt")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {ServiceConfig.class, DataSourceConfig.class, DefaultDomainConfiguration.class})
@Transactional
@Commit
public class WebappDaoTest {

  @Autowired
  private WebappDao dao;

  @Test
  public void testInsert() {
    Webapp webapp = new Webapp();

    // webapp.setRevision(System.currentTimeMillis());
    webapp.setRevision(1471418080640L);
    webapp.setBaseUrl("http://118.163.91.249/starter/webapp/zip");

    webapp.setProjectArtifactId("softleader-");
    webapp.setProjectDesc("SoftLeader project for ");
    webapp.setProjectGroupId("tw.com.softleader");
    webapp.setProjectPkg("tw.com.softleader");
    webapp.setProjectVersion("0.0.1-SNAPSHOT");

    webapp.setVersionText("SoftLeader Framework Version");
    webapp.setVersionLayout(SwtLayout.H);
    webapp.setVersions(new ArrayList<>());
    webapp.addVersion(new WebappVersion("1.1.0.SNAPSHOT", "2.0.3.RELEASE", true, true));
    webapp.addVersion(new WebappVersion("1.0.0.RELEASE", "1.1.3.RELEASE", false, false));

    webapp.setModules(new ArrayList<>());
    WebappModule module;
    webapp.addModule(module = new WebappModule());
    module.setDependencyText("Basic");
    module.setDependencyStyle(SwtStyle.CHECK);
    module.setDependencyLayout(SwtLayout.H);
    module.setDependencies(new ArrayList<>());
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-util", null, null, true, false));
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-commons", null, null, true, false));
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-web", null, null, true, false));
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-domain", null, null, true, false));
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-data", null, null, true, false));

    webapp.addModule(module = new WebappModule());
    module.setDependencyText("Web");
    module.setDependencyStyle(SwtStyle.CHECK);
    module.setDependencyLayout(SwtLayout.V);
    module.setDependencies(new ArrayList<>());
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-web-mvc", null, null, true, false));
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-security", null, null, true, false));
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-resources", null, null, false, true));

    webapp.addModule(module = new WebappModule());
    module.setDependencyText("Domain");
    module.setDependencyStyle(SwtStyle.CHECK);
    module.setDependencyLayout(SwtLayout.V);
    module.setDependencies(new ArrayList<>());
    module.addDependency(new WebappDependency("tw.com.softleader", "softleader-domain-rule", null,
        null, false, true));
    module.addDependency(new WebappDependency("tw.com.softleader", "softleader-domain-formula",
        null, null, false, true));
    module.addDependency(new WebappDependency("tw.com.softleader", "softleader-domain-scheduling",
        null, null, false, true));
    module.addDependency(new WebappDependency("tw.com.softleader", "softleader-report-jasper", null,
        null, false, true));
    module.addDependency(new WebappDependency("tw.com.softleader", "softleader-domain-bpm", null,
        null, false, false));

    webapp.addModule(module = new WebappModule());
    module.setDependencyText("Data");
    module.setDependencyStyle(SwtStyle.RADIO);
    module.setDependencyLayout(SwtLayout.V);
    module.setDependencies(new ArrayList<>());
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-data-jpa", null, null, true, true));
    module.addDependency(new WebappDependency("tw.com.softleader", "softleader-data-mybatis", null,
        null, false, true));

    webapp.addModule(module = new WebappModule());
    module.setDependencyText("Test");
    module.setDependencyStyle(SwtStyle.RADIO);
    module.setDependencyLayout(SwtLayout.V);
    module.setDependencies(new ArrayList<>());
    module.addDependency(
        new WebappDependency("tw.com.softleader", "softleader-test", null, null, true, false));

    webapp.setDatabaseText("Database");
    webapp.setDatabaseLayout(SwtLayout.V);
    webapp.setDatabaseStyle(SwtStyle.RADIO);
    webapp.setDatabases(new ArrayList<>());
    webapp.addDatabase(new WebappDatabase("PostgreSQL", "org.postgresql", "postgresql", null,
        "org.postgresql.Driver", true, true, "jdbc:postgresql:[<//host>[:<5432>/]]<database>"));
    webapp.addDatabase(new WebappDatabase("MySQL", "mysql", "mysql-connector-java", null,
        "com.mysql.jdbc.Driver", false, true,
        "jdbc:mysql://<hostname>[,<failoverhost>][<:3306>]/<dbname>[?<param1>=<value1>][&<param2>=<value2>]"));
    webapp.addDatabase(new WebappDatabase("Oracle Thin ojdbc14", "com.oracle", "ojdbc14",
        "10.2.0.4.0", "oracle.jdbc.driver.OracleDriver", false, true,
        "jdbc:oracle:thin:@<server>[:<1521>]:<database_name>"));
    webapp.addDatabase(new WebappDatabase("Microsoft sqljdbc4", "com.microsoft.sqlserver",
        "sqljdbc4", "4.0", "com.microsoft.sqlserver.jdbc.SQLServerDriver", false, true,
        "jdbc:sqlserver://<server_name>:1433;databaseName=<db_name>"));
    webapp.addDatabase(new WebappDatabase("HSQL", "org.hsqldb", "hsqldb", null,
        "org.hsqldb.jdbcDriver", false, true, "jdbc:hsqldb:hsql://<server>[:<1476>]"));
    webapp.addDatabase(new WebappDatabase("H2", "com.h2database", "h2", null, "org.h2.Driver",
        false, true, "jdbc:h2://<server>:<9092>/<db-name>"));

    dao.save(webapp);
  }

  @Test
  public void testToJson() throws JsonProcessingException {
    Webapp starter = dao.findAll().get(0);
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    System.out.println(mapper.writeValueAsString(starter));
  }

}
