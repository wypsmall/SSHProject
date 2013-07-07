package com.yzsystem.hr.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.imti.framework.common.StringUtils;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.common.ext.ExtJsTreeNode;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.yzsystem.common.constant.StatusConstant;
import com.yzsystem.hr.action.form.HrForm;
import com.yzsystem.hr.common.HrUtil;
import com.yzsystem.hr.common.search.HrSearch;
import com.yzsystem.hr.po.HrEmployeePO;
import com.yzsystem.hr.po.HrOrgPO;
import com.yzsystem.hr.service.HrService;
import com.yzsystem.hr.vo.HrEmployeeVO;
import com.yzsystem.hr.vo.HrOrgVO;

public class HrAction extends BaseExtAction {

	@Override
	public HrService getService() {
		return (HrService)SpringBeanUtil.getBean("hrService");
	}
	@SuppressWarnings("unchecked")
	public ActionForward getOrgTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//node��Ext���Դ��Ĳ���
		String folderId = request.getParameter("node");
		List<HrOrgVO> rsFolders=new ArrayList<HrOrgVO>();
		if(StringUtils.isNotBlank(folderId)){
			String whereHql = " and po.orgParentId='" + folderId + "' and po.delFlag=" + StatusConstant.DELETE_NO;
			rsFolders = getService().getByConditionWithNoPage(HrOrgPO.class, whereHql);
			List<ExtJsTreeNode> treeNodeList = new ArrayList<ExtJsTreeNode>();
			for (int i = 0; i < rsFolders.size(); i++) {
				ExtJsTreeNode node = new ExtJsTreeNode();
				HrOrgVO folder = (HrOrgVO) rsFolders.get(i);
				node.setId(folder.getOrgId());
				node.setText(folder.getOrgName());
				if(folder.hasChildren()){
					node.setLeaf(false);
				}else{
					node.setLeaf(true);
				}
				node.setQtip(folder.getMemo());
				treeNodeList.add(node);
			}
			JSONArray json = JSONArray.fromObject(treeNodeList);
			ExtJSONUtil.write(json.toString(),response);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward getOrgListByParentId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		if (StringUtils.isNotBlank(parentId)) {
			String whereHql = " and po.delFlag=" + StatusConstant.DELETE_NO + " and po.orgParentId='" + parentId + "'   order by po.orgCode ASC";
			List list = getService().getByConditionWithNoPage(HrOrgPO.class, whereHql);
			ExtJSONUtil.gridNoPaged(list, response, null);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward getOrgList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List list = getService().getByConditionWithNoPage(HrOrgPO.class, " and po.delFlag=" + StatusConstant.DELETE_NO + " order by po.orgParentName");
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter(){
		    public boolean apply(Object source, String name, Object value) {
		        if(name.equalsIgnoreCase("orgCode") || name.equalsIgnoreCase("delFlag") || 
		        		name.equalsIgnoreCase("memo") || name.equalsIgnoreCase("address") || 
		        		name.equalsIgnoreCase("tel") || name.equalsIgnoreCase("fax") 
		        		) { //���ô��䵽ǰ̨ҳ�������
		            return true;
		        } else {
		            return false;
		        }
		    }
		});
		ExtJSONUtil.gridNoPaged(list, response, config);
		return null;
	}
	/**
	 * ��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-19����03:39:34
	 * @since 1.0
	 */
	public ActionForward insertHrOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HrOrgVO hrOrg = ((HrForm)form).getOrg();
		try{
			getService().insertHrOrg(hrOrg);
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	/**
	 * �޸Ĳ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2013-4-19����03:39:44
	 * @since 1.0
	 */
	public ActionForward updateHrOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HrOrgVO hrOrg = ((HrForm)form).getOrg();
		try{
			getService().updateHrOrg(hrOrg);
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	/**
	 * ɾ����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHrOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orgId = request.getParameter("orgId");
		try{
			if(StringUtils.isNotBlank(orgId)){
				getService().deleteHrOrgById(orgId);
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "ɾ���ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	
	/**
	 * ��ѯְԱ�б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getEmployeeListByOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HrSearch search = HrUtil.getHrSearch(request);
		String whereHQL = HrUtil.getEmployeeCondition(search);
		List dataList = getService().getByHql(search.getStart(), search.getLimit(), " SELECT po from com.yzsystem.hr.po.HrEmployeePO po left join fetch po.org org where 1=1 " + whereHQL);
		int totalCnt = getService().getCountByCondition(HrEmployeePO.class, whereHQL);
		ExtJSONUtil.gridWithPaged(dataList, totalCnt, response, null);
		return null;
	}
	/**
	 * ����ְԱ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insertHrEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HrEmployeeVO hrEmployee = ((HrForm)form).getEmp();
		try{
			getService().insertHrEmployee(hrEmployee);
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	/**
	 * �޸�ְԱ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHrEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HrEmployeeVO hrEmployee = ((HrForm)form).getEmp();
		try{
			getService().updateHrEmployee(hrEmployee);
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	/**
	 * ɾ��ְԱ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHrEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String employeeId = request.getParameter("employeeId");
		try{
			if(StringUtils.isNotBlank(employeeId)){
				getService().deleteHrEmployee(employeeId);
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "ɾ���ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
	public ActionForward groupEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String employeeId = request.getParameter("employeeId");
		String orgId = request.getParameter("orgId");
		try{
			if(StringUtils.isNotBlank(employeeId) && StringUtils.isNotBlank(orgId)){
				getService().groupEmployee(employeeId, orgId);
				ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
			}else{
				ExtJSONUtil.writeSuccessInfo(false, response, "�ؼ���Ϣ��ʧ��");
			}
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage().replaceAll("java.lang.RuntimeException:", "").
					replaceAll("java.lang.NullPointerException:", ""));
		}
		return null;
	}
}
