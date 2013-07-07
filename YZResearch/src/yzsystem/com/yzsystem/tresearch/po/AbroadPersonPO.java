package com.yzsystem.tresearch.po;

import com.imti.framework.web.po.BasePO;

public class AbroadPersonPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7295613050936970302L;
	private Integer apid;		//��ʶid	
	private CompanyPO companyPO;	//��˾		
	private ExhibitionPO exhibitionPO;	//չ��
	private String apaddress;	//סַ
	private String apname;		//����
	private String apspell;		//ȫƴ
	private Integer apsex;		//�Ա�	0-Ů��1-��
	private Integer apmstate;	//���	0-δ�飬1-�ѻ�
	private String apduties;	//ְ��
	private String apbirthday;	//��������yyyy-mm-dd
	private String apborn;		//����ʡ��
	private String aptel;		//�绰
	private String apmobile;	//�ֻ�
	private String apfax;		//����
	private String apcardno;	//����֤��
	private Integer appasstype;	//��������	0-��˽���գ�1-������
	private String appassno;	//���պ�
	private String appassdate;	//������Ч��yyyy-mm-dd
	private Integer aptriptype;	//�г�����	0-չ�����ţ�1-ȫ�����ţ�3-����
	private Integer apdeparture;//������		0-��ۣ�1-���ݣ�2-�Ϻ�
	private Integer aproomtype;	//��������	0-vip��1-˫����2-��
	public Integer getApid() {
		return apid;
	}
	public void setApid(Integer apid) {
		this.apid = apid;
	}
	public CompanyPO getCompanyPO() {
		return companyPO;
	}
	public void setCompanyPO(CompanyPO companyPO) {
		this.companyPO = companyPO;
	}
	public ExhibitionPO getExhibitionPO() {
		return exhibitionPO;
	}
	public void setExhibitionPO(ExhibitionPO exhibitionPO) {
		this.exhibitionPO = exhibitionPO;
	}
	public String getApaddress() {
		return apaddress;
	}
	public void setApaddress(String apaddress) {
		this.apaddress = apaddress;
	}
	public String getApname() {
		return apname;
	}
	public void setApname(String apname) {
		this.apname = apname;
	}
	public String getApspell() {
		return apspell;
	}
	public void setApspell(String apspell) {
		this.apspell = apspell;
	}
	public Integer getApsex() {
		return apsex;
	}
	public void setApsex(Integer apsex) {
		this.apsex = apsex;
	}
	public Integer getApmstate() {
		return apmstate;
	}
	public void setApmstate(Integer apmstate) {
		this.apmstate = apmstate;
	}
	public String getApduties() {
		return apduties;
	}
	public void setApduties(String apduties) {
		this.apduties = apduties;
	}
	public String getApbirthday() {
		return apbirthday;
	}
	public void setApbirthday(String apbirthday) {
		this.apbirthday = apbirthday;
	}
	public String getApborn() {
		return apborn;
	}
	public void setApborn(String apborn) {
		this.apborn = apborn;
	}
	public String getAptel() {
		return aptel;
	}
	public void setAptel(String aptel) {
		this.aptel = aptel;
	}
	public String getApmobile() {
		return apmobile;
	}
	public void setApmobile(String apmobile) {
		this.apmobile = apmobile;
	}
	public String getApfax() {
		return apfax;
	}
	public void setApfax(String apfax) {
		this.apfax = apfax;
	}
	public String getApcardno() {
		return apcardno;
	}
	public void setApcardno(String apcardno) {
		this.apcardno = apcardno;
	}
	public Integer getAppasstype() {
		return appasstype;
	}
	public void setAppasstype(Integer appasstype) {
		this.appasstype = appasstype;
	}
	public String getAppassno() {
		return appassno;
	}
	public void setAppassno(String appassno) {
		this.appassno = appassno;
	}
	public String getAppassdate() {
		return appassdate;
	}
	public void setAppassdate(String appassdate) {
		this.appassdate = appassdate;
	}
	public Integer getAptriptype() {
		return aptriptype;
	}
	public void setAptriptype(Integer aptriptype) {
		this.aptriptype = aptriptype;
	}
	public Integer getApdeparture() {
		return apdeparture;
	}
	public void setApdeparture(Integer apdeparture) {
		this.apdeparture = apdeparture;
	}
	public Integer getAproomtype() {
		return aproomtype;
	}
	public void setAproomtype(Integer aproomtype) {
		this.aproomtype = aproomtype;
	}

	
	
}