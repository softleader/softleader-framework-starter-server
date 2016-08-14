package tw.com.softleader.starter.server.io;

import java.util.Optional;
import java.util.function.UnaryOperator;

import lombok.RequiredArgsConstructor;
import tw.com.softleader.starter.server.pojo.Starter;

@RequiredArgsConstructor
public class Datasource implements UnaryOperator<String> {

  private final Starter starter;

  @Override
  public String apply(String source) {

    source = source.replace("{database}", starter.getDatabase().getName().toUpperCase());
    source = source.replace("{driverClass}", starter.getDatabase().getDriverClass());
    source = source.replace("{url}", starter.getDatabase().getUrl());
    source = source.replace("{username}", starter.getDatabase().getUsername());
    source = source.replace("{password}",
        Optional.ofNullable(starter.getDatabase().getPassword()).orElse(""));

    return source;
  }

}
