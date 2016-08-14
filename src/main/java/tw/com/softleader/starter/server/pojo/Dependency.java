package tw.com.softleader.starter.server.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Dependency {

  public Dependency(String groupId, String artifactId) {
    super();
    this.groupId = groupId;
    this.artifactId = artifactId;
  }

  @NotEmpty
  private String groupId;
  @NotEmpty
  private String artifactId;
  private String version;
  private String scope;


}
