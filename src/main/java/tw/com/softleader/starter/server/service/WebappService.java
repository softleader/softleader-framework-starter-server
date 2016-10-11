package tw.com.softleader.starter.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.softleader.data.dao.CrudDao;
import tw.com.softleader.domain.AbstractCrudService;
import tw.com.softleader.starter.server.dao.WebappDao;
import tw.com.softleader.starter.server.entity.Webapp;

@Service
public class WebappService extends AbstractCrudService<Webapp, Long> {

  @Autowired
  private WebappDao dao;

  public CrudDao<Webapp, Long> getDao() {
    return dao;
  }

  public Webapp getAvailableOne() {
    return dao.findTopByOrderByIdDesc();
  }

}
