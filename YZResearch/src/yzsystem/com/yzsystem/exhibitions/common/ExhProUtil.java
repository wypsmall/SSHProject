package com.yzsystem.exhibitions.common;

import javax.servlet.http.HttpServletRequest;

import com.imti.common.util.CodeUtil;
import com.imti.framework.common.StringUtil;
import com.yzsystem.exhibitions.common.search.ExhProSearch;

public class ExhProUtil {

	public static final ExhProSearch getExhProSearch(HttpServletRequest request) {
		ExhProSearch search = new ExhProSearch();
		String exhProId = request.getParameter("exhProId");
		/*展会条件*/
		search.setExhProId(StringUtil.isEmpty(exhProId) ? null : Integer.valueOf(exhProId));
		search.setExhProChzName(CodeUtil.decode(request.getParameter("exhProChzName")));
		search.setExhProNo(CodeUtil.decode(request.getParameter("exhProNo")));
		/*参展企业*/
		search.setEnterpriseName(CodeUtil.decode(request.getParameter("enterpriseName")));
		/*展会模板*/
		search.setExhProModuleCode(request.getParameter("exhProModuleCode"));
		return search;
	}

	public static String getExhProCondition(ExhProSearch search) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 参展企业查询条件
	 * 
	 * @param search
	 * @return
	 * @author 曹刚 新增日期：2013-5-16上午11:17:10
	 * @since 1.0
	 */
	public static String getExhProEnterCondition(ExhProSearch search) {
		StringBuffer sb = new StringBuffer();
		/*展会条件*/
		if(null != search.getExhProId()){
			sb.append(" and ep.exhProId=" + search.getExhProId());
		}
		if(StringUtil.isNotBlank(search.getExhProChzName())){
			sb.append(" and ep.exhProChzName like '%" + search.getExhProChzName() + "%'");
		}
		if(StringUtil.isNotBlank(search.getExhProNo())){
			sb.append(" and ep.exhProNo like '%" + search.getExhProNo() + "%'");
		}
		/*参展企业*/
		if(StringUtil.isNotBlank(search.getEnterpriseName())){
			sb.append(" and po.enterpriseName like '%" + search.getEnterpriseName() + "%'");
		}
		return "";
	}
}
