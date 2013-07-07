package com.yzsystem.tresearch.vo;

import com.neo.common.vo.IValueObject;

public class FlowAppVO implements IValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2834848032502058762L;
	private Integer flwId;
	private String flwCode;
	private String flwTitle;
	private String flwRemark;
	private String flwProposerId;
	private String flwProposerName;
	private String flwAuditorId;
	private String flwAuditorName;
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
	public String getFlwProposerId() {
		return flwProposerId;
	}
	public void setFlwProposerId(String flwProposerId) {
		this.flwProposerId = flwProposerId;
	}
	public String getFlwProposerName() {
		return flwProposerName;
	}
	public void setFlwProposerName(String flwProposerName) {
		this.flwProposerName = flwProposerName;
	}
	public String getFlwAuditorId() {
		return flwAuditorId;
	}
	public void setFlwAuditorId(String flwAuditorId) {
		this.flwAuditorId = flwAuditorId;
	}
	public String getFlwAuditorName() {
		return flwAuditorName;
	}
	public void setFlwAuditorName(String flwAuditorName) {
		this.flwAuditorName = flwAuditorName;
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
