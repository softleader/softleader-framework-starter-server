package tw.com.softleader.starter.server.dao;

import tw.com.softleader.data.dao.GenericCrudDao;
import tw.com.softleader.starter.server.entity.Starter;

public interface StarterDao extends GenericCrudDao<Starter, Long> {

  public Starter findTopByOrderByIdDesc();

}
