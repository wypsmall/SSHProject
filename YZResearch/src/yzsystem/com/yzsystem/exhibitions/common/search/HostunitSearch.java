package com.yzsystem.exhibitions.common.search;

import java.io.Serializable;

import com.imti.sysmgr.bean.Page;

public class HostunitSearch extends Page implements Serializable {

	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = 2267326938602853252L;
	
	private String hostUnitName;//��λ����
	private String enterUser ;//¼��Ա
	private String lastModifyUser;//���һ���޸���
	
	public String getHostUnitName() {
		return hostUnitName;
	}
	public void setHostUnitName(String hostUnitName) {
		this.hostUnitName = hostUnitName;
	}
	public String getEnterUser() {
		return enterUser;
	}
	public void setEnterUser(String enterUser) {
		this.enterUser = enterUser;
	}
	public String getLastModifyUser() {
		return lastModifyUser;
	}
	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}
}
