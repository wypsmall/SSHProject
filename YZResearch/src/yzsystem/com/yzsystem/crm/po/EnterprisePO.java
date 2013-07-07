package com.yzsystem.crm.po;

import java.util.HashSet;
import java.util.Set;

import com.imti.framework.web.po.BasePO;
import com.yzsystem.exhibitions.po.ExhibitionProjectPO;

public class EnterprisePO extends BasePO {

	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = 4055498432244254381L;
	
	private Integer enterpriseId;//��ҵID
	private String enterpriseName;//��ҵName
	
	//��ҵ����������չ��
	private Set<ExhibitionProjectPO> exhibitionSet = new HashSet<ExhibitionProjectPO>();
	
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
	public Set<ExhibitionProjectPO> getExhibitionSet() {
		return exhibitionSet;
	}
	public void setExhibitionSet(Set<ExhibitionProjectPO> exhibitionSet) {
		this.exhibitionSet = exhibitionSet;
	}
}
