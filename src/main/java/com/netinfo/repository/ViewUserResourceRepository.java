package com.netinfo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.netinfo.entity.ViewUserResourceEntity;
import com.netinfo.entity.ViewUserResourcePK;

public interface ViewUserResourceRepository extends CrudRepository<ViewUserResourceEntity, ViewUserResourcePK>{
	
	@Query("select a from ViewUserResourceEntity a where a.id.userId = ?1 order by a.priority ") 
	public List<ViewUserResourceEntity> getUserLayerList( String userId);


}
