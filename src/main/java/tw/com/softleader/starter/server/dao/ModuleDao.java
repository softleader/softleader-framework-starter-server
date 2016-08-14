package tw.com.softleader.starter.server.dao;

import java.util.List;

import tw.com.softleader.data.dao.GenericCrudDao;
import tw.com.softleader.starter.server.entity.Module;

public interface ModuleDao extends GenericCrudDao<Module, Long> {

  public List<Module> findByArtifact(String artifact);

  /**
   * 也就是 global 的設定, 適用於所有專案
   * 
   * @return
   */
  public List<Module> findByArtifactIsNull();

}
