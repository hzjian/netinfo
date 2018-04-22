package com.netinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.netinfo.entity.StRangeCityEntity;

public interface StRangeCityRepository extends PagingAndSortingRepository<StRangeCityEntity, Long> {

	public List<StRangeCityEntity> findByPid(Long pid);

	@Query("select a from StRangeCityEntity a where a.nameeng like %?1%  or a.namechn like %?1%  or a.namepy like %?1%") 
	public Iterable<StRangeCityEntity> getListFilterByName(String term);
}
