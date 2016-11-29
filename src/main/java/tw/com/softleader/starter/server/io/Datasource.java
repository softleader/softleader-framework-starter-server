package tw.com.softleader.starter.server.io;

import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.util.VisibleForTesting;
import org.springframework.orm.jpa.vendor.Database;

import lombok.RequiredArgsConstructor;
import tw.com.softleader.starter.server.pojo.Snippet;

@RequiredArgsConstructor
public class Datasource implements UnaryOperator<String> {

  private static Pattern FIRST_WORD = Pattern.compile("^(\\w+)\\b");

  private final Snippet starter;

  @Override
  public String apply(String source) {

    source = source.replace("{database}", smartAnalyseDatabase(starter.getDatabase().getName()));
    source = source.replace("{driverClass}", starter.getDatabase().getDriverClass());
    source = source.replace("{url}", starter.getDatabase().getUrl());
    source = source.replace("{username}", starter.getDatabase().getUsername());
    source = source.replace("{password}",
        Optional.ofNullable(starter.getDatabase().getPassword()).orElse(""));

    return source;
  }

  @VisibleForTesting
  String smartAnalyseDatabase(String name) {
    // special rules
    if (name.startsWith("Microsoft sql")) {
      return Database.SQL_SERVER.name();
    }
    // matches whole words
    for (Database database : Database.values()) {
      if (database.name().equalsIgnoreCase(name)) {
        return database.name();
      }
    }
    // matches first word
    Matcher m = FIRST_WORD.matcher(name);
    if (m.find()) {
      String firstLetter = m.group(1);
      for (Database database : Database.values()) {
        if (database.name().equalsIgnoreCase(firstLetter)) {
          return database.name();
        }
      }
    }
    return "Choose a database vendor from 'org.springframework.orm.jpa.vendor.Database'";
  }

}
