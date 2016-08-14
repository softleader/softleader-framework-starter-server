package tw.com.softleader.starter.server.io;

import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import tw.com.softleader.starter.server.pojo.Dependency;
import tw.com.softleader.starter.server.pojo.Starter;

@RequiredArgsConstructor
public class Pom implements UnaryOperator<String> {

  private final Starter starter;

  @Override
  public String apply(String source) {
    source = source.replace("{projectName}", starter.getProject().getName());
    source = source.replace("{groupId}", starter.getProject().getGroupId());
    source = source.replace("{artifactId}", starter.getProject().getArtifactId());
    source = source.replace("{version}", starter.getProject().getVersion());

    source = source.replace("{slVersion}", starter.getVersion().getSoftleaderFramework());
    source = source.replace("{ioVersion}", starter.getVersion().getSpringIoPlatform());

    String dependencyText =
        starter.getDependencies().stream().sorted(Comparator.comparing(Dependency::getArtifactId))
            .map(this::getDependencyText).collect(Collectors.joining("\n"));
    source = source.replace("{dependencies}", dependencyText);

    source = source.replace("{datasource}", getDependencyText(starter.getDatabase()));

    return source;
  }

  String getDependencyText(Dependency dependency) {
    String text = "\t\t<dependency>\n";
    text += "\t\t\t<groupId>" + dependency.getGroupId() + "</groupId>\n";
    text += "\t\t\t<artifactId>" + dependency.getArtifactId() + "</artifactId>\n";
    if (dependency.getVersion() != null && !dependency.getVersion().isEmpty()) {
      text += "\t\t\t<version>" + dependency.getVersion() + "</version>\n";
    }
    if (dependency.getScope() != null && !dependency.getScope().isEmpty()) {
      text += "\t\t\t<scope>" + dependency.getScope() + "</scope>\n";
    }
    text += "\t\t</dependency>";
    return text;
  }


}
