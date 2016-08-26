package {pkg}.demo.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import {pkg}.demo.dao.DemoAssociationDao;
import {pkg}.demo.dao.DemoDao;
import {pkg}.demo.entity.Demo;
import {pkg}.demo.service.DemoService;
import tw.com.softleader.data.dao.CrudCodeDao;
import tw.com.softleader.domain.AbstractCrudCodeService;
import tw.com.softleader.domain.exception.ValidationException;
import tw.com.softleader.domain.guarantee.constraints.EntityUnique;
import tw.com.softleader.domain.guarantee.constraints.EntityUpToDate;

/**
 * @see https://github.com/softleader/softleader-framework-docs/wiki/Entity-Guarantee
 */
@Service
public class DemoServiceImpl extends AbstractCrudCodeService<Demo, Long> implements DemoService {

  @Autowired
  private DemoDao demoDao;

  @Autowired
  private DemoAssociationDao demoAssociationDao;

  @Override
  public CrudCodeDao<Demo, Long> getDao() {
    return demoDao;
  }
  
  @Override
  public void delete(Demo entity) throws ValidationException {
    demoAssociationDao.deleteByDemoId(entity.getId());
    super.delete(entity);
  }

  @Override
  public void delete(Collection<Long> ids) throws ValidationException {
    ids.stream().map(id->{
      Demo entity = new Demo();
      entity.setId(id);
      return entity;
    }).forEach(this::delete);
  }

  @Override
  public Demo save(@EntityUnique @EntityUpToDate Demo entity) throws ValidationException {
    Demo saved = super.save(entity);
    if (entity.getAssociations() != null) {
      saved.setAssociations(entity.getAssociations().stream().map(asso -> {
        asso.setDemoId(saved.getId());
        return demoAssociationDao.save(asso);
      }).collect(toList()));
    }
    return saved;
  }

}
