package {pkg}.demo.service;

import tw.com.softleader.domain.CrudCodeService;
import tw.com.softleader.domain.exception.ValidationException;
import tw.com.softleader.domain.guarantee.constraints.EntityUnique;
import tw.com.softleader.domain.guarantee.constraints.EntityUpToDate;
import {pkg}.demo.entity.Demo;

public interface DemoService extends CrudCodeService<Demo, Long> {

  @Override
  Demo save(@EntityUnique @EntityUpToDate Demo entity) throws ValidationException;

}
