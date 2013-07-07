package com.imti.framework.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;

import com.imti.framework.web.vo.BaseVO;


public class ActionConfigHelper {

	private ActionMapping mapping;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private BaseForm baseForm;
	private BaseVO vo;
	
	public ActionMapping getMapping() {
		return mapping;
	}
	public void setMapping(ActionMapping mapping) {
		this.mapping = mapping;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public BaseForm getBaseForm() {
		return baseForm;
	}
	public void setBaseForm(BaseForm baseForm) {
		this.baseForm = baseForm;
	}
	public BaseVO getVo() {
		return vo;
	}
	public void setVo(BaseVO vo) {
		this.vo = vo;
	}
}
