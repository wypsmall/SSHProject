package com.yzsystem.tperson.common.search;

import com.imti.sysmgr.bean.Page;

public class TPersonSearch extends Page {
	private Integer personId;
	private String	sr_personName;

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getSr_personName() {
		return sr_personName;
	}

	public void setSr_personName(String sr_personName) {
		this.sr_personName = sr_personName;
	}
	
	
}
