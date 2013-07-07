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
			ExtJSONUtil.writeSuccessInfo(false, response, "�˺Ų���Ϊ�գ�");
			return null ;
		}else if(StringUtils.isBlank(password)){
			ExtJSONUtil.writeSuccessInfo(false, response, "���벻��Ϊ�գ�");
			return null ;
		}else if(StringUtils.isBlank(ztId)){
			ExtJSONUtil.writeSuccessInfo(false, response, "���ײ���Ϊ�գ�");
			return null ;
		}else if(StringUtils.isBlank(randomCode)){
			ExtJSONUtil.writeSuccessInfo(false, response, "��֤�벻��Ϊ�գ�");
			return null ;
		}
		
		if(request.getSession().getAttribute(RandomCode.RANDOM_CODE_VERIFY) != null){
			String sessionRandomCode = (String)request.getSession().getAttribute(RandomCode.RANDOM_CODE_VERIFY);
			if(!sessionRandomCode.equals(randomCode)){
				ExtJSONUtil.writeSuccessInfo(false, response, "��֤�벻�ԣ�");
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
//			request.setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ERROR, "�˺Ż��������������¼�룡");
//			return mapping.findForward("reLogin");
//		} else {
//			return checkedLogin(mapping, request, loginId, password,ztId, randomCode);
//		}
		return null;
	}
	
	/*
	 *	1���˺���������֤
	 *	2�������û�������Ϣ�� ��1��������Դ
	 */
	private ActionForward checkedLogin(ActionMapping mapping, HttpServletRequest request, String loginId, String password,String ztId, String randomCode) {
		//Ԥ���ֶ�
		String loginType = request.getParameter("loginType");
		
		//���ݵ�¼��ID�����ݿ��в����û�����
		UserVO userVO = getService().getUserByLoginId(loginId,ztId);
		
		//�ж��˺��Ƿ����
		if(userVO == null){
			throw new RuntimeException("�˺Ų�����");
		}
		
		//��¼��־
		LoginLogVO loginLog = new LoginLogVO();
		String loginIp = request.getRemoteAddr();
		if(StringUtils.isBlank(loginIp)){
			loginLog.setLoginIP("�Ƿ���IP��ַ");
		}else {
			loginLog.setLoginIP(loginIp);
		}
		loginLog.setLoginTime(DateUtil.DateTOStr(new Date()));
		loginLog.setLoginId(loginId);
		loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_YES);//Ĭ�ϵ�¼�ɹ�
		loginLog.setType(LoginLogVO.LOGIN);
		loginLog.setUserName(userVO.getUserName());
		loginLog.setZtId(userVO.getZtId());
		loginLog.setLoginTime(DateUtil.getCurrentDateTime());
		//�˺Ŵ��ڵ����,ע�����������д���������еĹ̶��ַ���
		if(userVO.getPassword().indexOf("DIGEST_MD5_") != -1){//MD5У������
			String md5_password = "DIGEST_MD5_" + MD5Encoder.md5Encode(password);
			if(!md5_password.equals(userVO.getPassword())){
				//��¼��¼��־
				loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_NO);
				getLoginLogService().insert(loginLog);
				//request.setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ERROR, "�˺Ż��������������¼�룡");
				//return mapping.findForward("reLogin");
				throw new RuntimeException("�˺Ż��������������¼��");
			}
		}else if(userVO.getPassword().indexOf("DIGEST_BASE64_") != -1){//base64У������
			String base64_password = "DIGEST_BASE64_" + Base64Util.base64Encoder(password);
			if(!base64_password.equals(userVO.getPassword())){
				//��¼��¼��־
				loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_NO);
				getLoginLogService().insert(loginLog);
				//request.setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ERROR, "�˺Ż��������������¼�룡");
				//return mapping.findForward("reLogin");
				throw new RuntimeException("�˺Ż��������������¼��");
			}
		} else {//��ͨ��������
			if(!password.equals(userVO.getPassword())){
				//��¼��¼��־
				loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_NO);
				getLoginLogService().insert(loginLog);
				//request.setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ERROR, "�˺Ż��������������¼�룡");
				//return mapping.findForward("reLogin");
				throw new RuntimeException("�˺Ż��������������¼��");
			}
		}
		
		//����Ҫ�ر�ע�⣬������ָ������Դ������Դʱ�����������Դ�ĸ���Դ���뵽Ȩ�޵��У���������
		//ӵ������Դ��Ȩ�޶�û�и���Դ��Ȩ�޵����
		Map<String, String> rsOpeMap = getService().getRsOperatorMapById(userVO.getId());
		userVO.setRsOpeCodeMap(rsOpeMap);
		
		if(StringUtil.isNotBlank(userVO.getOrgId())){
			//�û������ֹ�˾
			String orgId = userVO.getOrgId();
			OrgVO org = (OrgVO)getService().getByPk(OrgPO.class, orgId);
			request.getSession().setAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER_ORG, org);
		}
		
		//�����¼��־
		getLoginLogService().insert(loginLog);

		//session�б��������Ϣ
		request.getSession().setAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER, userVO);
		request.getSession().setAttribute(SessionConstant.IMTI_SESSION_LOGIN_ZTID, userVO.getZtId());
		//Ȩ����Ϣ
		request.getSession().setAttribute(SessionConstant.IMTI_SESSION_USER_PEMISSION, rsOpeMap);
		return mapping.findForward("loginSucess");
	}
	
	/**
	 * �˳�ϵͳ
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
		
		//��¼��־
		LoginLogVO loginLog = new LoginLogVO();
		String loginIp = request.getRemoteAddr();
		if(StringUtils.isBlank(loginIp)){
			loginLog.setLoginIP("���Ի���");
		}else {
			loginLog.setLoginIP(loginIp);
		}
		loginLog.setLoginTime(DateUtil.DateTOStr(new Date()));
		loginLog.setLoginId(userVO.getLoginId());
		loginLog.setLoginSuccess(LoginLogVO.LOGIN_SUCCESS_YES);//Ĭ�ϵ�¼�ɹ�
		loginLog.setType(LoginLogVO.LOGOUT);
		loginLog.setUserName(userVO.getUserName());
		//�����¼��־
		getLoginLogService().insert(loginLog);
		
		//�û�����
		request.getSession().removeAttribute(SessionConstant.IMTI_SESSION_LOGIN_USER);
		//�û�Ȩ����Ϣ
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
			getService().resetPassWord(user, oldPassword, newPassword, loginPassword);//�޸�����
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
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
			getService().backPassWord(userId);//�û����뻹ԭ
			ExtJSONUtil.writeSuccessInfo(true, response, "��ԭ�ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
}
