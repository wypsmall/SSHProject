package com.yzsystem.tresearch.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;

import com.imti.framework.common.constant.SessionConstant;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.service.BaseService;
import com.imti.sysmgr.vo.UserVO;
import com.neo.common.utils.DateUtils;
import com.neo.common.vo.IValueObject;
import com.yzsystem.tresearch.action.form.FlowAppForm;
import com.yzsystem.tresearch.po.UserDO;
import com.yzsystem.tresearch.service.IFlowAppService;
import com.yzsystem.tresearch.service.IUserService;
import com.yzsystem.tresearch.vo.FlowAppVO;

public class FlowAppAction extends BaseExtAction {

	private IFlowAppService flowAppService;
	private IUserService userService;
	
	
	public IFlowAppService getFlowAppService() {
		return flowAppService;
	}
	public void setFlowAppService(IFlowAppService flowAppService) {
		this.flowAppService = flowAppService;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
//	public ActionForward getUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Integer start = ServletRequestUtils.getIntParameter(request, "start", 0);
//		Integer limit = ServletRequestUtils.getIntParameter(request, "limit" , 0);
//		GridJsonPageData<IValueObject> result = (GridJsonPageData<IValueObject>)userService.getDomainObjectPageJson(new Object[]{""}, start, limit);
//		ExtJSONUtil.gridWithPaged(result.getResults(), result.getTotalCount(), response, null);
//		return null;
//	}
	
	public ActionForward getUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDO userDO = ((FlowAppForm)form).getUserDO();
		List<IValueObject> res = userService.getUserNoPage(userDO);
		ExtJSONUtil.gridNoPaged(res, response, null);
		return null;
	}
	
	public ActionForward getAppFlows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserVO userVO = (UserVO)request.getSession().getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
		int sFlag = ServletRequestUtils.getIntParameter(request, "sFlag", 0);
		FlowAppVO flowAppVO = ((FlowAppForm)form).getFlowAppVO();
		if(sFlag==0)
			flowAppVO.setFlwProposerId(userVO.getId());
		else
			flowAppVO.setFlwAuditorId(userVO.getId());
		Integer start = ServletRequestUtils.getIntParameter(request, "start", 0);
		Integer limit = ServletRequestUtils.getIntParameter(request, "limit" , 20);
		List<IValueObject> res = flowAppService.getFlows(flowAppVO, start, limit);
		Integer total = flowAppService.getFlowsCount(flowAppVO);
		ExtJSONUtil.gridWithPaged(res, total, response, null);
		return null;
	}
	
	public ActionForward submitAppFlows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FlowAppVO flowAppVO = ((FlowAppForm)form).getFlowAppVO();
		UserVO userVO = (UserVO)request.getSession().getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
		flowAppVO.setFlwProposerId(userVO.getId());
		flowAppVO.setInTime(DateUtils.convertDateTOLongString(new Date()));
		flowAppVO.setFlwStatus(1);
		flowAppService.submitFlow(flowAppVO);
		return null;
	}
	
	public ActionForward replyAppFlows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FlowAppVO flowAppVO = ((FlowAppForm)form).getFlowAppVO();
		flowAppVO.setModTime(DateUtils.convertDateTOLongString(new Date()));
		flowAppService.replyFlow(flowAppVO);
		return null;
	}
	@Override
	public BaseService getService() {
		return null;
	}
	
}
