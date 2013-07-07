package com.imti.sysmgr.action;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.imti.framework.common.StringUtils;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.sysmgr.po.WarnPO;
import com.imti.sysmgr.service.WarnService;

public class WarnAction extends BaseExtAction {

	@Override
	public WarnService getService() {
		return (WarnService)SpringBeanUtil.getBean("warnService");
	}
	public void toList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		//分页参数
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		
		//条件搜索参数
		String isHandle = request.getParameter("isHandle");
		String module = request.getParameter("module");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		String whereHql = "";
		
		try{	
			if(StringUtils.isNotEmpty(module) && module.indexOf("%") != -1){
				module = URLDecoder.decode(module,"UTF-8");
				whereHql = whereHql + " and po.module like '%" + module + "%'";
			}
		}catch(Exception e){
		}	
		if(StringUtils.isNotEmpty(isHandle)){
			whereHql = whereHql + " and po.isHandle='" + isHandle + "'";
		}
		if(StringUtils.isNotEmpty(startTime)){
			whereHql = whereHql + " and to_char(po.createDate,'yyyy-mm-dd')>= '" + startTime + "'";
		}
		if(StringUtils.isNotEmpty(endTime)){
			whereHql = whereHql + " and to_char(po.createDate,'yyyy-mm-dd')< '" + endTime + "'" ;
		}
		whereHql = whereHql + " order by po.createDate desc";
		List list = getService().getByCondition(WarnPO.class, startStr, limitStr, whereHql);
		int totalCnt = getService().getCountByCondition(WarnPO.class, whereHql);
		ExtJSONUtil.gridWithPaged(list, totalCnt, response, null);
	}
}
