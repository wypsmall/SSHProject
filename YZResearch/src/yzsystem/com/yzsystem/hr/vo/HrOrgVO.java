package com.yzsystem.hr.vo;

import java.io.Serializable;
import java.util.List;

import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.vo.BaseVO;
import com.yzsystem.hr.po.HrOrgPO;
import com.yzsystem.hr.service.HrService;

public class HrOrgVO extends BaseVO implements Serializable{

	private static final long serialVersionUID = -6456242889538628927L;
	private String orgId;
	private String orgName;
	private String orgCode;
	private String orgParentId;
	private String memo;
	private String address;
	private String tel;
	private String fax;
	private String orgParentName;
	private int delFlag;//0É¾³ý 1Î´É¾³ý

	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public String getOrgParentName() {
		return orgParentName;
	}
	public void setOrgParentName(String orgParentName) {
		this.orgParentName = orgParentName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgParentId() {
		return orgParentId;
	}
	public void setOrgParentId(String orgParentId) {
		this.orgParentId = orgParentId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@SuppressWarnings("unchecked")
	public boolean hasChildren() {
		boolean hasChildren = false;
		HrService service = (HrService)SpringBeanUtil.getBean("hrService");
		String whereHql = " and po.orgParentId='" + orgId + "'";
		List list = service.getByConditionWithNoPage(HrOrgPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			hasChildren = true;
		}
		return hasChildren;
	}
}
