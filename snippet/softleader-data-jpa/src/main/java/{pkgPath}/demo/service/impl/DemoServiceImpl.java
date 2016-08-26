package {pkg}.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.softleader.data.dao.CrudCodeDao;
import tw.com.softleader.domain.AbstractCrudCodeService;
import tw.com.softleader.domain.exception.ValidationException;
import tw.com.softleader.domain.guarantee.constraints.EntityUnique;
import tw.com.softleader.domain.guarantee.constraints.EntityUpToDate;
import {pkg}.demo.dao.DemoDao;
import {pkg}.demo.entity.Demo;
import {pkg}.demo.service.DemoService;

/**
 * @see https://github.com/softleader/softleader-framework-docs/wiki/Entity-Guarantee
 */
@Service
public class DemoServiceImpl extends AbstractCrudCodeService<Demo, Long>
    implements DemoService {

  @Autowired
  private DemoDao demoDao;

  @Override
  public CrudCodeDao<Demo, Long> getDao() {
    return demoDao;
  }

  @Override
  public Demo save(@EntityUnique @EntityUpToDate Demo entity)
      throws ValidationException {
    return super.save(entity);
  }

}
