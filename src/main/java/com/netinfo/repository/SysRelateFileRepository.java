package com.netinfo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysRelateFileEntity;

public interface SysRelateFileRepository extends PagingAndSortingRepository<SysRelateFileEntity, String> {

	public List<SysRelateFileEntity> findByRelateid(String rid);

}
