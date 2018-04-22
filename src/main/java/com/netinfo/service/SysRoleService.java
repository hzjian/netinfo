package com.netinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.netinfo.entity.SysRoleEntity;
import com.netinfo.entity.SysRolesLayerEntity;
import com.netinfo.entity.SysRolesResourceEntity;
import com.netinfo.repository.SysRoleRepository;
import com.netinfo.repository.SysRolesLayerRepository;
import com.netinfo.repository.SysRolesResourceRepository;

@Service
public class SysRoleService {

	 	@Autowired
	 	private SysRoleRepository sysRoleRepository;
	 	
		 @Autowired
		 private SysRolesLayerRepository sysRolesLayerRepository;
	 	
		 @Autowired
		 private SysRolesResourceRepository sysRolesResourceRepository;
	 
	    public SysRoleEntity findById(String roleId) {
	        return sysRoleRepository.findOne(roleId);
	    }
	    
	    public Page<SysRoleEntity> getRoleList(PageRequest pageInfo) {
			return this.sysRoleRepository.findAll(pageInfo);
		}
	    
	    public Page<SysRoleEntity> getRoleList(String filterStr,PageRequest pageInfo) {
			return this.sysRoleRepository.roleFilter(filterStr,pageInfo);
		}

		public SysRoleEntity save(SysRoleEntity role) {
			return this.sysRoleRepository.save(role);
		}
		
		public void delete(SysRoleEntity role)
		{
			this.sysRoleRepository.delete(role);
			this.sysRolesLayerRepository.deleteByRoleId(role.getRoleId());
			this.sysRolesResourceRepository.deleteByRoleId(role.getRoleId());
		}
		
		public void deleteLayerByRoleId(SysRoleEntity role)
		{

			this.sysRolesLayerRepository.deleteByRoleId(role.getRoleId());
		}
		
		
		public void deleteMenuByRoleId(SysRoleEntity role)
		{
			this.sysRolesResourceRepository.deleteByRoleId(role.getRoleId());
		}
		
		public List<SysRolesLayerEntity> getRoleLayerList(String roleId) {
			return this.sysRolesLayerRepository.findByRoleId(roleId);
		}

		public void saveRoleLayerRelate(SysRolesLayerEntity entity) {
			this.sysRolesLayerRepository.save(entity);
			
		}

		public List<SysRolesResourceEntity> getRoleMenuList(String roleId) {
			// TODO Auto-generated method stub
			return this.sysRolesResourceRepository.findByRoleId(roleId);
		}

		public void saveRoleResourceRelate(SysRolesResourceEntity sysRolesResourceEntity) {
			// TODO Auto-generated method stub
			this.sysRolesResourceRepository.save(sysRolesResourceEntity);
		}

		public Iterable<SysRoleEntity> getAll() {
			// TODO Auto-generated method stub
			return this.sysRoleRepository.findAll();
		}
	 
}
