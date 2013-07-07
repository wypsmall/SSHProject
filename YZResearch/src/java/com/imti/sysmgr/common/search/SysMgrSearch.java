package com.imti.sysmgr.common.search;

import com.imti.sysmgr.bean.Page;

public class SysMgrSearch extends Page {

	//业务操作日志查询条件
	private String rootModuleName;//一级模块名称
	private String secondModuleName;//二级模块名称
	private String thirdModuleName;//三级模块名称
	private String operateName;//操作名称
	private String operateStatusName;//操作状态
	private String operator;//操作人
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
