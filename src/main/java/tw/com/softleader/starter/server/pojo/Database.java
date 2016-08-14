package tw.com.softleader.starter.server.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Database extends Dependency {

  @NotEmpty
  private String name;
  @NotEmpty
  private String driverClass;
  @NotEmpty
  private String url;
  @NotEmpty
  private String username;
  private String password;

}
