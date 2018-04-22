package com.netinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.netinfo.entity.SysRangeEntity;
import com.netinfo.repository.SysRangeRepository;

@Service
public class SysRangeService {

	@Autowired
	private SysRangeRepository sysRangeRepository;
	
	
	public Page<SysRangeEntity> findByRangeNameLike(String string,Pageable pageable)
	{
		return this.sysRangeRepository.findByRangeNameLike(string, pageable);
	}


	public Page<SysRangeEntity> rangeFilter( String filterStr,Pageable pageable)
	{
		return this.sysRangeRepository.rangeFilter(filterStr, pageable);
	}


	public Iterable<SysRangeEntity> getAllRange()
	{
		return this.sysRangeRepository.getAllRange();
	}
	
}
