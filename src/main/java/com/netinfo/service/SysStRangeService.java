package com.netinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netinfo.entity.StRangeCityEntity;
import com.netinfo.entity.StRangeDistrictEntity;
import com.netinfo.entity.StRangeProvinceEntity;
import com.netinfo.repository.StRangeCityRepository;
import com.netinfo.repository.StRangeDistrictRepository;
import com.netinfo.repository.StRangeProvinceRepository;

@Service
public class SysStRangeService {


	@Autowired
	private StRangeCityRepository stRangeCityRepository;
	
	@Autowired
	private StRangeProvinceRepository stRangeProvinceRepository;
	
	@Autowired
	private StRangeDistrictRepository stRangeDistrictRepository;
	
	
	public Iterable<StRangeProvinceEntity> getAllProvince()
	{
		return this.stRangeProvinceRepository.findAll();
	}
	
	public List<StRangeCityEntity> getCityListByProvinceId(Long pid)
	{
		return this.stRangeCityRepository.findByPid(pid);
	}
	
	public List<StRangeDistrictEntity> getDistrictListByCityId(Long pid)
	{
		return this.stRangeDistrictRepository.findByPid(pid);
	}

	public Iterable<StRangeProvinceEntity> filterByName(String term) {

		return this.stRangeProvinceRepository.getListFilterByName(term);
	}

	public Iterable<StRangeCityEntity> getCityListFilterByTerm(String term) {
		// TODO Auto-generated method stub
		return this.stRangeCityRepository.getListFilterByName(term);
	}
	
	public Iterable<StRangeDistrictEntity> getDistrictListFilterByTerm(String term) {
		// TODO Auto-generated method stub
		return this.stRangeDistrictRepository.getListFilterByName(term);
	}

	public StRangeProvinceEntity getProviceById(Long id) {
		return this.stRangeProvinceRepository.findOne(id);
		
	}
	
	public StRangeCityEntity getCityById(Long id) {
		return this.stRangeCityRepository.findOne(id);
		
	}
	
}
