package com.yzsystem.tresearch.po;

import com.neo.common.model.IDomainObject;

public class FlowAppDO implements IDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5454759067699832987L;
	
	private Integer flwId;
	private String flwCode;
	private String flwTitle;
	private String flwRemark;
	private UserDO flwProposer;
	private UserDO flwAuditor;
	private String flwReply;
	private Integer flwStatus;
	private String inTime;
	private String modTime;
	public Integer getFlwId() {
		return flwId;
	}
	public void setFlwId(Integer flwId) {
		this.flwId = flwId;
	}
	public String getFlwCode() {
		return flwCode;
	}
	public void setFlwCode(String flwCode) {
		this.flwCode = flwCode;
	}
	public String getFlwTitle() {
		return flwTitle;
	}
	public void setFlwTitle(String flwTitle) {
		this.flwTitle = flwTitle;
	}
	public String getFlwRemark() {
		return flwRemark;
	}
	public void setFlwRemark(String flwRemark) {
		this.flwRemark = flwRemark;
	}
	public UserDO getFlwProposer() {
		return flwProposer;
	}
	public void setFlwProposer(UserDO flwProposer) {
		this.flwProposer = flwProposer;
	}
	public UserDO getFlwAuditor() {
		return flwAuditor;
	}
	public void setFlwAuditor(UserDO flwAuditor) {
		this.flwAuditor = flwAuditor;
	}
	public String getFlwReply() {
		return flwReply;
	}
	public void setFlwReply(String flwReply) {
		this.flwReply = flwReply;
	}
	public Integer getFlwStatus() {
		return flwStatus;
	}
	public void setFlwStatus(Integer flwStatus) {
		this.flwStatus = flwStatus;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getModTime() {
		return modTime;
	}
	public void setModTime(String modTime) {
		this.modTime = modTime;
	}

}
