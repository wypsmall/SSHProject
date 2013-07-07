package com.yzsystem.exhibitions.po;

import java.math.BigDecimal;
import java.util.Date;

import com.imti.framework.web.po.BasePO;
import com.yzsystem.sysparam.po.SysParamPO;

public class ExhProModulePO extends BasePO {

	private static final long serialVersionUID = 1L;

	private Integer exhProModuleId;//翻译模板
	private Integer exhProId;//会展项目主键EXH_PROID
	private BigDecimal charges;//收费标准
	private int measurementUnits;//计量单位
	private Short lockStatus;//模板锁定开关
	private String operator;//经办人
	private Date operateDate;//经办时间
	
	//翻译项
	private SysParamPO moduleItem = new SysParamPO();
	
	
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
	public BigDecimal getCharges() {
		return charges;
	}
	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public SysParamPO getModuleItem() {
		return moduleItem;
	}
	public void setModuleItem(SysParamPO moduleItem) {
		this.moduleItem = moduleItem;
	}
}
