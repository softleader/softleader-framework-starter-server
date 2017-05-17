package tw.com.softleader.starter.server.pojo;

import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Dependency {

  // public static final String SOFTLEADER_FRAMEOWK_VERISON = "${softleader-framework.version}";

  public Dependency(String groupId, String artifactId) {
    this.groupId = groupId;
    this.artifactId = artifactId;
  }

  @NotEmpty
  private String groupId;
  @NotEmpty
  private String artifactId;
  @Getter private String version;
  private String scope;

//  public String getVersion() {
//    return "tw.com.softleader".equals(getGroupId()) && (version == null || version.isEmpty())
//        ? SOFTLEADER_FRAMEOWK_VERISON : version;
//  }

}
