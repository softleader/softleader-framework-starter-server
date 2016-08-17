package tw.com.softleader.starter.server.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "STARTER_DEPENDENCY")
public class StarterModule extends GenericEntity<Long> implements EntityJsonIgnore<Long> {

  @JsonBackReference("modules")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "STARTER_ID")
  private Starter starter;

  @Column(name = "DEPENDENCY_TEXT")
  private String dependencyText;

  @Column(name = "DEPENDENCY_LAYOUT")
  @Enumerated(EnumType.STRING)
  private SwtLayout dependencyLayout = SwtLayout.V;

  @Column(name = "DEPENDENCY_STYLE")
  @Enumerated(EnumType.STRING)
  private SwtStyle dependencyStyle;

  @JsonManagedReference("modules")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "module")
  private Collection<StarterDependency> dependencies;

  public void addDependency(StarterDependency entity) {
    this.dependencies.add(entity);
    if (entity.getModule() != this) {
      entity.setModule(this);
    }
  }

}
