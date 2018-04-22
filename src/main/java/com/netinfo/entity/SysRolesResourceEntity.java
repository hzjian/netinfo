package com.netinfo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the SYS_ROLES_RESOURCES database table.
 * 
 */
@Entity
@Table(name = "SYS_ROLES_RESOURCES")
@NamedQuery(name = "SysRolesResourceEntity.findAll", query = "SELECT s FROM SysRolesResourceEntity s")
public class SysRolesResourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name = "SYS_ROLES_RESOURCES_ID_GENERATOR", sequenceName = "SEQ_MNGR")
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYS_ROLES_RESOURCES_ID_GENERATOR")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Integer enabled;

	@Column(name = "RESOURCE_ID")
	private String resourceId;

	@Column(name = "ROLE_ID")
	private String roleId;

	public SysRolesResourceEntity() {
	}

	public SysRolesResourceEntity(String roleId, String resourceId) {
		// TODO Auto-generated constructor stub
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}