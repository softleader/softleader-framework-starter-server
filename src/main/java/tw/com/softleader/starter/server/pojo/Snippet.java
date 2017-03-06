package tw.com.softleader.starter.server.pojo;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.Data;
import tw.com.softleader.starter.server.enums.IDE;

@Data
public class Snippet {

  @NotNull
  private ProjectDetails project;
  @NotNull
  private Version version;
  @NotNull
  private Collection<Dependency> dependencies;
  @NotNull
  private Database database;
  private IDE ide = IDE.ECLIPSE;
}
