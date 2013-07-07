package com.yzsystem.crm.po;

import java.util.HashSet;
import java.util.Set;

import com.imti.framework.web.po.BasePO;
import com.yzsystem.exhibitions.po.ExhibitionProjectPO;

public class EnterprisePO extends BasePO {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 4055498432244254381L;
	
	private Integer enterpriseId;//企业ID
	private String enterpriseName;//企业Name
	
	//企业关联的历届展会
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
