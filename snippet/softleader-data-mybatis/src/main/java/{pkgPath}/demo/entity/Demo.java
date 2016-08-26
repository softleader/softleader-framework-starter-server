package {pkg}.demo.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;
import tw.com.softleader.commons.validation.constraints.AssertThat;
import tw.com.softleader.commons.validation.constraints.Latin;
import tw.com.softleader.data.entity.GenericCodeEntity;

/**
 * @see https://github.com/softleader/softleader-framework-docs/wiki/Bean-Validation
 */
@SuppressWarnings("serial")
@Setter
@Getter
@Table(name = "DEMO")
@AssertThat(
    value = "this.age > 0 && this.birthday != null && forName('java.time.Period').between(this.birthday, forName('java.time.LocalDate').now()).getYears() == this.age",
    propertyNode = "age", message = "{demo.birthday.and.age.not.match}")
public class Demo extends GenericCodeEntity<Long> {

  @Min(0)
  @Max(130)
  @Column(name = "age")
  private int age;

  @NotNull
  @Column(name = "birthday")
  private LocalDate birthday;

  @Email
  @Column(name = "email")
  private String email;

  @Transient
  private List<DemoAssociation> associations;

  @NotNull
  @Latin
  @Override
  public String getCode() { // You can add more JSR303 on GETTER to the fields defined in super class
    return super.getCode();
  }

}
