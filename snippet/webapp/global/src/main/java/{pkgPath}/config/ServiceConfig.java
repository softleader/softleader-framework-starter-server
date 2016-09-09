package {pkg}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import tw.com.softleader.security.supplier.CurrentUsernameSupplier;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"{pkg}.**.service"})
public class ServiceConfig {

  @Bean
  public CurrentUsernameSupplier currentUsernameSupplier() {
    return new CurrentUsernameSupplier();
  }

}
