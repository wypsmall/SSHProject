package com.yzsystem.tresearch.vo;

import com.imti.framework.web.vo.BaseVO;

public class ExhComVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2622336688530620102L;

	private Integer ecid;	// 标识Id
	
	private Integer cid;	//标识Id
	private String cname;	//企业名称
	
	private Integer eid; 	//标识id
	private String ename;	//展会名称
	private String eno;		//展会编号
	
	private String remark;	//备注

	public Integer getEcid() {
		return ecid;
	}

	public void setEcid(Integer ecid) {
		this.ecid = ecid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
