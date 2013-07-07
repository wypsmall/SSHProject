package com.yzsystem.exhibitions.action;

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
import com.yzsystem.exhibitions.common.ExhProUtil;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.service.ExhProjEnterpriseService;
import com.yzsystem.exhibitions.vo.ExhibitionEnterpriseVO;

/**
 * ��չ��ҵ����������ά������桢���롢չ�ߡ�չ̨����
 * @author �ܸ� �½����ڣ�2013-5-16����11:11:50
 * @version tde 1.0
 */
public class ExhProjEnterpriseAction extends BaseExtAction {

	@Override
	public ExhProjEnterpriseService getService() {
		
		return (ExhProjEnterpriseService)SpringBeanUtil.getBean("exhProjEnterpriseService");
	}

	public ActionForward listEnterprise(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExhProSearch search = ExhProUtil.getExhProSearch(request);
		List<ExhibitionEnterpriseVO> dataList = getService().getExhEnterpriseList(search);
		int totalCnt = getService().countExhEnterprise(search);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
		    public boolean apply(Object source, String name, Object value) {
		        if(name.equalsIgnoreCase("enterpriseId") || name.equalsIgnoreCase("exhProId")
		        		) { //���ô��䵽ǰ̨ҳ�������
		            return true;
		        } else {
		            return false;
		        }
		    }
		});
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
}
