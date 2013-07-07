package com.imti.framework.component.vopomapping.vomapping;

import org.apache.commons.lang.StringUtils;

public class SimpleProperty {
	private String voProperty ;  //vo的属性
	private String poProperty; //po的属性
	private String voType;     //vo的数据类型
	private String poType;       //po的数据类型
	private String primary;//是否是主键
	
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
	 * 当没有配置vo的属性类型时，给它设置一个默认的数据类型（字符串类型）
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
	 * 当没有配置vo的属性类型时，给它设置一个默认的数据类型（字符串类型）
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
