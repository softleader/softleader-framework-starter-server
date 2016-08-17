package tw.com.softleader.starter.server.dao;

import tw.com.softleader.data.dao.GenericCrudDao;
import tw.com.softleader.starter.server.entity.Webapp;

public interface WebappDao extends GenericCrudDao<Webapp, Long> {

  public Webapp findTopByOrderByIdDesc();

}
