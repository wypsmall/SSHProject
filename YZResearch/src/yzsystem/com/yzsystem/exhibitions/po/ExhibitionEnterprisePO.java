package com.yzsystem.exhibitions.po;

import com.imti.framework.web.po.BasePO;
import com.yzsystem.crm.po.EnterprisePO;

public class ExhibitionEnterprisePO extends BasePO {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 1L;

	private Integer exhEnterpriseId;//参展企业ID
	
	
	
	
	
	
	
	
	private EnterprisePO enterprise = new EnterprisePO();//企业
	private ExhibitionProjectPO exhPro = new ExhibitionProjectPO();//会展项目
	
	public EnterprisePO getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(EnterprisePO enterprise) {
		this.enterprise = enterprise;
	}
	public ExhibitionProjectPO getExhPro() {
		return exhPro;
	}
	public void setExhPro(ExhibitionProjectPO exhPro) {
		this.exhPro = exhPro;
	}
	public Integer getExhEnterpriseId() {
		return exhEnterpriseId;
	}
	public void setExhEnterpriseId(Integer exhEnterpriseId) {
		this.exhEnterpriseId = exhEnterpriseId;
	}
}
