package com.yzsystem.sysparam.po;

import com.imti.framework.web.po.BasePO;

public class SysParamPO extends BasePO {

	private static final long serialVersionUID = 1L;
	private Integer paramId;
	private String paramModuleCode;//模块
	private String paramModuleName;//模块
	private String paramCode;//子类变编码
	private String paramName;//名称
	private String paramValue;//值
	private Short displayIndex;
	private String paramRemark;//备注
	
	
	public Short getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(Short displayIndex) {
		this.displayIndex = displayIndex;
	}
	
	public String getParamModuleCode() {
		return paramModuleCode;
	}
	public void setParamModuleCode(String paramModuleCode) {
		this.paramModuleCode = paramModuleCode;
	}
	public String getParamModuleName() {
		return paramModuleName;
	}
	public void setParamModuleName(String paramModuleName) {
		this.paramModuleName = paramModuleName;
	}
	public Integer getParamId() {
		return paramId;
	}
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamRemark() {
		return paramRemark;
	}
	public void setParamRemark(String paramRemark) {
		this.paramRemark = paramRemark;
	}
	
}
