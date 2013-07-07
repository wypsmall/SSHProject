package com.imti.sysmgr.common;

import javax.servlet.http.HttpServletRequest;

import com.imti.common.util.CodeUtil;
import com.imti.framework.common.StringUtil;
import com.imti.framework.common.StringUtils;
import com.imti.sysmgr.common.search.SysMgrSearch;

public class SysMgrUtil {

	public static final SysMgrSearch getSysMgrSearch(HttpServletRequest request){
		SysMgrSearch search = new SysMgrSearch();
		search.setRootModuleName(CodeUtil.decode(request.getParameter("rootModuleName")));//一级模块名称
		search.setSecondModuleName(CodeUtil.decode(request.getParameter("secondModuleName")));//二级模块名称
		search.setThirdModuleName(CodeUtil.decode(request.getParameter("thirdModuleName")));//三级模块名称
		search.setOperateName(CodeUtil.decode(request.getParameter("operateName")));//操作名称
		search.setOperateStatusName(CodeUtil.decode(request.getParameter("operateStatusName")));//操作状态
		search.setOperator(CodeUtil.decode(request.getParameter("operator")));//操作人
		search.setOpeDateBegin(request.getParameter("opeDateBegin"));
		search.setOpeDateEnd(request.getParameter("opeDateEnd"));
		search.setStart(request.getParameter("start"));
		search.setLimit(request.getParameter("limit"));
		return search;
	}
	public static final String getBusiCondition(SysMgrSearch search){
		StringBuffer whereHQL = new StringBuffer();
		if(StringUtil.isNotBlank(search.getRootModuleName())){
			whereHQL.append(" and po.rootModuleName like '%"+search.getRootModuleName()+"%'");
		}
		if(StringUtil.isNotBlank(search.getSecondModuleName())){
			whereHQL.append(" and po.secondModuleName like '%"+search.getSecondModuleName()+"%'");
		}
		if(StringUtil.isNotBlank(search.getThirdModuleName())){
			whereHQL.append(" and po.thirdModuleName like '%"+search.getThirdModuleName()+"%'");
		}
		if(StringUtil.isNotBlank(search.getOperateName())){
			whereHQL.append(" and po.operateName like '%"+search.getOperateName()+"%'");
		}
		if(StringUtil.isNotBlank(search.getOperateStatusName())){
			whereHQL.append(" and po.operateStatusName like '%"+search.getOperateStatusName()+"%'");
		}
		if(StringUtil.isNotBlank(search.getOperator())){
			whereHQL.append(" and po.operator like '%"+search.getOperator()+"%'");
		}
		if(StringUtil.isNotBlank(search.getOpeDateBegin())){
			whereHQL.append(" and po.opeDateBegin>='"+search.getOpeDateBegin()+" 00:00:00'");
		}
		if(StringUtil.isNotBlank(search.getOpeDateEnd())){
			whereHQL.append(" and po.opeDateBegin<='"+search.getOpeDateEnd()+"  23:59:59'");
		}
		return whereHQL.toString();
	}
	public static String getLoginLoCondition(SysMgrSearch search) {
		StringBuffer whereHQL = new StringBuffer();
		if(StringUtil.isNotBlank(search.getOperator())){
			whereHQL.append(" and po.userName like '%"+search.getOperator()+"%'");
		}
		if(StringUtils.isNotEmpty(search.getOpeDateBegin())){
			whereHQL.append(" and po.loginTime>= '" + search.getOpeDateBegin() + " 00:00:00' ");
		}
		if(StringUtils.isNotEmpty(search.getOpeDateEnd())){
			whereHQL.append(" and po.loginTime< '" + search.getOpeDateEnd() + " 23:59:59'") ;
		}
		return whereHQL.toString();
	}
}
