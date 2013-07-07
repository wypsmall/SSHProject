package com.yzsystem.exhibitions.vo;

import com.imti.framework.web.vo.BaseVO;

public class ExhibitionEnterpriseVO extends BaseVO {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 1L;

	private Integer exhEnterpriseId;//参展企业ID
	private Integer enterpriseId;//企业ID
	private String enterpriseName;//企业Name 
	
	private Integer exhProId;//会展项目主键
	private String exhProNo;//会展项目编号
	
	public String getExhProNo() {
		return exhProNo;
	}
	public void setExhProNo(String exhProNo) {
		this.exhProNo = exhProNo;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public Integer getExhProId() {
		return exhProId;
	}
	public void setExhProId(Integer exhProId) {
		this.exhProId = exhProId;
	}
	public Integer getExhEnterpriseId() {
		return exhEnterpriseId;
	}
	public void setExhEnterpriseId(Integer exhEnterpriseId) {
		this.exhEnterpriseId = exhEnterpriseId;
	}
}
