package com.netinfo.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the VIEW_USERLAYER database table.
 * 
 */
@Entity
@Table(name="VIEW_USERLAYER")
@NamedQuery(name="ViewUserLayerEntity.findAll", query="SELECT s FROM ViewUserLayerEntity s")
public class ViewUserLayerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ViewUserLayerPK id;

	private String datasource;

	private String defexpression;

	private String elevationinfo;

	private String fields;

	private String geomtype;

	private Integer labelsvisible;

	private String labelinginfo;

	private String layername;

	private String layertype;

	private String listmode;

	private Double maxscale;

	private Double minscale;

	private String pid;

	private Integer seq;

	private Integer initvisible;
	
	private Double opacity;
	

	public ViewUserLayerEntity() {
	}

	
	/**
	 * @return the id
	 */
	public ViewUserLayerPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ViewUserLayerPK id) {
		this.id = id;
	}

	public String getDatasource() {
		return this.datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	
	/**
	 * @return the defexpression
	 */
	public String getDefexpression() {
		return defexpression;
	}


	/**
	 * @param defexpression the defexpression to set
	 */
	public void setDefexpression(String defexpression) {
		this.defexpression = defexpression;
	}


	/**
	 * @return the elevationinfo
	 */
	public String getElevationinfo() {
		return elevationinfo;
	}


	/**
	 * @param elevationinfo the elevationinfo to set
	 */
	public void setElevationinfo(String elevationinfo) {
		this.elevationinfo = elevationinfo;
	}


	/**
	 * @return the fields
	 */
	public String getFields() {
		return fields;
	}


	/**
	 * @param fields the fields to set
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}


	/**
	 * @return the geomtype
	 */
	public String getGeomtype() {
		return geomtype;
	}


	/**
	 * @param geomtype the geomtype to set
	 */
	public void setGeomtype(String geomtype) {
		this.geomtype = geomtype;
	}


	/**
	 * @return the labelsvisible
	 */
	public Integer getLabelsvisible() {
		return labelsvisible;
	}


	/**
	 * @param labelsvisible the labelsvisible to set
	 */
	public void setLabelsvisible(Integer labelsvisible) {
		this.labelsvisible = labelsvisible;
	}


	/**
	 * @return the labelinginfo
	 */
	public String getLabelinginfo() {
		return labelinginfo;
	}


	/**
	 * @param labelinginfo the labelinginfo to set
	 */
	public void setLabelinginfo(String labelinginfo) {
		this.labelinginfo = labelinginfo;
	}


	/**
	 * @return the layername
	 */
	public String getLayername() {
		return layername;
	}


	/**
	 * @param layername the layername to set
	 */
	public void setLayername(String layername) {
		this.layername = layername;
	}


	/**
	 * @return the layertype
	 */
	public String getLayertype() {
		return layertype;
	}


	/**
	 * @param layertype the layertype to set
	 */
	public void setLayertype(String layertype) {
		this.layertype = layertype;
	}


	/**
	 * @return the listmode
	 */
	public String getListmode() {
		return listmode;
	}


	/**
	 * @param listmode the listmode to set
	 */
	public void setListmode(String listmode) {
		this.listmode = listmode;
	}


	/**
	 * @return the maxscale
	 */
	public Double getMaxscale() {
		return maxscale;
	}


	/**
	 * @param maxscale the maxscale to set
	 */
	public void setMaxscale(Double maxscale) {
		this.maxscale = maxscale;
	}


	/**
	 * @return the minscale
	 */
	public Double getMinscale() {
		return minscale;
	}


	/**
	 * @param minscale the minscale to set
	 */
	public void setMinscale(Double minscale) {
		this.minscale = minscale;
	}


	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}


	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
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
	 * @return the initvisible
	 */
	public Integer getInitvisible() {
		return initvisible;
	}


	/**
	 * @param initvisible the initvisible to set
	 */
	public void setInitvisible(Integer initvisible) {
		this.initvisible = initvisible;
	}


	/**
	 * @return the opacity
	 */
	public Double getOpacity() {
		return opacity;
	}


	/**
	 * @param opacity the opacity to set
	 */
	public void setOpacity(Double opacity) {
		this.opacity = opacity;
	}
	

}