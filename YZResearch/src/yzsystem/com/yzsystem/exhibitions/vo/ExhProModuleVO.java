package com.yzsystem.exhibitions.vo;

import com.imti.framework.common.StringUtil;
import com.imti.framework.web.vo.BaseVO;

public class ExhProModuleVO extends BaseVO {

	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = 1L;

	private Integer exhProModuleId;//����ģ��
	private Integer exhProId;//��չ��Ŀ����
	private String charges;//�����շѱ�׼
	private int measurementUnits;//������λ
	private Short lockStatus = 1;//ģ����������
	private String operator;//������
	private String operateDate;//����ʱ��
	
	//������
	private Integer paramId;
	private String paramValue;//����
	private String paramRemark;//��λ
	
	private String paramCode;//��������
	private String paramName;//����
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
