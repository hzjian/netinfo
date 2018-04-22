package com.netinfo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysRoleEntity;

public interface SysRoleRepository extends PagingAndSortingRepository<SysRoleEntity, String> {

	Page<SysRoleEntity> findByRoleNameLike(String string,Pageable pageable);

	@Query("select a from SysRoleEntity a where a.roleId like %?1% or a.roleName like %?1% or a.roleDesc like %?1%") 
	public Page<SysRoleEntity> roleFilter( String filterStr,Pageable pageable);
}
