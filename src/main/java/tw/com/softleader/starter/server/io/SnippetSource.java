package tw.com.softleader.starter.server.io;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.vendor.Database;
import tw.com.softleader.starter.server.pojo.Snippet;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class SnippetSource implements UnaryOperator<String> {

  private static Pattern FIRST_WORD = Pattern.compile("^(\\w+)\\b");

  private final Snippet starter;

  /**
   * 會取代以下 keyword: 1: {pj} 2. {pkg}, 3. {pkgPath}
   */
  @Override
  public String apply(String source) {
    return source.replaceAll("\\{pj\\}", starter.getProject().getName())
            .replaceAll("\\{pkg\\}", starter.getProject().getPkg())
            .replaceAll("\\{pkgPath\\}", starter.getProject().getPkgPath())
            .replaceAll("\\{database\\}", smartAnalyseDatabase(starter.getDatabase().getName()));
  }


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
