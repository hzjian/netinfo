package com.netinfo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysUsersRangeEntity;

public interface SysUsersRangeRepository extends PagingAndSortingRepository<SysUsersRangeEntity, Long> 
{
	
	public List<SysUsersRangeEntity> findByUserId(String  userId);

	public void deleteByUserId(String userId);


}
