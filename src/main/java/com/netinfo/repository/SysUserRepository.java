package com.netinfo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysUserEntity;

public interface SysUserRepository extends PagingAndSortingRepository<SysUserEntity, String> {

    public List<SysUserEntity> findByUserCnname(String  cnname);

	public Optional<SysUserEntity> findByUserId(String userId);
	
	public List<SysUserEntity> findByCtaskId(Long  ctaskId);


}
