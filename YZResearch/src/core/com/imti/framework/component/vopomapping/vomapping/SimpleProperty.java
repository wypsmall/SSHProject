package com.imti.framework.component.vopomapping.vomapping;

import org.apache.commons.lang.StringUtils;

public class SimpleProperty {
	private String voProperty ;  //vo������
	private String poProperty; //po������
	private String voType;     //vo����������
	private String poType;       //po����������
	private String primary;//�Ƿ�������
	
	public String getVoProperty() {
		return voProperty;
	}
	public void setVoProperty(String voProperty) {
		this.voProperty = voProperty;
	}
	public String getPoProperty() {
		return poProperty;
	}
	public void setPoProperty(String poProperty) {
		this.poProperty = poProperty;
	}
	
	/**
	 * ��û������vo����������ʱ����������һ��Ĭ�ϵ��������ͣ��ַ������ͣ�
	 * @return
	 */
	public String getVoType() {
		if(StringUtils.isEmpty(voType)){
			voType = "string";
		}
		return voType;
	}
	public void setVoType(String voType) {
		this.voType = voType;
	}
	/**
	 * ��û������vo����������ʱ����������һ��Ĭ�ϵ��������ͣ��ַ������ͣ�
	 * @return
	 */
	public String getPoType() {
		if(StringUtils.isEmpty(poType)){
			poType = "string";
		}
		return poType;
	}
	public void setPoType(String poType) {
		this.poType = poType;
	}
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
}
