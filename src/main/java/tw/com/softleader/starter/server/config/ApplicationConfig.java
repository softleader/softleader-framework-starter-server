package tw.com.softleader.starter.server.config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.Database;
import tw.com.softleader.data.config.EnableCoreDataSource;
import tw.com.softleader.data.dao.GenericCrudDaoImpl;
import tw.com.softleader.data.entity.EntityPersistenceCallbackSupplier;
import tw.com.softleader.domain.config.DomainConfiguration;
import tw.com.softleader.security.supplier.CurrentUsernameSupplier;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(
        basePackages = {"tw.com.softleader.data.**.dao", "tw.com.softleader.starter.server.**.dao"},
        repositoryBaseClass = GenericCrudDaoImpl.class
)
@EnableCoreDataSource(
        persistenceUnitName = "softleader-starter",
        database = Database.POSTGRESQL,
        idStrategies = {"tw.com.softleader.starter.server.**=AUTO", "tw.com.softleader.**=AUTO"},
        entityPackagesToScan = "tw.com.softleader.starter.server.**.entity",
        propertySources = "datasource.properties"
)
@ComponentScan(basePackages = {"tw.com.softleader.starter.server.**.service"})
@PropertySource("classpath:application.properties")
@Import(DomainConfiguration.class)
@Configuration
public class ApplicationConfig {

  @Bean
  public CurrentUsernameSupplier currentUsernameSupplier() {
    return new CurrentUsernameSupplier();
  }

  @Bean
  @Primary
  public EntityPersistenceCallbackSupplier entityPersistenceCallbackSupplier() {
    return new EntityPersistenceCallbackSupplier(currentUsernameSupplier());
  }
}
