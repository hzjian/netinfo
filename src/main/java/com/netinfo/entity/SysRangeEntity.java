package com.netinfo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the SysRangeEntity database table.
 * 
 */
@Entity
@Table(name = "SYS_RANGE")
@NamedQuery(name = "SysRangeEntity.findAll", query = "SELECT s FROM SysRangeEntity s")
public class SysRangeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name = "RANGE_ID")
	private String rangeId;

	@Column(name = "PARENT_ID")
	private String parentId;

	@Column(name = "RANGE_NAME")
	private String rangeName;

	public SysRangeEntity() {
	}


	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	/**
	 * @return the rangeId
	 */
	public String getRangeId() {
		return rangeId;
	}


	/**
	 * @param rangeId the rangeId to set
	 */
	public void setRangeId(String rangeId) {
		this.rangeId = rangeId;
	}


	/**
	 * @return the rangeName
	 */
	public String getRangeName() {
		return rangeName;
	}


	/**
	 * @param rangeName the rangeName to set
	 */
	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}


}