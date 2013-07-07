package com.imti.sysmgr.po;

import java.sql.Timestamp;

import com.imti.framework.web.po.BasePO;

public class BusiLogPO extends BasePO {

	private static final long serialVersionUID = -1879802266471690022L;
	private String logId;
	private String rootModuleName;//一级模块名称
	private String secondModuleName;//二级模块名称
	private String thirdModuleName;//三级模块名称
	private String operateName;//操作名称
	private String operateStatusName;//操作状态
	private String errMsg;//失败原因
	private String operator;//操作人
	private Timestamp operateTime;//操作时间
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
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
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Timestamp getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}
}
