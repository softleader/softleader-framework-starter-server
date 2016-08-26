package {pkg}.config;

import java.util.Collection;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import tw.com.softleader.commons.collect.Lists;
import tw.com.softleader.data.config.DataSourceConfiguration;
import tw.com.softleader.data.entity.EntityPersistenceCallbackSupplier;
import tw.com.softleader.security.supplier.CurrentUsernameSupplier;

/**
 * @see https://github.com/softleader/softleader-framework-docs/wiki/Mybatis-Datasource-Setup
 */
@Configuration
public class DataSourceConfig extends DataSourceConfiguration {

  @Override
  public Collection<String> entityPackagesToScan() {
    return Lists.newArrayList("{pkg}.demo.entity");
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

  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer() {
    final MapperScannerConfigurer mapperScanner = new MapperScannerConfigurer();
    mapperScanner.setBasePackage("{pkg}.**.dao");
    mapperScanner.setAnnotationClass(Repository.class);
    mapperScanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
    return mapperScanner;
  }
}
