package com.yzsystem.hr.common;

import javax.servlet.http.HttpServletRequest;

import com.imti.common.util.CodeUtil;
import com.imti.framework.common.StringUtil;
import com.yzsystem.hr.common.search.HrSearch;

public class HrUtil {

	public static final HrSearch getHrSearch(HttpServletRequest request){
		HrSearch search = new HrSearch();
		search.setEmployeeNo(request.getParameter("employeeNo"));//职员工号
		search.setEmployeeName(CodeUtil.decode(request.getParameter("employeeName")));//职员姓名
		String employeeState = request.getParameter("employeeState");
		search.setEmployeeState(Short.valueOf(StringUtil.isEmpty(employeeState) ? "0" : employeeState));//在职状态（实习、试用期、正式职员、已离职）1实习2试用期3正式职员4已离职
		search.setOrgId(request.getParameter("orgId"));//部门主键
		search.setContractDateBegin(request.getParameter("contractDateBegin"));//合同到期日期查询开始日期
		search.setContractDateEnd(request.getParameter("contractDateEnd"));//合同到期日期查询结束日期
		return search;
	}
	public static final String getEmployeeCondition(HrSearch search){
		StringBuffer whereHQL = new StringBuffer();
		if(StringUtil.isNotEmpty(search.getEmployeeNo())){
			whereHQL.append(" and po.employeeNo like '%" + search.getEmployeeNo() + "%'");
		}
		if(StringUtil.isNotEmpty(search.getEmployeeName())){
			whereHQL.append(" and po.employeeName like '%" + search.getEmployeeName() + "%'");
		}
		if(search.getEmployeeState() != 0){
			whereHQL.append(" and po.employeeState=" + search.getEmployeeState());
		}
		if(StringUtil.isNotEmpty(search.getOrgId())){
			whereHQL.append(" and org.orgId='" + search.getOrgId() + "'");
		}
		if(StringUtil.isNotEmpty(search.getContractDateBegin())){
			whereHQL.append(" and po.contractEndDate>='" + search.getContractDateBegin() + " 00:00:00'");
		}
		if(StringUtil.isNotEmpty(search.getContractDateEnd())){
			whereHQL.append(" and po.contractEndDate<='" + search.getContractDateEnd() + " 23:59:59'");
		}
		return whereHQL.toString();
	}
}
