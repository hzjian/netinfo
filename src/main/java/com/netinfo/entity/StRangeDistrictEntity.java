package com.netinfo.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ST_RANGE_DISTRICT database table.
 * 
 */
@Entity
@Table(name="ST_RANGE_DISTRICT")
@NamedQuery(name="StRangeDistrictEntity.findAll", query="SELECT s FROM StRangeDistrictEntity s")
public class StRangeDistrictEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long objectid;

	private String code;

	private String type;
	
	@Column(name="NAME_ENG")
	private String nameeng;

	@Column(name="NAME_CHN")
	private String namechn;
	
	@Column(name="NAME_PY")
	private String namepy;
	
	private Long pid;
	
	public StRangeDistrictEntity() {
	}

	/**
	 * @return the objectid
	 */
	public Long getObjectid() {
		return objectid;
	}

	/**
	 * @param objectid the objectid to set
	 */
	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the nameeng
	 */
	public String getNameeng() {
		return nameeng;
	}

	/**
	 * @param nameeng the nameeng to set
	 */
	public void setNameeng(String nameeng) {
		this.nameeng = nameeng;
	}

	/**
	 * @return the namechn
	 */
	public String getNamechn() {
		return namechn;
	}

	/**
	 * @param namechn the namechn to set
	 */
	public void setNamechn(String namechn) {
		this.namechn = namechn;
	}

	/**
	 * @return the namepy
	 */
	public String getNamepy() {
		return namepy;
	}

	/**
	 * @param namepy the namepy to set
	 */
	public void setNamepy(String namepy) {
		this.namepy = namepy;
	}

	/**
	 * @return the pid
	 */
	public Long getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(Long pid) {
		this.pid = pid;
	}

	
}