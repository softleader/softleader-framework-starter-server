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
@Table(name = "STARTER_VERSION")
@NoArgsConstructor
public class StarterVersion extends GenericEntity<Long> implements EntityJsonIgnore<Long> {

  public StarterVersion(String sl, String io, boolean dft, boolean enabled) {
    super();
    this.sl = sl;
    this.io = io;
    this.dft = dft;
    this.enabled = enabled;
  }

  @JsonBackReference("versions")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "STARTER_ID")
  private Starter starter;

  @Column(name = "SL")
  private String sl;

  @Column(name = "IO")
  private String io;

  @Column(name = "DFT")
  private boolean dft;

  @Column(name = "ENABLED")
  private boolean enabled;
}
