package com.yzsystem.exhibitions.vo;

import com.imti.framework.web.vo.BaseVO;

public class ExhibitionProjectVO extends BaseVO {
	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = 1L;
	private Integer exhProId;//��չ��Ŀ����
	private String exhProChzName;//��չ��Ŀ����(����)
	private String exhProNo;//��չ��Ŀ���
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
