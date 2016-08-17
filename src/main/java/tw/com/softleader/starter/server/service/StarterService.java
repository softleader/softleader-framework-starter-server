package tw.com.softleader.starter.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.softleader.data.dao.CrudDao;
import tw.com.softleader.domain.AbstractCrudService;
import tw.com.softleader.starter.server.dao.StarterDao;
import tw.com.softleader.starter.server.entity.Starter;

@Service
public class StarterService extends AbstractCrudService<Starter, Long> {

  @Autowired
  private StarterDao dao;

  public CrudDao<Starter, Long> getDao() {
    return dao;
  }

  public Starter getAvailableOne() {
    return dao.findTopByOrderByIdDesc();
  }

}
