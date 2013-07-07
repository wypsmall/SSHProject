package com.yzsystem.exhibitions.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.imti.framework.common.StringUtil;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.yzsystem.exhibitions.common.HostunitUtil;
import com.yzsystem.exhibitions.common.search.HostunitSearch;
import com.yzsystem.exhibitions.service.HostunitService;
import com.yzsystem.exhibitions.vo.HostunitVO;

public class HostunitAction extends BaseExtAction{

	@Override
	public HostunitService getService() {
		
		return (HostunitService)SpringBeanUtil.getBean("hostunitService");
	}

	
	public ActionForward getHostunitList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HostunitSearch search = HostunitUtil.getHostunitSearch(request);
		List<HostunitVO> dataList = getService().getValidHostunit(search);
		int totalCnt = getService().countValidHostunit(search);
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
	/**
	 * 新增主办单位
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 曹刚 新增日期：2013-4-17上午09:19:04
	 * @since 1.0
	 */
	public ActionForward insetHostunit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HostunitVO hostunit = HostunitUtil.getHostunit(request);
		try{
			getService().insetHostunit(hostunit);
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	/**
	 * 修改主办单位资料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-17上午09:27:21
	 * @since 1.0
	 */
	public ActionForward updateHostunit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HostunitVO hostunit = HostunitUtil.getHostunit(request);
		try{
			getService().updateHostunit(hostunit);
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	public ActionForward deleteHostunit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try{
			String hostUnitId = request.getParameter("hostUnitId");
			if(StringUtil.isNotBlank(hostUnitId)){
				getService().deleteHostunit(hostUnitId);
				ExtJSONUtil.writeSuccessInfo(true, response, "删除成功！");
			}else{
				ExtJSONUtil.writeSuccessInfo(true, response, "主办单位关键标识丢失，删除失败！");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
}
