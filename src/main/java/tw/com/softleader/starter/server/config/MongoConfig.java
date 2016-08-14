package tw.com.softleader.starter.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

  @Override
  protected String getDatabaseName() {
    return "starter";
  }

  @Bean
  @Override
  public Mongo mongo() throws Exception {
    MongoClientFactoryBean mongo = new MongoClientFactoryBean();
    mongo.setHost("localhost");
    mongo.afterPropertiesSet();
    return mongo.getObject();
  }

  @Bean
  public MongoTemplate mongoTemplate(Mongo mongo) throws Exception {
    return new MongoTemplate(mongo, "starter");
  }
}
