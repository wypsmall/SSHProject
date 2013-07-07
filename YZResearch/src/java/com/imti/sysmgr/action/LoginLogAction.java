package com.imti.sysmgr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.sysmgr.common.SysMgrUtil;
import com.imti.sysmgr.common.search.SysMgrSearch;
import com.imti.sysmgr.po.BusiLogPO;
import com.imti.sysmgr.po.LoginLogPO;
import com.imti.sysmgr.service.LoginLogService;
public class LoginLogAction extends BaseExtAction {

	public LoginLogService getService() {
		return (LoginLogService)SpringBeanUtil.getBean("loginLogService");
	}
	/**
	 * �û���¼��־
	 */
	@SuppressWarnings("unchecked")
	public void toList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		SysMgrSearch search = SysMgrUtil.getSysMgrSearch(request);
		String whereHql = SysMgrUtil.getLoginLoCondition(search);
		List list = getService().getByCondition(LoginLogPO.class, search.getStart(), search.getLimit(), whereHql + " order by po.loginTime desc");
		int totalCnt = getService().getCountByCondition(LoginLogPO.class, whereHql);
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter(){
		    public boolean apply(Object source, String name, Object value) {
		        if(name.equalsIgnoreCase("id") || name.equalsIgnoreCase("loginId") || name.equalsIgnoreCase("ztId")) { //���ô��䵽ǰ̨ҳ�������
		            return true;
		        } else {
		            return false;
		        }
		    }
		}); 
		ExtJSONUtil.gridWithPaged(list, totalCnt, response, config);
	}
	/**
	 * ҵ�������־
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-19����09:23:13
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public ActionForward busiLogList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		SysMgrSearch search = SysMgrUtil.getSysMgrSearch(request);
		String whereHql = SysMgrUtil.getBusiCondition(search);
		List list = getService().getByCondition(BusiLogPO.class, search.getStart(), search.getLimit(), whereHql);
		int totalCnt = getService().getCountByCondition(BusiLogPO.class, whereHql);
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter(){
		    public boolean apply(Object source, String name, Object value) {
		        if(name.equalsIgnoreCase("logId")) { //���ô��䵽ǰ̨ҳ�������
		            return true;
		        } else {
		            return false;
		        }
		    }
		}); 
		ExtJSONUtil.gridWithPaged(list, totalCnt, response, null);
		return null;
	}
}
