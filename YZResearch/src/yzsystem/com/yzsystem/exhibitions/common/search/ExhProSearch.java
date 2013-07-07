package com.yzsystem.exhibitions.common.search;

import java.io.Serializable;

import com.imti.sysmgr.bean.Page;

public class ExhProSearch extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2267520890563312718L;

	/*展会模板*/
	private String exhProModuleCode;//模板编码
	
	/*展会条件*/
	private Integer exhProId;//会展项目主键
	private String exhProChzName;//会展项目名称(中文)
	private String exhProNo;//会展项目编号
	
	/*参展企业条件*/
	private String enterpriseName;//企业Name
	
	
	
	
	
	
	
	public String getExhProModuleCode() {
		return exhProModuleCode;
	}

	public void setExhProModuleCode(String exhProModuleCode) {
		this.exhProModuleCode = exhProModuleCode;
	}

	public Integer getExhProId() {
		return exhProId;
	}

	public void setExhProId(Integer exhProId) {
		this.exhProId = exhProId;
	}

	public String getExhProChzName() {
		return exhProChzName;
	}

	public void setExhProChzName(String exhProChzName) {
		this.exhProChzName = exhProChzName;
	}

	public String getExhProNo() {
		return exhProNo;
	}

	public void setExhProNo(String exhProNo) {
		this.exhProNo = exhProNo;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
}
