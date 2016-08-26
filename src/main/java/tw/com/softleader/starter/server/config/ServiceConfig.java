package tw.com.softleader.starter.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import tw.com.softleader.security.supplier.CurrentUsernameSupplier;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"tw.com.softleader.starter.server.**.service"})
@PropertySource({"classpath:application.properties"})
public class ServiceConfig {

  @Bean
  public CurrentUsernameSupplier currentUsernameSupplier() {
    return new CurrentUsernameSupplier();
  }

}
