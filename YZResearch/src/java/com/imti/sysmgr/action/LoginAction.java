package com.imti.sysmgr.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.imti.common.util.ImtiUtil;
import com.imti.framework.common.Base64Util;
import com.imti.framework.common.DateUtil;
import com.imti.framework.common.MD5Encoder;
import com.imti.framework.common.StringUtil;
import com.imti.framework.common.constant.SessionConstant;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.sysmgr.po.OrgPO;
import com.imti.sysmgr.service.LoginLogService;
import com.imti.sysmgr.service.SysMgrService;
import com.imti.sysmgr.servlet.RandomCode;
import com.imti.sysmgr.vo.LoginLogVO;
import com.imti.sysmgr.vo.OrgVO;
import com.imti.sysmgr.vo.UserVO;

public class LoginAction extends BaseExtAction {
	
	public SysMgrService getService() {
		return (SysMgrService)SpringBeanUtil.getBean("sysMgrService");
	}
	public LoginLogService getLoginLogService(){
		return (LoginLogService)SpringBeanUtil.getBean("loginLogService");
	}
	
	public ActionForward doLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String ztId = request.getParameter("ztId");
		String randomCode = request.getParameter("randomCode");
		
		if(StringUtils.isBlank(loginId)){
			ExtJSONUtil.writeSuccessInfo(false, response, "账号不能为空！");
			return null ;
		}else if(StringUtils.isBlank(password)){
			ExtJSONUtil.writeSuccessInfo(false, response, "密码不能为空！");
			return null ;
		}else if(StringUtils.isBlank(ztId)){
			ExtJSONUtil.writeSuccessInfo(false, response, "帐套不能为空！");
			return null ;
		}else if(StringUtils.isBlank(randomCode)){
			ExtJSONUtil.writeSuccessInfo(false, response, "验证码不能为空！");
			return null ;
		}
		
		if(request.getSession().getAttribute(RandomCode.RANDOM_CODE_VERIFY) != null){
			String sessionRandomCode = (String)request.getSession().getAttribute(RandomCode.RANDOM_CODE_VERIFY);
			if(!sessionRandomCode.equals(randomCode)){
				ExtJSONUtil.writeSuccessInfo(false, response, "验证码不对！");
				return null ;
			}
		}
		
		try{
			checkedLogin(mapping, request, loginId, password,ztId, randomCode);
			ExtJSONUtil.writeSuccessInfo(true, response, "");
		}catch(Exception e){
			ExtJSONUtil.writeSuccessInfo(false, response, e.getMessage());
		}
		
//		if (StringUtils.isBlank(loginId) || StringUtils.isBlank(password) || StringUtils.isBlank(ztId)) {
//			request.setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ERROR, "账号或密码出错，请重新录入！");
//			return mapping.findForward("reLogin");
//		} else {
//			return checkedLogin(mapping, request, loginId, password,ztId, randomCode);
//		}
		return null;
	}
	
	/*
	 *	1、账号与密码验证
	 *	2、加载用户附加信息： （1）操作资源
	 */
	private ActionForward checkedLogin(ActionMapping mapping, HttpServletRequest request, String loginId, String password,String ztId, String randomCode) {
		//预留字段
		String loginType = request.getParameter("loginType");
		
		//根据登录的ID到数据库中查找用户对象
		UserVO userVO = getService().getUserByLoginId(loginId,ztId);
		
		//判断账号是否存在
		if(userVO == null){
			throw new RuntimeException("账号不存在");
		}
		
		//登录日志
		LoginLogVO loginLog = new LoginLogVO();
		String loginIp = request.getRemoteAddr();
		if(StringUtils.isBlank(loginIp)){
			loginLog.setLoginIP("非法的IP地址");
		}else {
			loginLog.setLoginIP(loginIp);
		}
		loginLog.setLoginTime(DateUtil.DateTOStr(new Date()));
		loginLog.setLoginId(loginId);
		loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_YES);//默认登录成功
		loginLog.setType(LoginLogVO.LOGIN);
		loginLog.setUserName(userVO.getUserName());
		loginLog.setZtId(userVO.getZtId());
		loginLog.setLoginTime(DateUtil.getCurrentDateTime());
		//账号存在的情况,注意这里代码里写死了密码中的固定字符串
		if(userVO.getPassword().indexOf("DIGEST_MD5_") != -1){//MD5校验密码
			String md5_password = "DIGEST_MD5_" + MD5Encoder.md5Encode(password);
			if(!md5_password.equals(userVO.getPassword())){
				//记录登录日志
				loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_NO);
				getLoginLogService().insert(loginLog);
				//request.setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ERROR, "账号或密码出错，请重新录入！");
				//return mapping.findForward("reLogin");
				throw new RuntimeException("账号或密码出错，请重新录入");
			}
		}else if(userVO.getPassword().indexOf("DIGEST_BASE64_") != -1){//base64校验密码
			String base64_password = "DIGEST_BASE64_" + Base64Util.base64Encoder(password);
			if(!base64_password.equals(userVO.getPassword())){
				//记录登录日志
				loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_NO);
				getLoginLogService().insert(loginLog);
				//request.setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ERROR, "账号或密码出错，请重新录入！");
				//return mapping.findForward("reLogin");
				throw new RuntimeException("账号或密码出错，请重新录入");
			}
		} else {//普通明文密码
			if(!password.equals(userVO.getPassword())){
				//记录登录日志
				loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_NO);
				getLoginLogService().insert(loginLog);
				//request.setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ERROR, "账号或密码出错，请重新录入！");
				//return mapping.findForward("reLogin");
				throw new RuntimeException("账号或密码出错，请重新录入");
			}
		}
		
		//这里要特别注意，当操作指代的资源是子资源时，必须该子资源的父资源纳入到权限当中，否则会出现
		//拥有子资源的权限而没有父资源的权限的情况
		Map<String, String> rsOpeMap = getService().getRsOperatorMapById(userVO.getId());
		userVO.setRsOpeCodeMap(rsOpeMap);
		
		if(StringUtil.isNotBlank(userVO.getOrgId())){
			//用户所属分公司
			String orgId = userVO.getOrgId();
			OrgVO org = (OrgVO)getService().getByPk(OrgPO.class, orgId);
			request.getSession().setAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER_ORG, org);
		}
		
		//保存登录日志
		getLoginLogService().insert(loginLog);

		//session中保存相关信息
		request.getSession().setAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER, userVO);
		request.getSession().setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ZTID, userVO.getZtId());
		//权限信息
		request.getSession().setAttribute(SessionConstant.IMTI_SESSION_USER_PEMISSION, rsOpeMap);
		return mapping.findForward("loginSucess");
	}
	
	/**
	 * 退出系统
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doLogout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		UserVO userVO = (UserVO)request.getSession().getAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
		
		//登录日志
		LoginLogVO loginLog = new LoginLogVO();
		String loginIp = request.getRemoteAddr();
		if(StringUtils.isBlank(loginIp)){
			loginLog.setLoginIP("来自火星");
		}else {
			loginLog.setLoginIP(loginIp);
		}
		loginLog.setLoginTime(DateUtil.DateTOStr(new Date()));
		loginLog.setLoginId(userVO.getLoginId());
		loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_YES);//默认登录成功
		loginLog.setType(LoginLogVO.LOGOUT);
		loginLog.setUserName(userVO.getUserName());
		//保存登录日志
		getLoginLogService().insert(loginLog);
		
		//用户对象
		request.getSession().removeAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
		//用户权限信息
		request.getSession().removeAttribute(SessionConstant.IMTI_SESSION_USER_PEMISSION);
		ExtJSONUtil.writeSuccessInfo(true, response, "OK");
		return null;
	}
	
	public ActionForward resetPassWord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String loginPassword = request.getParameter("loginPassword");  
		try{
			UserVO user = ImtiUtil.getUser(request);
			getService().resetPassWord(user, oldPassword, newPassword, loginPassword);//修改密码
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	public ActionForward backPassWord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");
		try{
			getService().backPassWord(userId);//用户密码还原
			ExtJSONUtil.writeSuccessInfo(true, response, "还原成功！");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
}
