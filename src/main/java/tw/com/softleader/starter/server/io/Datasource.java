package tw.com.softleader.starter.server.io;

import lombok.RequiredArgsConstructor;
import tw.com.softleader.starter.server.pojo.Snippet;

import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class Datasource implements UnaryOperator<String> {

  private final Snippet starter;

  @Override
  public String apply(String source) {

    source = source.replace("{driverClass}", starter.getDatabase().getDriverClass());
    source = source.replace("{url}", starter.getDatabase().getUrl());
    source = source.replace("{username}", starter.getDatabase().getUsername());
    source = source.replace("{password}",
        Optional.ofNullable(starter.getDatabase().getPassword()).orElse(""));

    return source;
  }



}
