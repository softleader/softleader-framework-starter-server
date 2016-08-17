package tw.com.softleader.starter.server.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import tw.com.softleader.data.entity.EntityJsonIgnore;
import tw.com.softleader.data.entity.GenericEntity;
import tw.com.softleader.starter.server.enums.SwtLayout;
import tw.com.softleader.starter.server.enums.SwtStyle;

@SuppressWarnings("serial")
@Setter
@Getter
@Entity
@Table(name = "STARTER")
public class Starter extends GenericEntity<Long> implements EntityJsonIgnore<Long> {

  @Column(name = "revision")
  private long revision;

  @Column(name = "BASE_URL")
  private String baseUrl;

  @Column(name = "PROJECT_GROUP_ID")
  private String projectGroupId;

  @Column(name = "PROJECT_ARTIFACT_ID")
  private String projectArtifactId;

  @Column(name = "PROJECT_VERSION")
  private String projectVersion;

  @Column(name = "PROJECT_DESC")
  private String projectDesc;

  @Column(name = "PROJECT_PKG")
  private String projectPkg;

  @Column(name = "VERSION_TEXT")
  private String versionText;

  @Column(name = "VERSION_LAYOUT")
  @Enumerated(EnumType.STRING)
  private SwtLayout versionLayout = SwtLayout.V;

  @Column(name = "VERSION_STYLE")
  @Enumerated(EnumType.STRING)
  private SwtStyle versionStyle;

  @JsonManagedReference("versions")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "starter")
  private Collection<StarterVersion> versions;

  public void addVersion(StarterVersion entity) {
    this.versions.add(entity);
    if (entity.getStarter() != this) {
      entity.setStarter(this);
    }
  }

  @JsonManagedReference("modules")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "starter")
  private Collection<StarterModule> modules;

  public void addModule(StarterModule entity) {
    this.modules.add(entity);
    if (entity.getStarter() != this) {
      entity.setStarter(this);
    }
  }

  @Column(name = "DATABASE_TEXT")
  private String databaseText;

  @Column(name = "DATABASE_LAYOUT")
  @Enumerated(EnumType.STRING)
  private SwtLayout databaseLayout = SwtLayout.V;

  @Column(name = "DATABASE_STYLE")
  @Enumerated(EnumType.STRING)
  private SwtStyle databaseStyle;

  @JsonManagedReference("databases")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "starter")
  private Collection<StarterDatabase> databases;

  public void addDatabase(StarterDatabase entity) {
    this.databases.add(entity);
    if (entity.getStarter() != this) {
      entity.setStarter(this);
    }
  }

}
