package com.netinfo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysResourceEntity;

public interface SysResourceRepository extends PagingAndSortingRepository<SysResourceEntity, String>{


	Page<SysResourceEntity> findByResourceDescLike(String string,Pageable pageable);

	@Query("select a from SysResourceEntity a where a.resourceId like %?1% or a.resourceDesc like %?1% ") 
	public Page<SysResourceEntity> resourceFilter( String filterStr,Pageable pageable);

	@Query("select a from SysResourceEntity a  order by a.priority")
	Iterable<SysResourceEntity> getAllMenu();
}
