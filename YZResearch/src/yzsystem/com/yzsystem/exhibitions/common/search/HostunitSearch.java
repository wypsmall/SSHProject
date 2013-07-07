package com.yzsystem.exhibitions.common.search;

import java.io.Serializable;

import com.imti.sysmgr.bean.Page;

public class HostunitSearch extends Page implements Serializable {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 2267326938602853252L;
	
	private String hostUnitName;//单位名称
	private String enterUser ;//录入员
	private String lastModifyUser;//最后一次修改人
	
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
