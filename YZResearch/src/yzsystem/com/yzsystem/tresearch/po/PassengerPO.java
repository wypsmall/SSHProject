package com.yzsystem.tresearch.po;

import com.imti.framework.web.po.BasePO;

public class PassengerPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6343312432601963135L;
	private Integer uid;
	private String uname;
	private Integer usex;
	private String ucompany;
	private String uroom;
	private String inTime;
	private String modTime;


	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Integer getUsex() {
		return usex;
	}
	public void setUsex(Integer usex) {
		this.usex = usex;
	}
	public String getUcompany() {
		return ucompany;
	}
	public void setUcompany(String ucompany) {
		this.ucompany = ucompany;
	}
	public String getUroom() {
		return uroom;
	}
	public void setUroom(String uroom) {
		this.uroom = uroom;
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
