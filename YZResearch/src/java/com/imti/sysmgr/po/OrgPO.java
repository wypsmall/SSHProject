package com.imti.sysmgr.po;

import java.io.Serializable;

import com.imti.framework.web.po.BasePO;

public class OrgPO extends BasePO implements Serializable {
	private String orgId;//主键
	private String orgName;//部门名称
	private String orgCode;//部门编码
	private String orgParentId;//父部门
	private String memo;//备注
	private String ztId;
	private String orgParentCode;//分公司编码
	private String orgParentName;//分公司名称
	private String type;

	//2010-10-23 曹刚
	private String ownerCompany;//注册用户的主单位，注册单位的一级直为：root，二级值为（root_ztId）
	private int displayIndex;//组织机构的深度（大管家货代平台---XXX单位---XXX单位办事处）
	
	
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	public String getOwnerCompany() {
		return ownerCompany;
	}
	public void setOwnerCompany(String ownerCompany) {
		this.ownerCompany = ownerCompany;
	}
	public String getOrgParentCode() {
		return orgParentCode;
	}
	public void setOrgParentCode(String orgParentCode) {
		this.orgParentCode = orgParentCode;
	}
	public String getOrgParentName() {
		return orgParentName;
	}
	public void setOrgParentName(String orgParentName) {
		this.orgParentName = orgParentName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZtId() {
		return ztId;
	}
	public void setZtId(String ztId) {
		this.ztId = ztId;
	}
	
}
