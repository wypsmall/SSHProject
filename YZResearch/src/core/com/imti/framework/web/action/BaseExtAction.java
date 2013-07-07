package com.imti.framework.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.imti.framework.common.BeanUtil;
import com.imti.framework.common.StringUtils;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.service.BaseService;
import com.imti.framework.web.vo.BaseVO;

public abstract class BaseExtAction extends DispatchAction {

	public abstract BaseService getService();
	private ActionConfigHelper cfg = new ActionConfigHelper();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//权限判断：没有权限，给出提示直接返回信息到界面上
		
		
		//mapping配置参数
		String action = mapping.getParameter();
		if(StringUtils.isBlank(action)){
			throw new RuntimeException("Struts配置文件中parameter参数没有配置");
		}
		
		//得到请求的方法名
		String method = request.getParameter(action);
		if(StringUtils.isBlank(method)){
			throw new RuntimeException("非法请求路径（请求路径中没有方法名）;请检查url");
		}
		
		//组装控制帮助类
		cfg.setMapping(mapping);
		if(form != null){
			cfg.setBaseForm((BaseForm)form);
		}
		cfg.setRequest(request);
		cfg.setResponse(response);
		cfg.setSession(request.getSession());
		cfg.setVo(getVO());
		//定义一些常见的方法
		if(ActionConstant.LIST_WIDTH_PAGE_ACTION.equals(method)){//toList方法
			toList(mapping, form, request, response);
		}else if (ActionConstant.LIST_WIDTH_NO_PAGE_ACTION.equals(action)){//toListWithNoPage
			toListNoPage(mapping, form, request, response);
		}else {
			return super.execute(mapping, form, request, response);
		}
		return null;
	}


	public void toListNoPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
	}

	public BaseVO getVO(){
		return new BaseVO();
	}

	public void toList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String whereHql = "";
		Class cla = BeanUtil.getPOClass(cfg.getVo());
		List list = getService().getByCondition(cla, startStr, limitStr, whereHql);
		int totalCnt = getService().getCountByCondition(cla, whereHql);
		ExtJSONUtil.gridWithPaged(list, totalCnt, cfg.getResponse(), null);
	}


	public ActionConfigHelper getCfg() {
		return cfg;
	}

	public void setCfg(ActionConfigHelper cfg) {
		this.cfg = cfg;
	}

}
