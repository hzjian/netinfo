package com.netinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.StRangeDistrictEntity;

public interface StRangeDistrictRepository extends PagingAndSortingRepository<StRangeDistrictEntity, Long> {

	public List<StRangeDistrictEntity> findByPid(Long pid);
	
	@Query("select a from StRangeDistrictEntity a where a.nameeng like %?1%  or a.namechn like %?1%  or a.namepy like %?1%") 
	public Iterable<StRangeDistrictEntity> getListFilterByName(String term);
}
