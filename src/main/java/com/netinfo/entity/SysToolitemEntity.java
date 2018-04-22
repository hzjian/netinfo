package com.netinfo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the SYS_Toolitem database table.
 * 
 */
@Entity
@Table(name = "SYS_Toolitem")
@NamedQuery(name = "SysToolitemEntity.findAll", query = "SELECT s FROM SysToolitemEntity s")
public class SysToolitemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private String id;
	
	private String name;

	private Integer enabled;

	private String classname;
	
	private String layerid;
	
	private String tooltip;
	
	@Transient
	private Integer worktype = 2;
	
	
	public SysToolitemEntity() {
	}


	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the classname
	 */
	public String getClassname() {
		return classname;
	}



	/**
	 * @param classname the classname to set
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}



	/**
	 * @return the enabled
	 */
	public Integer getEnabled() {
		return enabled;
	}


	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}



	/**
	 * @return the layerid
	 */
	public String getLayerid() {
		return layerid;
	}


	/**
	 * @param layerid the layerid to set
	 */
	public void setLayerid(String layerid) {
		this.layerid = layerid;
	}


	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}


	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}



	/**
	 * @return the worktype
	 */
	public Integer getWorktype() {
		return worktype;
	}

	
	
}