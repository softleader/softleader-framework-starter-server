package tw.com.softleader.starter.server.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Version {

  @NotEmpty
  private String softleaderFramework;
  @NotEmpty
  private String springIoPlatform;

}
