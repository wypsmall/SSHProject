package com.yzsystem.exhibitions.vo;

import com.imti.framework.web.vo.BaseVO;

public class ExhibitionEnterpriseVO extends BaseVO {

	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = 1L;

	private Integer exhEnterpriseId;//��չ��ҵID
	private Integer enterpriseId;//��ҵID
	private String enterpriseName;//��ҵName 
	
	private Integer exhProId;//��չ��Ŀ����
	private String exhProNo;//��չ��Ŀ���
	
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
