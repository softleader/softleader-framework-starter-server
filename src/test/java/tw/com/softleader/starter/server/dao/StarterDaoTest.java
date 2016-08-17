package tw.com.softleader.starter.server.dao;

import java.io.IOException;
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
import tw.com.softleader.starter.server.entity.Starter;
import tw.com.softleader.starter.server.entity.StarterDatabase;
import tw.com.softleader.starter.server.entity.StarterDependency;
import tw.com.softleader.starter.server.entity.StarterModule;
import tw.com.softleader.starter.server.entity.StarterVersion;
import tw.com.softleader.starter.server.enums.SwtLayout;
import tw.com.softleader.starter.server.enums.SwtStyle;

@WithMockUser("matt")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {ServiceConfig.class, DataSourceConfig.class, DefaultDomainConfiguration.class})
@Transactional
@Commit
public class StarterDaoTest {

  @Autowired
  private StarterDao dao;

  @Test
  public void testInsert() {
    Starter starter = new Starter();

    starter.setRevision(System.currentTimeMillis());
    starter.setBaseUrl(
        "https://raw.githubusercontent.com/softleader/softleader-framework-starter/master/resources");

    starter.setProjectArtifactId("softleader-");
    starter.setProjectDesc("SoftLeader project for ");
    starter.setProjectGroupId("tw.com.softleader");
    starter.setProjectPkg("tw.com.softleader");
    starter.setProjectVersion("0.0.1-SNAPSHOT");

    starter.setVersionText("SoftLeader Framework Version");
    starter.setVersionLayout(SwtLayout.H);
    starter.setVersions(new ArrayList<>());
    starter.addVersion(new StarterVersion("1.1.0.SNAPSHOT", "2.0.3.RELEASE", true, true));
    starter.addVersion(new StarterVersion("1.0.0.RELEASE", "1.1.3.RELEASE", false, false));

    starter.setModules(new ArrayList<>());
    StarterModule module;
    starter.addModule(module = new StarterModule());
    module.setDependencyText("Basic");
    module.setDependencyStyle(SwtStyle.CHECK);
    module.setDependencyLayout(SwtLayout.H);
    module.setDependencies(new ArrayList<>());
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-util", null, null, true, false));
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-commons", null, null, true, false));
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-web", null, null, true, false));
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-domain", null, null, true, false));
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-data", null, null, true, false));

    starter.addModule(module = new StarterModule());
    module.setDependencyText("Web");
    module.setDependencyStyle(SwtStyle.CHECK);
    module.setDependencyLayout(SwtLayout.V);
    module.setDependencies(new ArrayList<>());
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-web-mvc", null, null, true, false));
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-security", null, null, true, false));
    module.addDependency(new StarterDependency("tw.com.softleader", "softleader-resources", null,
        null, false, true));

    starter.addModule(module = new StarterModule());
    module.setDependencyText("Domain");
    module.setDependencyStyle(SwtStyle.CHECK);
    module.setDependencyLayout(SwtLayout.V);
    module.setDependencies(new ArrayList<>());
    module.addDependency(new StarterDependency("tw.com.softleader", "softleader-domain-rule", null,
        null, false, true));
    module.addDependency(new StarterDependency("tw.com.softleader", "softleader-domain-formula",
        null, null, false, true));
    module.addDependency(new StarterDependency("tw.com.softleader", "softleader-domain-scheduling",
        null, null, false, true));
    module.addDependency(new StarterDependency("tw.com.softleader", "softleader-report-jasper",
        null, null, false, true));
    module.addDependency(new StarterDependency("tw.com.softleader", "softleader-domain-bpm", null,
        null, false, false));

    starter.addModule(module = new StarterModule());
    module.setDependencyText("Data");
    module.setDependencyStyle(SwtStyle.RADIO);
    module.setDependencyLayout(SwtLayout.V);
    module.setDependencies(new ArrayList<>());
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-data-jpa", null, null, true, true));
    module.addDependency(new StarterDependency("tw.com.softleader", "softleader-data-mybatis", null,
        null, false, true));

    starter.addModule(module = new StarterModule());
    module.setDependencyText("Test");
    module.setDependencyStyle(SwtStyle.RADIO);
    module.setDependencyLayout(SwtLayout.V);
    module.setDependencies(new ArrayList<>());
    module.addDependency(
        new StarterDependency("tw.com.softleader", "softleader-test", null, null, true, false));

    starter.setDatabaseText("Database");
    starter.setDatabaseLayout(SwtLayout.V);
    starter.setDatabaseStyle(SwtStyle.RADIO);
    starter.setDatabases(new ArrayList<>());
    starter.addDatabase(new StarterDatabase("PostgreSQL", "org.postgresql", "postgresql", null,
        "org.postgresql.Driver", true, true, "jdbc:postgresql:[<//host>[:<5432>/]]<database>"));
    starter.addDatabase(new StarterDatabase("MySQL", "mysql", "mysql-connector-java", null,
        "com.mysql.jdbc.Driver", false, true,
        "jdbc:mysql://<hostname>[,<failoverhost>][<:3306>]/<dbname>[?<param1>=<value1>][&<param2>=<value2>]"));
    starter.addDatabase(new StarterDatabase("Oracle Thin ojdbc14", "com.oracle", "ojdbc14",
        "10.2.0.4.0", "oracle.jdbc.driver.OracleDriver", false, true,
        "jdbc:oracle:thin:@<server>[:<1521>]:<database_name>"));
    starter.addDatabase(new StarterDatabase("Microsoft sqljdbc4", "com.microsoft.sqlserver",
        "sqljdbc4", "4.0", "com.microsoft.sqlserver.jdbc.SQLServerDriver", false, true,
        "jdbc:sqlserver://<server_name>:1433;databaseName=<db_name>"));
    starter.addDatabase(new StarterDatabase("HSQL", "org.hsqldb", "hsqldb", null,
        "org.hsqldb.jdbcDriver", false, true, "jdbc:hsqldb:hsql://<server>[:<1476>]"));
    starter.addDatabase(new StarterDatabase("H2", "com.h2database", "h2", null, "org.h2.Driver",
        false, true, "jdbc:h2://<server>:<9092>/<db-name>"));

    dao.save(starter);
  }

  @Test
  public void testToJson() throws JsonProcessingException {
    Starter starter = dao.findAll().get(0);
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    System.out.println(mapper.writeValueAsString(starter));
  }

}
