package com.imti.framework.web.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.imti.framework.common.BeanUtil;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;
import com.imti.framework.web.environment.utils.RequestUtil;
import com.imti.framework.web.exception.constant.ExceptionConstant;
import com.imti.framework.web.log.ExtLog;
import com.imti.framework.web.service.BaseService;
import com.imti.framework.web.vo.BaseVO;

public abstract class BaseExtAction_old extends Action{

	private static ExtLog log  = new ExtLog(BaseExtAction_old.class);
	@SuppressWarnings("unchecked")
	private HashMap methods = new HashMap(); 
	@SuppressWarnings("unchecked")
	public abstract BaseService getService();
	/* *
	 * 1、优先读取属性文件中配置的请求参数
	 * 2、请求参数判断
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String action = "";
		String actParam = PropertyUtils.getProperty("imti.struts.action.param");
		if(StringUtils.isEmpty(actParam)){
			action = request.getParameter("action");
			if(StringUtils.isEmpty(action)){
				action = request.getParameter("method");
			}
		}else{
			action = request.getParameter(actParam);
		}
		if(StringUtils.isEmpty(action)){
			ExtJSONUtil.writeSuccessInfo(false, response, ExceptionConstant.IMTI_ACTION_PARAM);
			return null;
		}
		try{
			actionCenter(mapping, form, request,response, action);
		}catch (Exception ex) {
			log.error(ex);
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private void actionCenter(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response,
			String action){
		ActionConfigHelper cfg = new ActionConfigHelper();
		try{
			if(actionform != null){
				cfg.setBaseForm((BaseForm)actionform);
			}
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		try{
			cfg.setMapping(mapping);
			cfg.setRequest(request);
			cfg.setResponse(response);
			cfg.setSession(request.getSession());
			String voClass = mapping.getParameter();
			BaseVO baseVO = RequestUtil.getBaseVO(voClass);
			cfg.setVo(baseVO);
			if(ActionConstant.EDIT_ACTION.equals(action)){//toEdit
				toEdit(cfg);
			} else if (ActionConstant.VIEW_ACTION.equals(action)){//toView
				toView(cfg);
			} else if (ActionConstant.LIST_WIDTH_PAGE_ACTION.equals(action)){//toListWithPage
				toList(cfg);
			} else if (ActionConstant.LIST_WIDTH_NO_PAGE_ACTION.equals(action)){//toListWithNoPage
				toListNoPage(cfg);
			} else if( ActionConstant.INSERT_ACTION.equals(action)){//insert
				insert(cfg);
			} else if (ActionConstant.UPDATE_ACTION.equals(action)){//update
				update(cfg);
			} else if  (ActionConstant.DELETE_ACTION.equals(action)){//delete
				delete(cfg);
			} else if (ActionConstant.SAVE_ACTION.equals(action)){//save
				save(cfg);
			}  else {
				Class types[] = { ActionConfigHelper.class };
				Object values[] = { cfg };
				performMethod(action, types, values);
			}
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	@SuppressWarnings("unchecked")
	private void performMethod(String action, Class[] types, Object[] values) {
		Method method;
		try {
			synchronized (methods) {
				method = (Method) methods.get(action);
				if (method == null) {
					method = this.getClass().getMethod(action,types);
				}
			}
			method.invoke(this,values);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	/**
	 * <li>根据主键查询得到对象实体
	 * @param cfg
	 */
	public void toEdit(ActionConfigHelper cfg) {
		try{
			String pk = RequestUtil.getPropertyValue(BeanUtil.getPkField(cfg.getVo()));
			BaseVO vo = (BaseVO)getService().getByPk(cfg.getVo().getClass(), pk);
			ExtJSONUtil.writeObject(vo, cfg.getResponse());
		}catch(Exception ex){
			log.info(ex);
			ExtJSONUtil.writeObject(new BaseVO(), cfg.getResponse());
		}
	}
	/**
	 * <li>查询所有满足条件的数据，带分页
	 * @param cfg
	 */
	public void toList(ActionConfigHelper cfg) {
		String startStr = cfg.getRequest().getParameter("start");
		String limitStr = cfg.getRequest().getParameter("limit"); 

		String whereHql = "";
		int total = getService().getCountByCondition(BeanUtil.getPOClass(cfg.getVo()), whereHql);
		List list = getService().getByCondition(BeanUtil.getPOClass(cfg.getVo()), startStr, limitStr, whereHql);
		ExtJSONUtil.gridWithPaged(list, total, cfg.getResponse(), null);
	}
	/**
	 * <li>查询所有满足条件的数据，不分页
	 * @param cfg
	 */
	public void toListNoPage(ActionConfigHelper cfg) {
		String whereHql = "";
		
		List list = getService().getByConditionWithNoPage(BeanUtil.getPOClass(cfg.getVo()), whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			ExtJSONUtil.gridWithPaged(list, list.size(), cfg.getResponse(), null);
		}
	}
	/**
	 * <li>根据主键查找实体
	 * @param cfg
	 */
	public void toView(ActionConfigHelper cfg) {
		try{
			String pk = RequestUtil.getPropertyValue(BeanUtil.getPkField(cfg.getVo()));
			BaseVO vo = (BaseVO)getService().getByPk(cfg.getVo().getClass(), pk);
			ExtJSONUtil.writeObject(vo, cfg.getResponse());
		}catch(Exception ex){
			log.info(ex);
			ExtJSONUtil.writeObject(new BaseVO(), cfg.getResponse());
		}
	}
	
	/**
	 * <li>保存内容
	 * @param cfg
	 */
	@SuppressWarnings("unchecked")
	public void insert(ActionConfigHelper cfg) {
		try{
			getService().insert(cfg.getVo());
			ExtJSONUtil.writeSuccessInfo(true, cfg.getResponse(), "");
		}catch(Exception ex){
			log.info(ex);
			ExtJSONUtil.writeSuccessInfo(true, cfg.getResponse(), ex.getMessage());
		}
	}
	
	/**
	 * <li>保存内容
	 * @param cfg
	 */
	@SuppressWarnings("unchecked")
	public void save(ActionConfigHelper cfg) {
		try{
			getService().save(cfg.getVo());
			ExtJSONUtil.writeSuccessInfo(true, cfg.getResponse(), "");
		}catch(Exception ex){
			log.info(ex);
			ExtJSONUtil.writeSuccessInfo(true, cfg.getResponse(), ex.getMessage());
		}
	}
	
	/**
	 * <li>修改实体内容
	 * @param cfg
	 */
	@SuppressWarnings("unchecked")
	public void update(ActionConfigHelper cfg) {
		try{
			getService().update(cfg.getVo());
			ExtJSONUtil.writeSuccessInfo(true, cfg.getResponse(), "");
		}catch(Exception ex){
			log.info(ex);
			ExtJSONUtil.writeSuccessInfo(true, cfg.getResponse(), ex.getMessage());
		}
	}
	/**
	 * <li>根据主键删除实体
	 * @param cfg
	 */
	public void delete(ActionConfigHelper cfg) {
		try{
			String pk = RequestUtil.getPropertyValue(BeanUtil.getPkField(cfg.getVo()));
			getService().deleteByPk(cfg.getVo().getClass(), new String[]{pk});
			ExtJSONUtil.writeSuccessInfo(true, cfg.getResponse(), "");
		}catch(Exception ex){
			log.info(ex);
			ExtJSONUtil.writeSuccessInfo(true, cfg.getResponse(), ex.getMessage());
		}
	}
}
