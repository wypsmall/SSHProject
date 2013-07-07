package com.yzsystem.crm.vo;

import com.imti.framework.web.vo.BaseVO;

public class EnterpriseVO extends BaseVO {
	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = -2496560336819441164L;
	private Integer enterpriseId;//企业ID 
	private String enterpriseName;//企业Name 
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
}
