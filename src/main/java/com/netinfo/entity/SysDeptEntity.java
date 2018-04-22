package com.netinfo.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the SYS_DEPT database table.
 * 
 */
@Entity
@Table(name="SYS_DEPT")
@NamedQuery(name="SysDeptEntity.findAll", query="SELECT s FROM SysDeptEntity s")
public class SysDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	private String name;

	@Column(name="PARENT_ID")
	private String parentId;

	public SysDeptEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}