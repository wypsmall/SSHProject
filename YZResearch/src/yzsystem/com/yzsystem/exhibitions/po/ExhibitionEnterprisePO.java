package com.yzsystem.exhibitions.po;

import com.imti.framework.web.po.BasePO;
import com.yzsystem.crm.po.EnterprisePO;

public class ExhibitionEnterprisePO extends BasePO {

	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = 1L;

	private Integer exhEnterpriseId;//��չ��ҵID
	
	
	
	
	
	
	
	
	private EnterprisePO enterprise = new EnterprisePO();//��ҵ
	private ExhibitionProjectPO exhPro = new ExhibitionProjectPO();//��չ��Ŀ
	
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
