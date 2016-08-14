package tw.com.softleader.starter.server.pojo;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Starter {

  @NotNull
  private ProjectDetails project;
  @NotNull
  private Version version;
  @NotNull
  private Collection<Dependency> dependencies;
  @NotNull
  private Database database;

}
