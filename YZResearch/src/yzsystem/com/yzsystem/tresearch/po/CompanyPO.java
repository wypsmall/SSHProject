package com.yzsystem.tresearch.po;

import com.imti.framework.web.po.BasePO;

public class CompanyPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2836111422352221354L;

	private Integer cid;	//��ʶId
	private String cname;	//��ҵ����
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
	
	
}
