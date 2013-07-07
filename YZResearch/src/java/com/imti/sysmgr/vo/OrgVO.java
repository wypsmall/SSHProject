package com.imti.sysmgr.vo;

import java.io.Serializable;
import java.util.List;

import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.vo.BaseVO;
import com.imti.sysmgr.service.SysMgrService;

public class OrgVO extends BaseVO implements Serializable {
	private String orgId;//����
	private String orgName;//��������
	private String orgCode;//���ű���
	private String orgParentId;//������
	private String orgParentCode;//�ֹ�˾����
	private String orgParentName;//�ֹ�˾����
	private String memo;//��ע
	private String type;//1��һ�㲿�� 2���������� 3����������  4����
	private String ztId;
	//2010-10-23 �ܸ�
	private String ownerCompany;//ע���û�������λ��ע�ᵥλ��һ��ֱΪ��root������ֵΪ��root_ztId��
	private int displayIndex;//��֯��������ȣ���ܼһ���ƽ̨---XXX��λ---XXX��λ���´���
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
	public boolean hasChildren() {
		boolean hasChildren = false;
		SysMgrService service = (SysMgrService)SpringBeanUtil.getBean("sysMgrService");
		List list = service.getOrgChildrenById(orgId);
		if(ImtiListUtil.isNotEmpty(list)){
			hasChildren = true;
		}
		return hasChildren;
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
	
}
