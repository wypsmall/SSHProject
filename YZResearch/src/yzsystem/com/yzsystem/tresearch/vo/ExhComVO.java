package com.yzsystem.tresearch.vo;

import com.imti.framework.web.vo.BaseVO;

public class ExhComVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2622336688530620102L;

	private Integer ecid;	// ��ʶId
	
	private Integer cid;	//��ʶId
	private String cname;	//��ҵ����
	
	private Integer eid; 	//��ʶid
	private String ename;	//չ������
	private String eno;		//չ����
	
	private String remark;	//��ע

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
