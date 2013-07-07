package com.yzsystem.tresearch.po;

import com.imti.framework.web.po.BasePO;

public class ExhibitionPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5843669515489754432L;
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
