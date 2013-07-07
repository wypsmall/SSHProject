package com.yzsystem.tresearch.common.search;

import com.imti.sysmgr.bean.Page;

public class AbroadPersonSearch extends Page {
	private Integer sr_apid;		//标识id	
	
	private Integer sr_ecid;	// 标识Id

	private Integer sr_cid;	//标识Id
	private String sr_cname;	//企业名称
	
	private Integer sr_eid; 	//标识id
	private String sr_ename;	//展会名称
	private String sr_eno;		//展会编号
	
	private String sr_apname;		//姓名
	public Integer getSr_apid() {
		return sr_apid;
	}
	public void setSr_apid(Integer sr_apid) {
		this.sr_apid = sr_apid;
	}
	public Integer getSr_ecid() {
		return sr_ecid;
	}
	public void setSr_ecid(Integer sr_ecid) {
		this.sr_ecid = sr_ecid;
	}
	public Integer getSr_cid() {
		return sr_cid;
	}
	public void setSr_cid(Integer sr_cid) {
		this.sr_cid = sr_cid;
	}
	public String getSr_cname() {
		return sr_cname;
	}
	public void setSr_cname(String sr_cname) {
		this.sr_cname = sr_cname;
	}
	public Integer getSr_eid() {
		return sr_eid;
	}
	public void setSr_eid(Integer sr_eid) {
		this.sr_eid = sr_eid;
	}
	public String getSr_ename() {
		return sr_ename;
	}
	public void setSr_ename(String sr_ename) {
		this.sr_ename = sr_ename;
	}
	public String getSr_eno() {
		return sr_eno;
	}
	public void setSr_eno(String sr_eno) {
		this.sr_eno = sr_eno;
	}
	public String getSr_apname() {
		return sr_apname;
	}
	public void setSr_apname(String sr_apname) {
		this.sr_apname = sr_apname;
	}

	
}
