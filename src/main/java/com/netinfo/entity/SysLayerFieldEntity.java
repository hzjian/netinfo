package com.netinfo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the SYS_LAYERFIELD database table.
 * 
 */
@Entity
@Table(name="SYS_LAYERFIELD")
@NamedQuery(name="SysLayerFieldEntity.findAll", query="SELECT s FROM SysLayerFieldEntity s")
public class SysLayerFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	@Column(name="FIELD_ID")
	private String fieldId;
	
	@Column(name="FIELD_NAME")
	private String fieldName;

	@Column(name="FIELD_TYPE")
	private String fieldType;
	
	@Column(name="FIELD_LABEL")
	private String fieldLabel;
	
	@Column(name="LAYER_ID")
	private String layerId;
	
	

	private Integer seq;
	
	private String datasource;
	
	


	public SysLayerFieldEntity() {
	}


	/**
	 * @return the fieldId
	 */
	public String getFieldId() {
		return fieldId;
	}


	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}


	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}


	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	/**
	 * @return the fieldType
	 */
	public String getFieldType() {
		return fieldType;
	}


	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}


	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}


	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}


	/**
	 * @return the datasource
	 */
	public String getDatasource() {
		return datasource;
	}


	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}


	/**
	 * @return the layerId
	 */
	public String getLayerId() {
		return layerId;
	}


	/**
	 * @param layerId the layerId to set
	 */
	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}


	/**
	 * @return the fieldLabel
	 */
	public String getFieldLabel() {
		return fieldLabel;
	}


	/**
	 * @param fieldLabel the fieldLabel to set
	 */
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	

}