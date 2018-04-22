package com.netinfo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysUsersRoleEntity;

public interface SysUsersRoleRepositry extends PagingAndSortingRepository<SysUsersRoleEntity, Long> 
{
	
	public List<SysUsersRoleEntity> findByUserId(String  userId);

	public void deleteByUserId(String userId);


}
