package com.netinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netinfo.entity.SysUserEntity;
import com.netinfo.repository.SysUserRepository;

@Component
public class SysEnviService {
	
	@Autowired
	private SysUserRepository sysUserRepository;

	private Iterable<SysUserEntity> userList;

	public void loadInitData() {
		
		this.userList = this.sysUserRepository.findAll();

	}

	public SysUserEntity getUserById(String userId)
	{
		for (SysUserEntity user: this.userList)
		{
			if(user.getUserId().equalsIgnoreCase(userId))
				return user;
		}
		return null;
	}
}
