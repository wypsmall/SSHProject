package com.yzsystem.hr.po;

import java.util.Date;

import com.imti.framework.web.po.BasePO;

public class HrEmployeePO extends BasePO implements java.io.Serializable{
	
	private static final long serialVersionUID = -5372631213500273837L;
	//������Ϣ
	private String employeeId;//ְԱ������ʶ
	private String employeeNo;//ְԱ����
	private String employeeName;//ְԱ����
	private Short employeeSex;//�Ա� 1 �� 2 Ů
	private Short employeeBirthType;//�������ͣ�ũ��or������ 1 ũ�� 2����
	private Date employeeBirth;//����
	private String employeePostName;//ְλ
	private Short employeeState;//��ְ״̬��ʵϰ�������ڡ���ʽְԱ������ְ��1ʵϰ2������3��ʽְԱ4����ְ
	private Date entryJobDate;//��ְ����
	private Date contractStartDate;//��ͬ��������
	private Date contractEndDate;//��ͬ��ֹ����
	private Date offJobDate;//��ְ����
	private String identification;//���֤��
	private String contact;//��ϵ��
	private String contactTel;//��ϵ�˵绰
	private String memo;//��ע
	private String homeAddress;//��ͥסַ
	private Short accountType;//1ũҵ����2��ũҵ����
	private Short hasBuyShebao;//0δ���� 1�ѹ���
	//��������
	private Date graduateDate;//��ҵʱ��
	private String graduateSch;//��ҵԺУ
	private String education;//ѧ��
	private String profession;//רҵ
	
	//ְԱ��������
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