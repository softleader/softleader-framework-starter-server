package tw.com.softleader.starter.server.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

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

  @Column(name = "SNIPPETS", length = 4000)
  @Convert(converter = StringJoiningConverter.class)
  private Collection<String> snippets; // 可以是一個目錄或一個檔案

}
