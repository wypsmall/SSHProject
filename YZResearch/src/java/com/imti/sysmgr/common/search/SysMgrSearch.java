package com.imti.sysmgr.common.search;

import com.imti.sysmgr.bean.Page;

public class SysMgrSearch extends Page {

	//ҵ�������־��ѯ����
	private String rootModuleName;//һ��ģ������
	private String secondModuleName;//����ģ������
	private String thirdModuleName;//����ģ������
	private String operateName;//��������
	private String operateStatusName;//����״̬
	private String operator;//������
	private String opeDateBegin;
	private String opeDateEnd;
	
	public String getRootModuleName() {
		return rootModuleName;
	}
	public void setRootModuleName(String rootModuleName) {
		this.rootModuleName = rootModuleName;
	}
	public String getSecondModuleName() {
		return secondModuleName;
	}
	public void setSecondModuleName(String secondModuleName) {
		this.secondModuleName = secondModuleName;
	}
	public String getThirdModuleName() {
		return thirdModuleName;
	}
	public void setThirdModuleName(String thirdModuleName) {
		this.thirdModuleName = thirdModuleName;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getOperateStatusName() {
		return operateStatusName;
	}
	public void setOperateStatusName(String operateStatusName) {
		this.operateStatusName = operateStatusName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOpeDateBegin() {
		return opeDateBegin;
	}
	public void setOpeDateBegin(String opeDateBegin) {
		this.opeDateBegin = opeDateBegin;
	}
	public String getOpeDateEnd() {
		return opeDateEnd;
	}
	public void setOpeDateEnd(String opeDateEnd) {
		this.opeDateEnd = opeDateEnd;
	}
}
