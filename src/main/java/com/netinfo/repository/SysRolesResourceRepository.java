package com.netinfo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysRolesResourceEntity;

public interface SysRolesResourceRepository extends PagingAndSortingRepository<SysRolesResourceEntity, Long> 
{
	
	public List<SysRolesResourceEntity> findByRoleId(String  roleId);

	public void deleteByRoleId(String roleId);


}
