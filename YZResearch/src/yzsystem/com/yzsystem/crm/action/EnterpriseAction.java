package com.yzsystem.crm.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.yzsystem.crm.common.EnterpriseUtil;
import com.yzsystem.crm.common.search.EnterpriseSearch;
import com.yzsystem.crm.service.EnterpriseService;
import com.yzsystem.crm.vo.EnterpriseVO;

public class EnterpriseAction extends BaseExtAction {

	@Override
	public EnterpriseService getService() {
		
		return (EnterpriseService)SpringBeanUtil.getBean("enterpriseService");
	}
	public ActionForward listEnterprise(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EnterpriseSearch search = EnterpriseUtil.getSearch(request);
		List<EnterpriseVO> dataList = getService().getEnterpriseList(search);
		int totalCnt = getService().countEnterprise(search);
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
}
