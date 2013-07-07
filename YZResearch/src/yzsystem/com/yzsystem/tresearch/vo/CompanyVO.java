package com.yzsystem.tresearch.vo;

import com.imti.framework.web.vo.BaseVO;

public class CompanyVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7010792489650485167L;
	private Integer cid;	//标识Id
	private String cname;	//企业名称
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
