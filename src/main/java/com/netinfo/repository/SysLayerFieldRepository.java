package com.netinfo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.SysLayerFieldEntity;

public interface SysLayerFieldRepository extends PagingAndSortingRepository<SysLayerFieldEntity, String> {

	public List<SysLayerFieldEntity> findByLayerIdOrderBySeq(String  layerId);
}
