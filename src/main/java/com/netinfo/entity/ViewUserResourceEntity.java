package com.netinfo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the VIEW_USERRESOURCE database table.
 * 
 */
@Entity
@Table(name = "VIEW_USERRESOURCE")
@NamedQuery(name = "ViewUserResourceEntity.findAll", query = "SELECT s FROM ViewUserResourceEntity s")
public class ViewUserResourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ViewUserResourcePK id;

	public ViewUserResourcePK getId() {
		return id;
	}

	public void setId(ViewUserResourcePK id) {
		this.id = id;
	}
	//d.resource_desc,d.resource_path,d.priority 
	
	private Integer priority;

	@Column(name = "RESOURCE_DESC")
	private String resourceDesc;


	@Column(name = "RESOURCE_PATH")
	private String resourcePath;

	@Column(name = "RESOURCE_TYPE")
	private String resourceType;
	
	@Column(name = "RESOURCE_LOADSTR")
	private String resourceLoadstr;

	public ViewUserResourceEntity() {
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