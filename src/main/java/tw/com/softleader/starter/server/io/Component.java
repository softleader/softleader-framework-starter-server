package tw.com.softleader.starter.server.io;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import tw.com.softleader.starter.server.pojo.Dependency;
import tw.com.softleader.starter.server.pojo.Snippet;
import tw.com.softleader.util.StringUtils;

@RequiredArgsConstructor
public class Component implements UnaryOperator<String> {

  private final Snippet starter;

  @Override
  public String apply(String source) {
    String dependentModule = starter.getDependencies().stream().map(this::getComponentText)
        .collect(Collectors.joining("\n"));
    source = source.replace("{dependent-module}", dependentModule);
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
}
