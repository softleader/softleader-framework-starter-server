package tw.com.softleader.starter.server.io;

import java.util.function.UnaryOperator;

import lombok.RequiredArgsConstructor;
import tw.com.softleader.starter.server.pojo.Starter;

@RequiredArgsConstructor
public class SnippetSource implements UnaryOperator<String> {

  private final Starter starter;

  /**
   * 會取代以下 keyword: 1: {pj} 2. {pkg}, 3. {pkgPath}
   */
  @Override
  public String apply(String source) {
    return source.replaceAll("\\{pj\\}", starter.getProject().getName())
        .replaceAll("\\{pkg\\}", starter.getProject().getPkg())
        .replaceAll("\\{pkgPath\\}", starter.getProject().getPkgPath());
  }

}
