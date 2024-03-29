package com.yzsystem.exhibitions.vo;

import com.imti.framework.web.vo.BaseVO;

public class ExhibitionProjectVO extends BaseVO {
	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 1L;
	private Integer exhProId;//会展项目主键
	private String exhProChzName;//会展项目名称(中文)
	private String exhProNo;//会展项目编号
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
	
}
