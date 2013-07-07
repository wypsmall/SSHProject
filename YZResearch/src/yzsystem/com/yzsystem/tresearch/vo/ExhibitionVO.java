package com.yzsystem.tresearch.vo;

import com.imti.framework.web.vo.BaseVO;

public class ExhibitionVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6165664454397626506L;
	private Integer eid; 	//标识id
	private String ename;	//展会名称
	private String eno;		//展会编号
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getEno() {
		return eno;
	}
	public void setEno(String eno) {
		this.eno = eno;
	}
	
	
}
