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
 * The persistent class for the SYS_ROLES_LAYERS database table.
 * 
 */
@Entity
@Table(name = "SYS_ROLES_LAYERS")
@NamedQuery(name = "SysRolesLayerEntity.findAll", query = "SELECT s FROM SysRolesLayerEntity s")
public class SysRolesLayerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "LAYER_ID")
	private String layerId;

	@Column(name = "ROLE_ID")
	private String roleId;

	public SysRolesLayerEntity() {
	}

	public SysRolesLayerEntity(String roleId, String layerId) {
		// TODO Auto-generated constructor stub
		this.roleId = roleId;
		this.layerId = layerId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLayerId() {
		return this.layerId;
	}

	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}