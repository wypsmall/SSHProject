package com.yzsystem.hr.vo;

import com.imti.framework.common.DateUtil;
import com.imti.framework.web.vo.BaseVO;

public class HrEmployeeVO extends BaseVO {
	
	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 5397488594977174082L;
	//基本信息
	private String employeeId;//职员主键标识
	private String employeeNo;//职员工号
	private String employeeName;//职员姓名
	private String employeeSex = "1";//性别
	private String employeeBirthType = "1";//生日类型（农历or阳历）
	private String employeeBirth;//生日
	private String employeePostName;//职位
	private String employeeState = "2";//在职状态（实习、试用期、正式职员、已离职）
	private String entryJobDate;//入职日期
	private String contractStartDate;//合同起算日期
	private String contractEndDate;//合同截止日期
	private String offJobDate;//离职日期
	private String identification;//身份证号
	private String contact;//联系人
	private String contactTel;//联系人电话
	private String memo;//备注
	private String homeAddress;//家庭住址
	private String accountType;//1农业户口2非农业户口
	private String hasBuyShebao = "0";
	//个人履历
	private String graduateDate;//毕业时间
	private String graduateSch;//毕业院校
	private String education;//学历
	private String profession;//专业
	//职员部门资料
	private String orgId;
	private String orgName;
	private String orgParentId;
	private String groupName;
	
	public String getOrgParentId() {
		return orgParentId;
	}
	public void setOrgParentId(String orgParentId) {
		this.orgParentId = orgParentId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeSex() {
		return employeeSex;
	}
	public void setEmployeeSex(String employeeSex) {
		this.employeeSex = employeeSex;
	}
	public String getEmployeeBirthType() {
		return employeeBirthType;
	}
	public void setEmployeeBirthType(String employeeBirthType) {
		this.employeeBirthType = employeeBirthType;
	}
	public String getEmployeeBirth() {
		return DateUtil.utcSimpleFormat(employeeBirth);
	}
	public void setEmployeeBirth(String employeeBirth) {
		this.employeeBirth = employeeBirth;
	}
	public String getEmployeePostName() {
		return employeePostName;
	}
	public void setEmployeePostName(String employeePostName) {
		this.employeePostName = employeePostName;
	}
	public String getEmployeeState() {
		return employeeState;
	}
	public void setEmployeeState(String employeeState) {
		this.employeeState = employeeState;
	}
	public String getEntryJobDate() {
		return DateUtil.utcSimpleFormat(entryJobDate);
	}
	public void setEntryJobDate(String entryJobDate) {
		this.entryJobDate = entryJobDate;
	}
	public String getContractStartDate() {
		return DateUtil.utcSimpleFormat(contractStartDate);
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return DateUtil.utcSimpleFormat(contractEndDate);
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getOffJobDate() {
		return DateUtil.utcSimpleFormat(offJobDate);
	}
	public void setOffJobDate(String offJobDate) {
		this.offJobDate = offJobDate;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getHasBuyShebao() {
		return hasBuyShebao;
	}
	public void setHasBuyShebao(String hasBuyShebao) {
		this.hasBuyShebao = hasBuyShebao;
	}
	public String getGraduateDate() {
		return DateUtil.utcSimpleFormat(graduateDate);
	}
	public void setGraduateDate(String graduateDate) {
		this.graduateDate = graduateDate;
	}
	public String getGraduateSch() {
		return graduateSch;
	}
	public void setGraduateSch(String graduateSch) {
		this.graduateSch = graduateSch;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
