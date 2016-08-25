package tw.com.softleader.starter.server.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import tw.com.softleader.data.entity.GenericEntity;
import tw.com.softleader.data.jpa.converter.StringJoiningConverter;

@SuppressWarnings("serial")
@Setter
@Getter
@Entity
@Table(name = "MODULE")
public class Module extends GenericEntity<Long> {

  @Column(name = "ARTIFACT")
  private String artifact; // 格式為: [groupId]:[artifactId]

  @Column(name = "REQUIRES", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> requires; // 依賴的 dependency, 格式為: [groupId]:[artifactId]

  @Column(name = "ROOT_CONFIGS", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> rootConfigs;

  @Column(name = "REMOVE_ROOT_CONFIGS", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> removeRootConfigs;

  @Column(name = "SERVLET_CONFIGS", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> servletConfigs;

  @Column(name = "REMOVE_SERVLET_CONFIGS", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> removeServletConfigs;

  @Column(name = "SERVLET_FILTERS", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> servletFilters;

  @Column(name = "REMOVE_SERVLET_FILTERS", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> removeServletFilters;

  @Column(name = "DIRS", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> dirs;

  @JsonManagedReference("sources")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "module")
  private Collection<Source> sources;

  public void addSource(Source entity) {
    this.sources.add(entity);
    if (entity.getModule() != this) {
      entity.setModule(this);
    }
  }
}
