package {pkg}.demo.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import tw.com.softleader.data.entity.GenericEntity;

/**
 * @see https://github.com/softleader/softleader-framework-docs/wiki/Bean-Validation
 */
@SuppressWarnings("serial")
@Setter
@Getter
@Table(name = "DEMO_ASSOCIATION")
public class DemoAssociation extends GenericEntity<Long> {

  @Column(name = "DEMO_ID")
  private long demoId;

}
