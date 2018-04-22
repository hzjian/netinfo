package com.netinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.StRangeProvinceEntity;

public interface StRangeProvinceRepository extends PagingAndSortingRepository<StRangeProvinceEntity, Long> {

	public List<StRangeProvinceEntity> findByPid(Long pid);

	@Query("select a from StRangeProvinceEntity a where a.nameeng like %?1%  or a.namechn like %?1%  or a.namepy like %?1%") 
	public Iterable<StRangeProvinceEntity> getListFilterByName(String term);
}
