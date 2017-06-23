package tw.com.softleader.starter.server.io;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.orm.jpa.vendor.Database;


public class ComponentTest {

  @Test
  public void testSmartAnalyseDatabase() {
    SnippetSource ds = new SnippetSource(null);

    Assert.assertEquals(Database.POSTGRESQL.name(), ds.smartAnalyseDatabase("PostgreSQL"));
    Assert.assertEquals(Database.MYSQL.name(), ds.smartAnalyseDatabase("MySQL"));
    Assert.assertEquals(Database.ORACLE.name(), ds.smartAnalyseDatabase("Oracle Thin ojdbc14"));
    Assert.assertEquals(Database.SQL_SERVER.name(), ds.smartAnalyseDatabase("Microsoft sqljdbc4"));
    Assert.assertEquals(Database.HSQL.name(), ds.smartAnalyseDatabase("HSQL"));
    Assert.assertEquals(Database.H2.name(), ds.smartAnalyseDatabase("H2"));
  }

}
