package tw.com.softleader.starter.server.io;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.vendor.Database;
import tw.com.softleader.starter.server.pojo.Dependency;
import tw.com.softleader.starter.server.pojo.Snippet;
import tw.com.softleader.util.StringUtils;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Component implements UnaryOperator<String> {

  private static Pattern FIRST_WORD = Pattern.compile("^(\\w+)\\b");

  private final Snippet starter;

  @Override
  public String apply(String source) {
    String dependentModule = starter.getDependencies().stream().map(this::getComponentText)
            .collect(Collectors.joining("\n"));
    source = source.replace("{dependent-module}", dependentModule);
    source = source.replace("{database}", smartAnalyseDatabase(starter.getDatabase().getName()));
    return source;
  }

  String getComponentText(Dependency dependency) {
    String text =
            "\t\t<dependent-module archiveName=\"{}-{}.jar\" deploy-path=\"/WEB-INF/lib\" handle=\"module:/resource/{}/{}\">\n";
    text += "\t\t\t<dependency-type>uses</dependency-type>\n";
    text += "\t\t</dependent-module>";
    return StringUtils.format(text, dependency.getArtifactId(),
            starter.getVersion().getSoftleaderFramework(), dependency.getArtifactId(),
            dependency.getArtifactId());
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
