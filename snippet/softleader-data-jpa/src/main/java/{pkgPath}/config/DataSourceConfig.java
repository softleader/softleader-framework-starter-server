package {pkg}.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tw.com.softleader.commons.collect.Lists;
import tw.com.softleader.data.config.DataSourceConfiguration;
import tw.com.softleader.data.dao.GenericCrudDaoImpl;
import tw.com.softleader.data.entity.EntityPersistenceCallbackSupplier;
import tw.com.softleader.security.supplier.CurrentUsernameSupplier;

/**
 * @see https://github.com/softleader/softleader-framework-docs/wiki/JPA-Datasource-Setup 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"tw.com.softleader.data.**.dao", "{pkg}.**.dao"},
    entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager",
    repositoryBaseClass = GenericCrudDaoImpl.class)
public class DataSourceConfig extends DataSourceConfiguration {

  @Override
  public Collection<String> entityPackagesToScan() {
    return Lists.newArrayList("{pkg}.**.entity");
  }

  @Autowired
  private CurrentUsernameSupplier currentUsernameSupplier;

  @Override
  @Bean
  public EntityPersistenceCallbackSupplier entityPersistenceSupport() {
    return new EntityPersistenceCallbackSupplier(currentUsernameSupplier);
  }

}
