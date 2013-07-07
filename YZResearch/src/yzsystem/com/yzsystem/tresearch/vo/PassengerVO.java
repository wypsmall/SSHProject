package com.yzsystem.tresearch.vo;

import com.imti.framework.web.vo.BaseVO;

public class PassengerVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8641428114306225282L;
	private Integer uid;
	private String uname;
	private Integer usex;
	private String ucompany;
	private String uroom;

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

}
