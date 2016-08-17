package tw.com.softleader.starter.server.dao;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tw.com.softleader.commons.collect.Lists;
import tw.com.softleader.domain.config.DefaultDomainConfiguration;
import tw.com.softleader.starter.server.config.DataSourceConfig;
import tw.com.softleader.starter.server.config.ServiceConfig;
import tw.com.softleader.starter.server.entity.Module;

@WithMockUser("matt")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {ServiceConfig.class, DataSourceConfig.class, DefaultDomainConfiguration.class})
@Transactional
@Commit
public class ModuleSnippetTest {

  @Autowired
  private ModuleDao dao;

  @Test
  public void testDeleteAll() {
    // dao.deleteAll();
  }

  @Test
  public void testGlobalmodule() {
    Module module = new Module();
    module.setSnippets(
        Lists.newArrayList("/Users/Matt/git/softleader-framework-starter-server/snippet/global"));
    dao.save(module);
  }

  @Test
  public void testJpamodule() {
    Module module = new Module();
    module.setArtifact("tw.com.softleader:softleader-data-jpa");
    module.setRootConfigs(Lists.newArrayList("DataSourceConfig.class"));
    module.setRemoveRootConfigs(
        Lists.newArrayList("tw.com.softleader.data.config.DataSourceConfiguration.class"));
    module.setServletFilters(Lists
        .newArrayList("new org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter()"));
    module.setSnippets(Lists.newArrayList(
        "/Users/Matt/git/softleader-framework-starter-server/snippet/softleader-data-jpa"));
    dao.save(module);
  }

  @Test
  public void testDomainSchedulingmodule() {
    Module module = new Module();
    module.setArtifact("tw.com.softleader:softleader-domain-scheduling");
    module.setRootConfigs(Lists.newArrayList("SchedulingConfig.class"));
    module.setSnippets(Lists.newArrayList(
        "/Users/Matt/git/softleader-framework-starter-server/snippet/softleader-domain-scheduling"));
    dao.save(module);
  }

  @Test
  public void testDomainRulemodule() {
    Module module = new Module();
    module.setArtifact("tw.com.softleader:softleader-domain-rule");
    module.setRootConfigs(
        Lists.newArrayList("tw.com.softleader.rule.config.RuleConfiguration.class"));
    dao.save(module);
  }

  @Test
  public void testSecurity() {
    Module module = new Module();
    module.setArtifact("tw.com.softleader:softleader-web-mvc");
    module.setRequires(Lists.newArrayList("tw.com.softleader:softleader-web-mvc"));
    module.setRootConfigs(Lists.newArrayList("WebSecurityConfig.class"));
    module.setSnippets(Lists.newArrayList(
        "/Users/Matt/git/softleader-framework-starter-server/snippet/softleader-security"));
    dao.save(module);
  }

  @Test
  public void testWebMvc() {
    Module module = new Module();
    module.setArtifact("tw.com.softleader:softleader-web-mvc");
    module.setRootConfigs(
        Lists.newArrayList("tw.com.softleader.data.config.DataSourceConfiguration.class",
            "tw.com.softleader.domain.config.DefaultDomainConfiguration.class",
            "WebSecurityConfig.class", "ServiceConfig.class"));
    module.setServletConfigs(Lists.newArrayList("WebMvcConfig.class"));
    module.setSnippets(Lists.newArrayList(
        "/Users/Matt/git/softleader-framework-starter-server/snippet/softleader-web-mvc"));
    dao.save(module);
  }


}
