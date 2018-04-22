package com.netinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netinfo.entity.SysRelateFileEntity;
import com.netinfo.repository.SysRelateFileRepository;


@Service
public class SysRelateFileService {

	
	@Autowired
	private SysRelateFileRepository sysRelateFileRepository ;
	
	public List<SysRelateFileEntity> findByRelateid(String rid)
	{
		return this.sysRelateFileRepository.findByRelateid(rid);
	}
	
	public SysRelateFileEntity saveRelateFileEntity(SysRelateFileEntity entity)
	{
		return this.sysRelateFileRepository.save(entity);
	}

	public String getFileNameById(String name) {
		// TODO Auto-generated method stub
		SysRelateFileEntity tmp = this.sysRelateFileRepository.findOne(name);
		return tmp.getFname();
	}

	public void deleteFile(String fileid) {
		// TODO Auto-generated method stub
		this.sysRelateFileRepository.delete(fileid);
		
	}
	
}
