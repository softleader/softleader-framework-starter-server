package tw.com.softleader.starter.server.pojo;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import tw.com.softleader.starter.server.entity.Module;

@Data
public class ProjectDetails {

  private @JsonIgnore Set<String> rootConfigs;
  private @JsonIgnore Set<String> servletConfigs;
  private @JsonIgnore Set<String> servletFilters;
  private @JsonIgnore Set<String> dirs;

  @NotEmpty
  private String name;
  @NotEmpty
  private String pkg;
  @NotEmpty
  private String groupId;
  @NotEmpty
  private String artifactId;
  @NotEmpty
  private String version;

  public void collectGlobalInfo(Collection<Module> modules) {
    rootConfigs =
        modules.stream().map(Module::getRootConfigs).flatMap(Collection::stream).collect(toSet());
    modules.stream().map(Module::getRemoveRootConfigs).flatMap(Collection::stream)
        .forEach(this.rootConfigs::remove);

    servletConfigs = modules.stream().map(Module::getServletConfigs).flatMap(Collection::stream)
        .collect(toSet());
    modules.stream().map(Module::getRemoveServletConfigs).flatMap(Collection::stream)
        .forEach(this.servletConfigs::remove);

    servletFilters = modules.stream().map(Module::getServletFilters).flatMap(Collection::stream)
        .collect(toSet());
    modules.stream().map(Module::getRemoveServletFilters).flatMap(Collection::stream)
        .forEach(this.servletFilters::remove);

    dirs = modules.stream().map(Module::getDirs).flatMap(Collection::stream).collect(toSet());
  }

  public String getPkgPath() {
    return getPkg().replace(".", "/");
  }

}
