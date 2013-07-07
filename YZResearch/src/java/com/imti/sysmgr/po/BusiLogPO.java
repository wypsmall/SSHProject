package com.imti.sysmgr.po;

import java.sql.Timestamp;

import com.imti.framework.web.po.BasePO;

public class BusiLogPO extends BasePO {

	private static final long serialVersionUID = -1879802266471690022L;
	private String logId;
	private String rootModuleName;//һ��ģ������
	private String secondModuleName;//����ģ������
	private String thirdModuleName;//����ģ������
	private String operateName;//��������
	private String operateStatusName;//����״̬
	private String errMsg;//ʧ��ԭ��
	private String operator;//������
	private Timestamp operateTime;//����ʱ��
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
