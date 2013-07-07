package com.yzsystem.tresearch.action;

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
import com.yzsystem.tresearch.common.PassengerUtil;
import com.yzsystem.tresearch.common.search.PassengerSearch;
import com.yzsystem.tresearch.po.PassengerPO;
import com.yzsystem.tresearch.service.PassengerService;

public class PassengerAction extends BaseExtAction {

	@Override
	public PassengerService getService() {
		
		return (PassengerService)SpringBeanUtil.getBean("passengerService");
	}

	@SuppressWarnings("unchecked")
	public ActionForward getPassengers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PassengerSearch search = PassengerUtil.getPassengerSearch(request);
		String whereHQL = PassengerUtil.getPassengerCondition(search);
		List dataList = getService().getByHql(search.getStart(), search.getLimit(), " SELECT po from com.yzsystem.tresearch.po.PassengerPO po where 1=1 " + whereHQL + " order by po.ucompany, po.usex, po.uroom");
		int totalCnt = getService().getCountByCondition(PassengerPO.class, whereHQL);
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
	
	public ActionForward saveGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String data = ServletRequestUtils.getStringParameter(request, "groupdata");
		System.out.println("==========================="+data);
		getService().saveRoom(data);
		ExtJSONUtil.writeSuccessInfo(true, response);
		return null;
	}
	public ActionForward batchSaveRoomNo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String batchdata = ServletRequestUtils.getStringParameter(request, "batchdata");
		String roomNo = ServletRequestUtils.getStringParameter(request, "roomNo");
		System.out.println("==========================="+batchdata);
		System.out.println("==========================="+roomNo);
		getService().batchSaveRoomNo(batchdata, roomNo);
		ExtJSONUtil.writeSuccessInfo(true, response);
		return null;
	}
}
