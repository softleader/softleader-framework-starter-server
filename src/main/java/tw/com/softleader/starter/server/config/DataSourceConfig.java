package tw.com.softleader.starter.server.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tw.com.softleader.commons.collect.Lists;
import tw.com.softleader.data.config.DataSourceConfiguration;
import tw.com.softleader.data.dao.GenericCrudDaoImpl;
import tw.com.softleader.data.entity.EntityPersistenceCallbackSupplier;
import tw.com.softleader.security.supplier.CurrentUsernameSupplier;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"tw.com.softleader.data.**.dao", "tw.com.softleader.starter.server.**.dao"},
    entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager",
    repositoryBaseClass = GenericCrudDaoImpl.class)
public class DataSourceConfig extends DataSourceConfiguration {

  @Override
  public Collection<String> entityPackagesToScan() {
    return Lists.newArrayList("tw.com.softleader.starter.server.**.entity");
  }

  @Override
  @Bean
  public EntityPersistenceCallbackSupplier entityPersistenceSupport() {
    return new EntityPersistenceCallbackSupplier(currentUsernameSupplier());
  }

  @Bean
  public CurrentUsernameSupplier currentUsernameSupplier() {
    return new CurrentUsernameSupplier();
  }

}
