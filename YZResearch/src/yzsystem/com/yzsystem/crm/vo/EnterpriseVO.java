package com.yzsystem.crm.vo;

import com.imti.framework.web.vo.BaseVO;

public class EnterpriseVO extends BaseVO {
	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = -2496560336819441164L;
	private Integer enterpriseId;//��ҵID 
	private String enterpriseName;//��ҵName 
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
