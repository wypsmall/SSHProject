package com.yzsystem.hr.vo;

import com.imti.framework.common.DateUtil;
import com.imti.framework.web.vo.BaseVO;

public class HrEmployeeVO extends BaseVO {
	
	/**
	 * ˵����������Ե�ҵ����
	 */
	private static final long serialVersionUID = 5397488594977174082L;
	//������Ϣ
	private String employeeId;//ְԱ������ʶ
	private String employeeNo;//ְԱ����
	private String employeeName;//ְԱ����
	private String employeeSex = "1";//�Ա�
	private String employeeBirthType = "1";//�������ͣ�ũ��or������
	private String employeeBirth;//����
	private String employeePostName;//ְλ
	private String employeeState = "2";//��ְ״̬��ʵϰ�������ڡ���ʽְԱ������ְ��
	private String entryJobDate;//��ְ����
	private String contractStartDate;//��ͬ��������
	private String contractEndDate;//��ͬ��ֹ����
	private String offJobDate;//��ְ����
	private String identification;//���֤��
	private String contact;//��ϵ��
	private String contactTel;//��ϵ�˵绰
	private String memo;//��ע
	private String homeAddress;//��ͥסַ
	private String accountType;//1ũҵ����2��ũҵ����
	private String hasBuyShebao = "0";
	//��������
	private String graduateDate;//��ҵʱ��
	private String graduateSch;//��ҵԺУ
	private String education;//ѧ��
	private String profession;//רҵ
	//ְԱ��������
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
