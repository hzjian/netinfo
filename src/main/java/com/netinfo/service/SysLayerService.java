package com.netinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.netinfo.entity.SysLayerEntity;
import com.netinfo.entity.SysLayerFieldEntity;
import com.netinfo.entity.ViewUserLayerEntity;
import com.netinfo.repository.SysLayerFieldRepository;
import com.netinfo.repository.SysLayerRepository;
import com.netinfo.repository.ViewUserLayerRepository;

@Service
public class SysLayerService {

	@Autowired
	private SysLayerRepository sysLayerRepositry;
	
	@Autowired
	private ViewUserLayerRepository viewUserLayerRepository;
	
	@Autowired
	private SysLayerFieldRepository sysLayerFieldRepository;

	public Page<SysLayerEntity> getAllLayer(PageRequest pageInfo) {
		return this.sysLayerRepositry.findAll(pageInfo);
	}
	
	public Iterable<SysLayerEntity> getAllLayer() {
		return this.sysLayerRepositry.findAll();
	}

	public SysLayerEntity findById(String layerId) {
		return sysLayerRepositry.findOne(layerId);
	}

	public SysLayerEntity save(SysLayerEntity layer) {
		return this.sysLayerRepositry.save(layer);
	}

	public SysLayerEntity findOne(String layerId) {
		// TODO Auto-generated method stub
		return this.sysLayerRepositry.findOne(layerId);
	}
	
	public void delete(SysLayerEntity layer)
	{
		this.sysLayerRepositry.delete(layer);
	}
	
	public List<ViewUserLayerEntity> getUserLayer(String userId)
	{
		return this.viewUserLayerRepository.getUserLayerList(userId);
	}
	
	public List<SysLayerFieldEntity> getLayerFieldList(String layerId)
	{
		return this.sysLayerFieldRepository.findByLayerIdOrderBySeq(layerId);
	}
}
