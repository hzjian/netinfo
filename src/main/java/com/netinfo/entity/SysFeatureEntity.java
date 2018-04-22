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
 * The persistent class for the SYS_FEATURE database table.
 * 
 */
@Entity
@Table(name="SYS_FEATURE")
@NamedQuery(name="SysFeatureEntity.findAll", query="SELECT s FROM SysFeatureEntity s")
public class SysFeatureEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FEATURE_ID")
	private long featureId;

	private String fcode;

	private String fdesc;

	private String fname;

	private String fstyledesc;

	private String ftype;

	@Column(name="LAYER_ID")
	private String layerId;

	public SysFeatureEntity() {
	}

	public long getFeatureId() {
		return this.featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}

	public String getFcode() {
		return this.fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	public String getFdesc() {
		return this.fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFstyledesc() {
		return this.fstyledesc;
	}

	public void setFstyledesc(String fstyledesc) {
		this.fstyledesc = fstyledesc;
	}

	public String getFtype() {
		return this.ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getLayerId() {
		return this.layerId;
	}

	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}

}