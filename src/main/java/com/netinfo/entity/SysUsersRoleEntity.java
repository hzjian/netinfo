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
 * The persistent class for the SYS_USERS_ROLES database table.
 * 
 */
@Entity
@Table(name = "SYS_USERS_ROLES")
@NamedQuery(name = "SysUsersRoleEntity.findAll", query = "SELECT s FROM SysUsersRoleEntity s")
public class SysUsersRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "ROLE_ID")
	private String roleId;

	@Column(name = "USER_ID")
	private String userId;

	public SysUsersRoleEntity() {
	}

	public SysUsersRoleEntity(String userId, String roleId) {
		// TODO Auto-generated constructor stub
		this.userId = userId;
		this.roleId = roleId;

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}