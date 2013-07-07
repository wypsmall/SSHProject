package com.yzsystem.hr.po;

import java.util.Date;

import com.imti.framework.web.po.BasePO;

public class HrEmployeePO extends BasePO implements java.io.Serializable{
	
	private static final long serialVersionUID = -5372631213500273837L;
	//基本信息
	private String employeeId;//职员主键标识
	private String employeeNo;//职员工号
	private String employeeName;//职员姓名
	private Short employeeSex;//性别 1 男 2 女
	private Short employeeBirthType;//生日类型（农历or阳历） 1 农历 2阳历
	private Date employeeBirth;//生日
	private String employeePostName;//职位
	private Short employeeState;//在职状态（实习、试用期、正式职员、已离职）1实习2试用期3正式职员4已离职
	private Date entryJobDate;//入职日期
	private Date contractStartDate;//合同起算日期
	private Date contractEndDate;//合同截止日期
	private Date offJobDate;//离职日期
	private String identification;//身份证号
	private String contact;//联系人
	private String contactTel;//联系人电话
	private String memo;//备注
	private String homeAddress;//家庭住址
	private Short accountType;//1农业户口2非农业户口
	private Short hasBuyShebao;//0未购买 1已购买
	//个人履历
	private Date graduateDate;//毕业时间
	private String graduateSch;//毕业院校
	private String education;//学历
	private String profession;//专业
	
	//职员部门资料
	private HrOrgPO org = new HrOrgPO();
	
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
	public Short getEmployeeSex() {
		return employeeSex;
	}
	public void setEmployeeSex(Short employeeSex) {
		this.employeeSex = employeeSex;
	}
	public Short getEmployeeBirthType() {
		return employeeBirthType;
	}
	public void setEmployeeBirthType(Short employeeBirthType) {
		this.employeeBirthType = employeeBirthType;
	}
	public Date getEmployeeBirth() {
		return employeeBirth;
	}
	public void setEmployeeBirth(Date employeeBirth) {
		this.employeeBirth = employeeBirth;
	}
	public String getEmployeePostName() {
		return employeePostName;
	}
	public void setEmployeePostName(String employeePostName) {
		this.employeePostName = employeePostName;
	}
	public Short getEmployeeState() {
		return employeeState;
	}
	public void setEmployeeState(Short employeeState) {
		this.employeeState = employeeState;
	}
	public Date getEntryJobDate() {
		return entryJobDate;
	}
	public void setEntryJobDate(Date entryJobDate) {
		this.entryJobDate = entryJobDate;
	}
	public Date getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public Date getOffJobDate() {
		return offJobDate;
	}
	public void setOffJobDate(Date offJobDate) {
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
	public Short getAccountType() {
		return accountType;
	}
	public void setAccountType(Short accountType) {
		this.accountType = accountType;
	}
	public Short getHasBuyShebao() {
		return hasBuyShebao;
	}
	public void setHasBuyShebao(Short hasBuyShebao) {
		this.hasBuyShebao = hasBuyShebao;
	}
	public Date getGraduateDate() {
		return graduateDate;
	}
	public void setGraduateDate(Date graduateDate) {
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
	public HrOrgPO getOrg() {
		return org;
	}
	public void setOrg(HrOrgPO org) {
		this.org = org;
	}
}