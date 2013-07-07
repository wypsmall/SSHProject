package com.yzsystem.hr.action.form;

import com.imti.framework.web.action.BaseForm;
import com.yzsystem.hr.vo.HrEmpPosHisVO;
import com.yzsystem.hr.vo.HrEmployeeVO;
import com.yzsystem.hr.vo.HrOrgVO;

public class HrForm extends BaseForm {

	private static final long serialVersionUID = 8198344225992919701L;
	
	private HrOrgVO org = new HrOrgVO();
	private HrEmployeeVO emp = new HrEmployeeVO();
	private HrEmpPosHisVO hsi = new HrEmpPosHisVO();
	
	public HrOrgVO getOrg() {
		return org;
	}
	public void setOrg(HrOrgVO org) {
		this.org = org;
	}
	public HrEmployeeVO getEmp() {
		return emp;
	}
	public void setEmp(HrEmployeeVO emp) {
		this.emp = emp;
	}
	public HrEmpPosHisVO getHsi() {
		return hsi;
	}
	public void setHsi(HrEmpPosHisVO hsi) {
		this.hsi = hsi;
	}
	
	
}
