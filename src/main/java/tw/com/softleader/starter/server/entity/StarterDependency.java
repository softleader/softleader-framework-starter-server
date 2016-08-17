package tw.com.softleader.starter.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.com.softleader.data.entity.EntityJsonIgnore;
import tw.com.softleader.data.entity.GenericEntity;
import tw.com.softleader.starter.server.enums.MvnScope;

@SuppressWarnings("serial")
@Setter
@Getter
@Entity
@Table(name = "STARTER_DEPENDENCY")
@NoArgsConstructor
public class StarterDependency extends GenericEntity<Long> implements EntityJsonIgnore<Long> {

  @JsonBackReference("dependencies")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MODULE_ID")
  private StarterModule module;

  public StarterDependency(String groupId, String artifactId, String version, MvnScope scope,
      boolean dft, boolean enabled) {
    super();
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.scope = scope;
    this.dft = dft;
    this.enabled = enabled;
  }

  @Column(name = "GROUP_ID")
  private String groupId;

  @Column(name = "ARTIFACT_ID")
  private String artifactId;

  @Column(name = "VERSION")
  private String version;

  @Column(name = "SCOPE")
  @Enumerated(EnumType.STRING)
  private MvnScope scope;

  @Column(name = "DFT")
  private boolean dft;

  @Column(name = "ENABLED")
  private boolean enabled = true;
}
