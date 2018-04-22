package com.netinfo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysRolesLayerEntity;

public interface SysRolesLayerRepository extends PagingAndSortingRepository<SysRolesLayerEntity, Long> 
{
	
	public List<SysRolesLayerEntity> findByRoleId(String  roleId);

	public void deleteByRoleId(String roleId);


}
