package com.netinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.netinfo.entity.SysResourceEntity;
import com.netinfo.entity.ViewUserResourceEntity;
import com.netinfo.repository.SysResourceRepository;
import com.netinfo.repository.ViewUserResourceRepository;

@Service
public class SysResourceService {

	@Autowired
	private SysResourceRepository sysResourceRepository;
	
	@Autowired
	private ViewUserResourceRepository viewUserResourceRepository;
	
	public SysResourceEntity findById(String resourceId) {
        return sysResourceRepository.findOne(resourceId);
    }
    
    public Page<SysResourceEntity> getResourceList(PageRequest pageInfo) {
		return this.sysResourceRepository.findAll(pageInfo);
	}
    
    public Page<SysResourceEntity> getResourceList(String filterStr,PageRequest pageInfo) {
		return this.sysResourceRepository.resourceFilter(filterStr,pageInfo);
	}

	public SysResourceEntity save(SysResourceEntity resource) {
		return this.sysResourceRepository.save(resource);
	}
	
	public void delete(SysResourceEntity resource)
	{
		this.sysResourceRepository.delete(resource);
	}

	public Iterable<SysResourceEntity> getAllMenu() {
		// TODO Auto-generated method stub
		return this.sysResourceRepository.getAllMenu();
	}

	public List<ViewUserResourceEntity> getUserResourceList(String userId)
	{
		return this.viewUserResourceRepository.getUserLayerList(userId);
	}
}
