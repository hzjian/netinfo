package com.netinfo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysToolitemEntity;

public interface SysToolitemRepository extends PagingAndSortingRepository<SysToolitemEntity, String> 
{

	public List<SysToolitemEntity> findByLayerid(String  layerid);
}
