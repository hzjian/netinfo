package com.netinfo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the SYS_RESOURCES database table.
 * 
 */
@Entity
@Table(name = "SYS_RESOURCES")
@NamedQuery(name = "SysResourceEntity.findAll", query = "SELECT s FROM SysResourceEntity s")
public class SysResourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name = "RESOURCE_ID")
	private String resourceId;

	private Integer enabled;

	private Integer issys;

	@Column(name = "PARENT_ID")
	private String parentId;

	private Integer priority;

	@Column(name = "RESOURCE_DESC")
	private String resourceDesc;


	@Column(name = "RESOURCE_PATH")
	private String resourcePath;

	@Column(name = "RESOURCE_LOADSTR")
	private String resourceLoadstr;
	
	@Column(name = "RESOURCE_TYPE")
	private String resourceType;

	public SysResourceEntity() {
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getResourcePath() {
		return this.resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the resourceLoadstr
	 */
	public String getResourceLoadstr() {
		return resourceLoadstr;
	}

	/**
	 * @param resourceLoadstr the resourceLoadstr to set
	 */
	public void setResourceLoadstr(String resourceLoadstr) {
		this.resourceLoadstr = resourceLoadstr;
	}

}