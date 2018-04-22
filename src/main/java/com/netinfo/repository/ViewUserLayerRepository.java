package com.netinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.netinfo.entity.ViewUserLayerEntity;
import com.netinfo.entity.ViewUserLayerPK;

public interface ViewUserLayerRepository extends CrudRepository<ViewUserLayerEntity, ViewUserLayerPK>{
	
	@Query("select a from ViewUserLayerEntity a where a.id.userId = ?1 order by a.seq ") 
	public List<ViewUserLayerEntity> getUserLayerList( String userId);


}
