package com.yzsystem.exhibitions.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.imti.common.util.ImtiUtil;
import com.imti.framework.common.DateUtil;
import com.imti.framework.common.StringUtil;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.yzsystem.exhibitions.action.form.ExhibitionProjectForm;
import com.yzsystem.exhibitions.common.ExhProUtil;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.service.ExhProService;
import com.yzsystem.exhibitions.vo.ExhProModuleVO;
import com.yzsystem.exhibitions.vo.ExhibitionProjectVO;

public class ExhibitionProjectAction extends BaseExtAction {

	@Override
	public ExhProService getService() {
		
		return (ExhProService)SpringBeanUtil.getBean("exhProService");
	}

	/**
	 * 展会列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-5-14下午01:34:07
	 * @since 1.0
	 */
	public ActionForward getExhProList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExhProSearch search = ExhProUtil.getExhProSearch(request);
		List<ExhibitionProjectVO> dataList = getService().getExhProList(search);
		int totalCnt = getService().countExhPro(search);
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
	/**
	 * 获取展会项目对应的翻译模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExhProTransList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExhProSearch search = ExhProUtil.getExhProSearch(request);
		List<ExhProModuleVO> dataList = getService().getExhProTransList(search);
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
		ExtJSONUtil.gridNoPaged(dataList, response, null);
		return null;
	}
	public ActionForward getExhProAdvList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExhProSearch search = ExhProUtil.getExhProSearch(request);
		List<ExhProModuleVO> dataList = getService().getExhProAdvList(search);
		ExtJSONUtil.gridNoPaged(dataList, response, null);
		return null;
	}
	/**
	 * 保存展会项目设置模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 曹刚 新增日期：2013-5-14下午05:24:34
	 * @since 1.0
	 */
	public ActionForward saveExhProModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExhProModuleVO mod = ((ExhibitionProjectForm)form).getMod();
		try{
			mod.setOperator(ImtiUtil.getUserName(request));
			mod.setOperateDate(DateUtil.getCurrentDate());
			getService().saveExhProModule(mod);
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	/**
	 * 删除展会项目设置模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 曹刚 新增日期：2013-5-14下午05:24:34
	 * @since 1.0
	 */
	public ActionForward deleteExhProModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try{
			String exhProModuleId = request.getParameter("exhProModuleId");
			if(StringUtil.isNotEmpty(exhProModuleId)){
				getService().deleteExhProModule(exhProModuleId);
				ExtJSONUtil.writeSuccessInfo(true, response, "删除成功！");
			}else {
				ExtJSONUtil.writeSuccessInfo(false, response, "模板标识丢失，删除失败！");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	/**
	 * 提交模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 曹刚 新增日期：2013-5-15上午11:05:44
	 * @since 1.0
	 */
	public ActionForward submitExhProModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try{
			String exhProModuleId = request.getParameter("exhProModuleId");
			if(StringUtil.isNotEmpty(exhProModuleId)){
				getService().submitExhProModule(exhProModuleId);
				ExtJSONUtil.writeSuccessInfo(true, response, "提交成功！");
			}else {
				ExtJSONUtil.writeSuccessInfo(false, response, "模板标识丢失，删除失败！");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
}
