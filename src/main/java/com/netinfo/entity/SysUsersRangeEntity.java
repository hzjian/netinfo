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
 * The persistent class for the SYS_USERS_RANGE database table.
 * 
 */
@Entity
@Table(name = "SYS_USERS_RANGE")
@NamedQuery(name = "SysUsersRangeEntity.findAll", query = "SELECT s FROM SysUsersRangeEntity s")
public class SysUsersRangeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "RANGE_ID")
	private String rangeId;

	@Column(name = "USER_ID")
	private String userId;

	public SysUsersRangeEntity() {
	}

	public SysUsersRangeEntity(String userName, String string) {
		// TODO Auto-generated constructor stub
		this.userId = userName;
		this.rangeId = string;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRangeId() {
		return this.rangeId;
	}

	public void setRangeId(String rangeId) {
		this.rangeId = rangeId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}