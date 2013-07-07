package com.yzsystem.exhibitions.vo;

import com.imti.framework.common.StringUtil;
import com.imti.framework.web.vo.BaseVO;

public class ExhProModuleVO extends BaseVO {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 1L;

	private Integer exhProModuleId;//翻译模板
	private Integer exhProId;//会展项目主键
	private String charges;//翻译收费标准
	private int measurementUnits;//计量单位
	private Short lockStatus = 1;//模板锁定开关
	private String operator;//经办人
	private String operateDate;//经办时间
	
	//翻译项
	private Integer paramId;
	private String paramValue;//子项
	private String paramRemark;//单位
	
	private String paramCode;//子类变编码
	private String paramName;//名称
	private Short displayIndex;
	
	
	public Integer getExhProModuleId() {
		return exhProModuleId;
	}
	public void setExhProModuleId(Integer exhProModuleId) {
		this.exhProModuleId = exhProModuleId;
	}
	public Integer getExhProId() {
		return exhProId;
	}
	public void setExhProId(Integer exhProId) {
		this.exhProId = exhProId;
	}
	public String getCharges() {
		return charges;
	}
	public void setCharges(String charges) {
		this.charges = charges;
	}
	public int getMeasurementUnits() {
		return measurementUnits;
	}
	public void setMeasurementUnits(int measurementUnits) {
		this.measurementUnits = measurementUnits;
	}
	public Short getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Short lockStatus) {
		this.lockStatus = lockStatus;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperateDate() {
		return StringUtil.isEmpty(operateDate)?"":operateDate.substring(0, 10);
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public Integer getParamId() {
		return paramId;
	}
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
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
	public Short getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(Short displayIndex) {
		this.displayIndex = displayIndex;
	}
}
