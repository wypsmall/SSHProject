package com.yzsystem.tperson.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;

import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.yzsystem.hr.po.HrEmployeePO;
import com.yzsystem.tperson.common.TPersonUtil;
import com.yzsystem.tperson.common.search.TPersonSearch;
import com.yzsystem.tperson.po.TPersonPO;
import com.yzsystem.tperson.service.TPersonService;

public class TPersonAction extends BaseExtAction {

	@Override
	public TPersonService getService() {
		
		return (TPersonService)SpringBeanUtil.getBean("tpersonService");
	}

	@SuppressWarnings("unchecked")
	public ActionForward getTPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TPersonSearch search = TPersonUtil.getTPersonSearch(request);
		String whereHQL = TPersonUtil.getTPersonCondition(search);
		List dataList = getService().getByHql(search.getStart(), search.getLimit(), " SELECT po from com.yzsystem.tperson.po.TPersonPO po where 1=1 " + whereHQL);
		int totalCnt = getService().getCountByCondition(TPersonPO.class, whereHQL);
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward upateTPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer personId = ServletRequestUtils.getIntParameter(request, "personId", -1);
		String personName = ServletRequestUtils.getStringParameter(request, "personName", null);
		try {
			getService().updateTPerson(personId, personName);
			ExtJSONUtil.writeSuccessInfo(true, response, "更新人员成功！");
		} catch (Exception e) {
			ExtJSONUtil.writeSuccessInfo(false, response, e.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		
		return null;
	}
}
