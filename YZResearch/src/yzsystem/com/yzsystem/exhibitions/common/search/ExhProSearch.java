package com.yzsystem.exhibitions.common.search;

import java.io.Serializable;

import com.imti.sysmgr.bean.Page;

public class ExhProSearch extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2267520890563312718L;

	/*չ��ģ��*/
	private String exhProModuleCode;//ģ�����
	
	/*չ������*/
	private Integer exhProId;//��չ��Ŀ����
	private String exhProChzName;//��չ��Ŀ����(����)
	private String exhProNo;//��չ��Ŀ���
	
	/*��չ��ҵ����*/
	private String enterpriseName;//��ҵName
	
	
	
	
	
	
	
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
