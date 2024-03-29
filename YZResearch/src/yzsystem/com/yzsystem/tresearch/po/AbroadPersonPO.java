package com.yzsystem.tresearch.po;

import com.imti.framework.web.po.BasePO;

public class AbroadPersonPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7295613050936970302L;
	private Integer apid;		//标识id	
	private CompanyPO companyPO;	//公司		
	private ExhibitionPO exhibitionPO;	//展会
	private String apaddress;	//住址
	private String apname;		//姓名
	private String apspell;		//全拼
	private Integer apsex;		//性别	0-女，1-男
	private Integer apmstate;	//婚否	0-未婚，1-已婚
	private String apduties;	//职务
	private String apbirthday;	//出生日期yyyy-mm-dd
	private String apborn;		//出生省市
	private String aptel;		//电话
	private String apmobile;	//手机
	private String apfax;		//传真
	private String apcardno;	//身份证号
	private Integer appasstype;	//护照类型	0-因私护照，1-公务护照
	private String appassno;	//护照号
	private String appassdate;	//护照有效期yyyy-mm-dd
	private Integer aptriptype;	//行程类型	0-展期随团，1-全程随团，3-特殊
	private Integer apdeparture;//出发地		0-香港，1-广州，2-上海
	private Integer aproomtype;	//房间类型	0-vip，1-双床，2-大床
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
