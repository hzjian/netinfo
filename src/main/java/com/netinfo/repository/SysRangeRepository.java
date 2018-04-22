package com.netinfo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysRangeEntity;

public interface SysRangeRepository extends PagingAndSortingRepository<SysRangeEntity, String>{


	Page<SysRangeEntity> findByRangeNameLike(String string,Pageable pageable);

	@Query("select a from SysRangeEntity a where a.rangeId like %?1% or a.rangeName like %?1% ") 
	public Page<SysRangeEntity> rangeFilter( String filterStr,Pageable pageable);

	@Query("select a from SysRangeEntity a  order by a.rangeName")
	Iterable<SysRangeEntity> getAllRange();
}