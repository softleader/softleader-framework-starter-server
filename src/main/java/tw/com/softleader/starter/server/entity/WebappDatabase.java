package tw.com.softleader.starter.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@SuppressWarnings("serial")
@Setter
@Getter
@Entity
@Table(name = "WEBAPP_DATABASE")
@NoArgsConstructor
public class WebappDatabase extends GenericEntity<Long> implements EntityJsonIgnore<Long> {

  public WebappDatabase(String name, String groupId, String artifactId, String version,
      String driver, boolean dft, boolean enabled, String urlHint) {
    super();
    this.name = name;
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.driver = driver;
    this.dft = dft;
    this.enabled = enabled;
    this.urlHint = urlHint;
  }

  @JsonBackReference("databases")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "WEBAPP_ID")
  private Webapp webapp;

  @Column(name = "NAME")
  private String name;

  @Column(name = "GROUP_ID")
  private String groupId;

  @Column(name = "ARTIFACT_ID")
  private String artifactId;

  @Column(name = "VERSION")
  private String version;

  @Column(name = "DRIVER")
  private String driver;

  @Column(name = "DFT")
  private boolean dft;

  @Column(name = "ENABLED")
  private boolean enabled;

  @Column(name = "URL_HINT")
  private String urlHint;
}
