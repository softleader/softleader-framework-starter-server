package tw.com.softleader.starter.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"tw.com.softleader.starter.server.**.service"})
@PropertySource({"classpath:application.properties"})
public class ServiceConfig {

}
