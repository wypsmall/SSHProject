package com.yzsystem.tresearch.po;

import com.imti.framework.web.po.BasePO;

public class ExhComPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6453554800601826140L;

	private Integer ecid;	// ��ʶId
	private CompanyPO companyPO;	//��˾		
	private ExhibitionPO exhibitionPO;	//չ��
	private String remark;	//��ע
	public Integer getEcid() {
		return ecid;
	}
	public void setEcid(Integer ecid) {
		this.ecid = ecid;
	}
	public CompanyPO getCompanyPO() {
		return companyPO;
	}
	public void setCompanyPO(CompanyPO companyPO) {
		this.companyPO = companyPO;
	}
	public ExhibitionPO getExhibitionPO() {
		return exhibitionPO;
	}
	public void setExhibitionPO(ExhibitionPO exhibitionPO) {
		this.exhibitionPO = exhibitionPO;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
