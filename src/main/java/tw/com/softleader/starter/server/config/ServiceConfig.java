package tw.com.softleader.starter.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"tw.com.softleader.starter.server.**.service"})
public class ServiceConfig {

}
