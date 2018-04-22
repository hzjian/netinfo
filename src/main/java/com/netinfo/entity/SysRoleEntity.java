package com.netinfo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the SYS_ROLES database table.
 * 
 */
@Entity
@Table(name = "SYS_ROLES")
@NamedQuery(name = "SysRoleEntity.findAll", query = "SELECT s FROM SysRoleEntity s")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROLE_ID")
	private String roleId;

	private Integer enabled;

	private Integer issys;

	@Column(name = "ROLE_DESC")
	private String roleDesc;

	@Column(name = "ROLE_NAME")
	private String roleName;
	
	private String module;
	
	@Transient
	private String[] layerIds;
	
	
	@Transient
	private String[] menuIds;
	
	public SysRoleEntity() {
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getIssys() {
		return issys;
	}

	public void setIssys(Integer issys) {
		this.issys = issys;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the layerIds
	 */
	public String[] getLayerIds() {
		return layerIds;
	}

	/**
	 * @param layerIds the layerIds to set
	 */
	public void setLayerIds(String[] layerIds) {
		this.layerIds = layerIds;
	}

	/**
	 * @return the menuIds
	 */
	public String[] getMenuIds() {
		return menuIds;
	}

	/**
	 * @param menuIds the menuIds to set
	 */
	public void setMenuIds(String[] menuIds) {
		this.menuIds = menuIds;
	}
	
	
	
}