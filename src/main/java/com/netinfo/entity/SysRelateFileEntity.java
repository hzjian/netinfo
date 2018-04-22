package com.netinfo.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the SYS_RELATE_FILE database table.
 * 
 */
@Entity
@Table(name="SYS_RELATE_FILE")
@NamedQuery(name="SysRelateFileEntity.findAll", query="SELECT s FROM SysRelateFileEntity s")
public class SysRelateFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String fileid;

	private String fname;
	
	private String ftype;
	
	private String layerid;
	
	private String relateid;
	
	private String fdesc;

	
	/**
	 * @return the fileid
	 */
	public String getFileid() {
		return fileid;
	}

	/**
	 * @param fileid the fileid to set
	 */
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the ftype
	 */
	public String getFtype() {
		return ftype;
	}

	/**
	 * @param ftype the ftype to set
	 */
	public void setFtype(String ftype) {
		this.ftype = ftype;
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
	 * @return the relateid
	 */
	public String getRelateid() {
		return relateid;
	}

	/**
	 * @param relateid the relateid to set
	 */
	public void setRelateid(String relateid) {
		this.relateid = relateid;
	}

	/**
	 * @return the fdesc
	 */
	public String getFdesc() {
		return fdesc;
	}

	/**
	 * @param fdesc the fdesc to set
	 */
	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}

	

}