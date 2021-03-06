package tw.com.softleader.starter.server.dao;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tw.com.softleader.starter.server.config.ApplicationConfig;
import tw.com.softleader.starter.server.entity.Module;
import tw.com.softleader.starter.server.entity.Source;
import tw.com.softleader.starter.server.enums.Wizard;

import javax.transaction.Transactional;

@WithMockUser("matt")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
@Commit
public class ModuleWebappSnippetTest {

  @Autowired
  private ModuleDao dao;

  @Test
  public void testDeleteAll() {
    // dao.deleteAll();
  }

  @Test
  public void testGlobalModule() {
    Module module = new Module();
    module.setWizard(Wizard.WEBAPP);
    module.setSources(Lists.newArrayList());
    module.addSource(new Source("/webapp/global"));
    dao.save(module);
  }

  @Test
  public void testDataModule() {
    Module module = new Module();
    module.setWizard(Wizard.WEBAPP);
    module.setArtifact("tw.com.softleader:softleader-data");
    module.setSources(Lists.newArrayList());
    module.addSource(new Source("/webapp/softleader-data"));
    dao.save(module);
  }

  @Test
  public void testMybatisModule() {
    Module module = new Module();
    module.setWizard(Wizard.WEBAPP);
    module.setArtifact("tw.com.softleader:softleader-data-mybatis");
    module.setRequires(Lists.newArrayList("tw.com.softleader:softleader-data"));
    // module.setRootConfigs(Lists.newArrayList("DataSourceConfig.class"));
    module.setRemoveRootConfigs(
            Lists.newArrayList("tw.com.softleader.data.config.DataSourceConfiguration.class"));
    module.setSources(Lists.newArrayList());
    module.addSource(new Source("/webapp/softleader-data-mybatis"));
    dao.save(module);
  }

  @Test
  public void testJpaModule() {
    Module module = new Module();
    module.setWizard(Wizard.WEBAPP);
    module.setArtifact("tw.com.softleader:softleader-data-jpa");
    module.setRequires(Lists.newArrayList("tw.com.softleader:softleader-data"));
    module.setRootConfigs(Lists.newArrayList("DataSourceConfig.class"));
    module.setRemoveRootConfigs(
            Lists.newArrayList("tw.com.softleader.data.config.DataSourceConfiguration.class"));
    module.setServletFilters(Lists
            .newArrayList("new org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter()",
                    "new tw.com.softleader.security.xss.http.HtmlXSSProtectionFilter()"));
    module.setSources(Lists.newArrayList());
    module.addSource(new Source("/webapp/softleader-data-jpa"));
    dao.save(module);
  }

  @Test
  public void testDomainSchedulingModule() {
    Module module = new Module();
    module.setWizard(Wizard.WEBAPP);
    module.setArtifact("tw.com.softleader:softleader-domain-scheduling");
    module.setRootConfigs(Lists.newArrayList("SchedulingConfig.class"));
    module.setSources(Lists.newArrayList());
    module.addSource(new Source("/webapp/softleader-domain-scheduling"));
    dao.save(module);
  }

  @Test
  public void testDomainRuleModule() {
    Module module = new Module();
    module.setWizard(Wizard.WEBAPP);
    module.setArtifact("tw.com.softleader:softleader-domain-rule");
    module.setRootConfigs(
            Lists.newArrayList("tw.com.softleader.domain.rule.config.DomainRuleConfiguration.class"));
    dao.save(module);
  }

  @Test
  public void testSecurity() {
    Module module = new Module();
    module.setWizard(Wizard.WEBAPP);
    module.setArtifact("tw.com.softleader:softleader-security");
    module.setRequires(Lists.newArrayList("tw.com.softleader:softleader-web-mvc"));
    module.setRootConfigs(Lists.newArrayList("WebSecurityConfig.class"));
    module.setSources(Lists.newArrayList());
    module.addSource(new Source("/webapp/softleader-security"));
    dao.save(module);
  }

  @Test
  public void testWebMvc() {
    Module module = new Module();
    module.setWizard(Wizard.WEBAPP);
    module.setArtifact("tw.com.softleader:softleader-web-mvc");
    module.setRootConfigs(
            Lists.newArrayList(
                    "WebSecurityConfig.class", "ApplicationConfig.class"));
    module.setServletConfigs(Lists.newArrayList("WebMvcConfig.class"));
    module.setSources(Lists.newArrayList());
    module.addSource(new Source("/webapp/softleader-web-mvc"));
    dao.save(module);
  }


}
