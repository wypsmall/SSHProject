package com.imti.sysmgr.vo;

import com.imti.framework.web.vo.BaseVO;

public class BusiLogVO extends BaseVO {
	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = -5905150043033398517L;
	private String logId;
	private String rootModuleName;//一级模块名称
	private String secondModuleName;//二级模块名称
	private String thirdModuleName;//三级模块名称
	private String operateName;//操作名称
	private String operateStatusName;//操作状态
	private String errMsg;//失败原因
	private String operator;//操作人
	private String operateTime;//操作时间
	
	public BusiLogVO(){
		
	}
	public BusiLogVO(String rootModuleName, String secondModuleName,String thirdModuleName,String operateName,
			String operateStatusName,String errMsg,String operator,String operateTime){
		this.rootModuleName = rootModuleName;
		this.secondModuleName = secondModuleName;
		this.thirdModuleName = thirdModuleName;
		this.operateName = operateName;
		this.operateStatusName = operateStatusName;
		this.errMsg = errMsg;
		this.operator = operator;
		this.operateTime = operateTime;
	}
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
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
}
