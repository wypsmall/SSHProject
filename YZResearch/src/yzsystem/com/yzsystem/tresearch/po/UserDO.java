package com.yzsystem.tresearch.po;

import com.neo.common.model.IDomainObject;

public class UserDO implements IDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5834279047929965005L;

	private String fid;
	private String fuserName;
	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}
	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}
	/**
	 * @return the fuserName
	 */
	public String getFuserName() {
		return fuserName;
	}
	/**
	 * @param fuserName the fuserName to set
	 */
	public void setFuserName(String fuserName) {
		this.fuserName = fuserName;
	}

	
}
