package com.imti.sysmgr.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.imti.common.util.ImtiUtil;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.common.StringUtils;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.common.ext.ExtJsTreeNode;
import com.imti.framework.common.ext.ExtJsTreeNodeWithCheckbox;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.sysmgr.po.OrgPO;
import com.imti.sysmgr.service.LoginLogService;
import com.imti.sysmgr.service.SysMgrService;
import com.imti.sysmgr.vo.OrgVO;
import com.imti.sysmgr.vo.SysRoleVO;
import com.imti.sysmgr.vo.UserVO;

public class SysMgrAction extends BaseExtAction {
	
	public SysMgrService getService() {
		return (SysMgrService)SpringBeanUtil.getBean("sysMgrService");
	}
	public LoginLogService getLoginLogService(){
		return (LoginLogService)SpringBeanUtil.getBean("loginLogService");
	}
	
	/**
	 * ��ȡ�ֹ�˾��Ϣ(һ����λ)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCompany(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		String whereHql = " and po.orgParentId='root'";
		
		List list = getService().getCompany(startStr, limitStr);
		int totalCnt = getService().getCountByCondition(OrgPO.class, whereHql);
		ExtJSONUtil.gridWithPaged(list, totalCnt, response, null);
		return null;
	}
	/**
	 * ��ȡ�����µ�ֱ���ӻ����б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOrgListByParentId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String parentId = request.getParameter("parentId");
		if (StringUtils.isNotBlank(parentId)) {
			List list = getService().getOrgChildrenById(parentId);
			ExtJSONUtil.gridNoPaged(list, response, null);
		} 
		return null;
	}
	/**
	 * ��ȡ���еĽ�ɫ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRoleList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String roleName = request.getParameter("roleName");
		List list = getService().getRoleList(roleName);
		ExtJSONUtil.gridNoPaged(list, response, null);
		return null;
	}
	/**
	 * �����ɫ��Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysRoleVO roleVO = getSysRoleVO(request);
		String rightRsIds = request.getParameter("rightRsIds");
		try{
			if(StringUtils.isBlank(roleVO.getId())){//����
				getService().insertRole(roleVO, rightRsIds);
			}else {
				getService().updateRole(roleVO, rightRsIds);//�޸�
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	/**
	 * ɾ����ɫ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String roleIds = request.getParameter("roleIds");
		try{
			if(StringUtils.isNotBlank(roleIds)){
				getService().deleteRoleById(roleIds);
				ExtJSONUtil.writeSuccessInfo(true, response, "ɾ���ɹ���");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	/**
	 * �����û���Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserVO user = getUserVO(request);
		try{
			String roleId = request.getParameter("roleId");//��ɫ��
			String orgIds = request.getParameter("orgIds");//��֯������
			if(StringUtils.isBlank(user.getId())){//����
				getService().insertUser(user, roleId,orgIds);
			}else {
				getService().updateUser(user, roleId,orgIds);//�޸�
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	public ActionForward deleteUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("userIds");
		try{
			if(StringUtils.isNotBlank(userIds)){
				getService().deleteUserById(userIds);
				ExtJSONUtil.writeSuccessInfo(true, response, "ɾ���ɹ���");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	public ActionForward activationUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("userIds");
		try{
			if(StringUtils.isNotBlank(userIds)){
				getService().activationUser(userIds);
				ExtJSONUtil.writeSuccessInfo(true, response, "�����˺ųɹ���");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	public ActionForward suspensionUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("userIds");
		try{
			if(StringUtils.isNotBlank(userIds)){
				getService().suspensionUser(userIds);
				ExtJSONUtil.writeSuccessInfo(true, response, "��ͣ�˺ųɹ���");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	/**
	 * ��ȡ���н�ɫ������״�ṩѡ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRoleListTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1����ȡ���еĽ�ɫ
		 * 2����ȡ�û��Ѿ����õĽ�ɫ�������Map(��ɫId���û�Id)
		 * 3������1�еĽ�ɫ�飬ת����ext��״
		 */
		String userId = request.getParameter("userId");
		
		List<ExtJsTreeNodeWithCheckbox> treeNodeList = new ArrayList<ExtJsTreeNodeWithCheckbox>();
		Map roleMap = getService().getRoleMapByUserId(userId);
		List list = getService().getRoleList("");
		
		if(ImtiListUtil.isNotEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				ExtJsTreeNodeWithCheckbox node = new ExtJsTreeNodeWithCheckbox();
				SysRoleVO role = (SysRoleVO) list.get(i);
				node.setId(role.getId());
				node.setText(role.getRoleName());
				node.setLeaf(true);
				node.setQtip(role.getRoleMemo());
				node.setChecked(false);//Ĭ��Ϊδѡ��
				Object object = roleMap.get(role.getId());
				if(object != null){
					node.setChecked(true);
				}
				treeNodeList.add(node);
			}
		}
		JSONArray json = JSONArray.fromObject(treeNodeList);
		ExtJSONUtil.write(json.toString(),response);
		return null;
	}
	public ActionForward getOrgListTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1����ȡ���еĽ�ɫ
		 * 2����ȡ�û��Ѿ����õĽ�ɫ�������Map(��ɫId���û�Id)
		 * 3������1�еĽ�ɫ�飬ת����ext��״
		 */
		String userId = request.getParameter("userId");
		String orgId = request.getParameter("node");
		List<ExtJsTreeNodeWithCheckbox> treeNodeList = new ArrayList<ExtJsTreeNodeWithCheckbox>();
		Map orgMap = getService().getOrgMapByUserId(userId);
		List list = getService().getOrgChildrenById(orgId);
		if(ImtiListUtil.isNotEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				ExtJsTreeNodeWithCheckbox node = new ExtJsTreeNodeWithCheckbox();
				OrgVO org = (OrgVO) list.get(i);
				node.setId(org.getOrgId());
				node.setText(org.getOrgName());
				node.setLeaf(true);
				if("root".equals(org.getOrgParentId())){
					node.setLeaf(false);
				}
				node.setQtip(org.getMemo());
				node.setChecked(false);//Ĭ��Ϊδѡ��
				Object object = orgMap.get(org.getOrgId());
				if(object != null || (org!=null&&orgId!=null&&orgId.equals(org.getOrgId()))){
					node.setChecked(true);
				}
				treeNodeList.add(node);
			}
		}
		JSONArray json = JSONArray.fromObject(treeNodeList);
		ExtJSONUtil.write(json.toString(),response);
		return null;
	}
	
	/**
	 * ��ȡ�����µ��û��б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getUserListByOrgId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String orgId = request.getParameter("orgId");
		String userName = request.getParameter("userName");
		String loginId = request.getParameter("loginId");
		List list = getService().getUserListByOrgId(orgId,loginId, userName);
		ExtJSONUtil.gridNoPaged(list, response, null);
		return null;
	}
	
	
	/**
	 * ��ȡ��֯������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOrgTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//node��Ext���Դ��Ĳ���
		String folderId = request.getParameter("node");
		List rsFolders=new ArrayList();
		if(StringUtils.isNotBlank(folderId)){
			rsFolders= getService().getOrgChildrenById(folderId);
			List<ExtJsTreeNode> treeNodeList = new ArrayList<ExtJsTreeNode>();
			for (int i = 0; i < rsFolders.size(); i++) {
				ExtJsTreeNode node = new ExtJsTreeNode();
				OrgVO folder = (OrgVO) rsFolders.get(i);
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
	/**
	 * ������֯������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrgVO orgVO = getSysOrgVO(request);
		try{
			if(StringUtils.isBlank(orgVO.getOrgId())){//����
				getService().insertOrg(orgVO);
			}else {
				getService().updateOrg(orgVO);//�޸�
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	
	public ActionForward deleteOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String orgIds = request.getParameter("orgIds");
		
		try{
			if(StringUtils.isNotBlank(orgIds)){
				getService().deleteOrgByOrgId(orgIds);
				ExtJSONUtil.writeSuccessInfo(true, response, "ɾ���ɹ���");
			}
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	
	public OrgVO getSysOrgVO(HttpServletRequest request){
		OrgVO orgVO = new OrgVO();
		String orgId = request.getParameter("orgId");//��������
		String orgName = request.getParameter("orgName");//��������
		String orgCode = request.getParameter("orgCode");//��������
		String orgParentId = request.getParameter("orgParentId");//������ID
		String memo = request.getParameter("memo");//����
		String type = request.getParameter("type");
		String ztId = request.getParameter("ztId");
		String ownerCompany = request.getParameter("ownerCompany");
		String displayIndex = request.getParameter("displayIndex");
		orgVO.setMemo(memo);
		orgVO.setOrgCode(orgCode);
		orgVO.setOrgId(orgId);
		orgVO.setOrgName(orgName);
		orgVO.setOrgParentId(orgParentId);
		orgVO.setType(type);
		orgVO.setOwnerCompany(ownerCompany);
		orgVO.setDisplayIndex(Integer.parseInt(StringUtils.isEmpty(displayIndex)?"1":displayIndex));
		orgVO.setZtId(StringUtils.isEmpty(ztId)?ImtiUtil.getZtId(request) : ztId);
		return orgVO;
	}	
	public SysRoleVO getSysRoleVO(HttpServletRequest request){
		SysRoleVO roleVO = new SysRoleVO();
		String roleId = request.getParameter("id");//��������
		String roleName = request.getParameter("roleName");//��������
		String roleCode = request.getParameter("roleCode");//��������
		String roleMemo = request.getParameter("roleMemo");//����
		String ztId = request.getParameter("ztId");
		roleVO.setId(roleId);
		roleVO.setRoleCode(roleCode);
		roleVO.setRoleName(roleName);
		roleVO.setRoleMemo(roleMemo);
		roleVO.setZtId(StringUtils.isEmpty(ztId)?ImtiUtil.getZtId(request) : ztId);
		return roleVO;
	}
	public UserVO getUserVO(HttpServletRequest request){
		UserVO user = new UserVO();
		
		String id = request.getParameter("id");//��������
		String loginId = request.getParameter("loginId");//�˺�
		String password = request.getParameter("password");//����
		String userNick = request.getParameter("userNick");//�ǳ�
		String userName = request.getParameter("userName");//����
		String orgId = request.getParameter("orgId");//���ڻ���
		String loginType = request.getParameter("loginType");//��¼����
		String companyCode = request.getParameter("companyCode");//�ֹ�˾
		String finCode = request.getParameter("finCode");//�������
		String ztId = request.getParameter("ztId");
		user.setCompanyCode(companyCode);
		user.setId(id);
		user.setLoginId(loginId);
		user.setLoginType(loginType);
		user.setOrgId(orgId);
		user.setPassword(password);
		user.setUserName(userName);
		user.setUserNick(userNick);
		user.setValid("1");
		user.setFinCode(finCode);
		user.setZtId(StringUtils.isEmpty(ztId)?ImtiUtil.getZtId(request) : ztId);
		return user;
	}
}
