package com.yzsystem.exhibitions.vo;

import com.imti.framework.common.StringUtil;
import com.imti.framework.web.vo.BaseVO;

public class HostunitVO extends BaseVO {
	private static final long serialVersionUID = -5334714317992032285L;
	private String hostUnitId;
	private String hostUnitName;//单位名称
	private String postalcode ;//邮编
	private String address;//通讯地址
	private String contact;//联系人
	private String postName;//职务
	private String telephone;//联系手机
	private String contactEmail;//联系人邮箱
	private String officeTel;//办公电话
	private String officeFax;//办公传真
	private String webSite;//公司网址
	private String remark;//备注
	private String enterUser ;//录入员
	private String enterDate ;//录入时间
	private String lastModifyUser;//最后一次修改人
	private String lastModifyDate ;//最后一次修改时间
	private String delFlag="1";//10删除1未删除（默认值是1）
	
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getHostUnitId() {
		return hostUnitId;
	}
	public void setHostUnitId(String hostUnitId) {
		this.hostUnitId = hostUnitId;
	}
	public String getHostUnitName() {
		return hostUnitName;
	}
	public void setHostUnitName(String hostUnitName) {
		this.hostUnitName = hostUnitName;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	public String getOfficeFax() {
		return officeFax;
	}
	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEnterUser() {
		return enterUser;
	}
	public void setEnterUser(String enterUser) {
		this.enterUser = enterUser;
	}
	public String getEnterDate() {
		return StringUtil.isEmpty(enterDate)?"":enterDate.substring(0, 10);
	}
	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}
	public String getLastModifyUser() {
		return lastModifyUser;
	}
	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}
	public String getLastModifyDate() {
		return StringUtil.isEmpty(lastModifyDate)?"":lastModifyDate.substring(0, 10);
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
}
