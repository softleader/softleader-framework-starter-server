package {pkg}.demo.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import {pkg}.demo.entity.DemoAssociation;
import tw.com.softleader.data.dao.GenericCrudDao;
import tw.com.softleader.data.mybatis.provider.SqlProvider;

@Repository
public interface DemoAssociationDao extends GenericCrudDao<Long, DemoAssociation> {

  // You have to override save method to define useGeneratedKeys ONLY if you use AUTO id.strategy in
  // datasource.properties
  @Override
  @InsertProvider(type = SqlProvider.class, method = "insert")
  @Options(flushCache = true, useGeneratedKeys = true)
  <S extends DemoAssociation> S save(S entity);
  
  void deleteByDemoId(long demoId);
}
