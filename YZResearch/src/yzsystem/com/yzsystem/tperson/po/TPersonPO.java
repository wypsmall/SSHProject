package com.yzsystem.tperson.po;

import java.io.Serializable;

public class TPersonPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6871806191716435278L;

	private Integer personId;
	private String personName;
	private String inTime;
	private String modTime;
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getModTime() {
		return modTime;
	}
	public void setModTime(String modTime) {
		this.modTime = modTime;
	}
	
	
}
