package com.yzsystem.sysparam.action;

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
import com.yzsystem.sysparam.service.SysParamService;
import com.yzsystem.sysparam.vo.SysParamVO;

public class SysParamAction extends BaseExtAction {

	@Override
	public SysParamService getService() {
		
		return (SysParamService)SpringBeanUtil.getBean("sysParamService");
	}
	/**
	 * 获取翻译模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExhProTransList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysParamVO> dataList = getService().getTransParam();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
		    public boolean apply(Object source, String name, Object value) {
		        if(name.equalsIgnoreCase("paramModuleCode") || name.equalsIgnoreCase("paramModuleName") || 
		        		name.equalsIgnoreCase("paramCode") || name.equalsIgnoreCase("paramName") || 
		        		name.equalsIgnoreCase("displayIndex")
		        		) { //不用传输到前台页面的属性
		            return true;
		        } else {
		            return false;
		        }
		    }
		});
		ExtJSONUtil.gridNoPaged(dataList, response, jsonConfig);
		return null;
	}
	
}
