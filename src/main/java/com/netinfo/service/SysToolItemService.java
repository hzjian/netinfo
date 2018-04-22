package com.netinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netinfo.entity.SysToolitemEntity;
import com.netinfo.repository.SysToolitemRepository;

@Service
public class SysToolItemService {

	@Autowired
	private SysToolitemRepository sysToolitemRepository;
	
	public List<SysToolitemEntity> findByLayerid(String  layerid)
	{
		return this.sysToolitemRepository.findByLayerid(layerid);
	}
}
