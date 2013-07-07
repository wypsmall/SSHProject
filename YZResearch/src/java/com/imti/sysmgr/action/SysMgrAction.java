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
	 * 获取分公司信息(一级单位)
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
	 * 获取机构下的直接子机构列表
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
	 * 获取所有的角色
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
	 * 保存角色信息
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
			if(StringUtils.isBlank(roleVO.getId())){//新增
				getService().insertRole(roleVO, rightRsIds);
			}else {
				getService().updateRole(roleVO, rightRsIds);//修改
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	/**
	 * 删除角色
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
				ExtJSONUtil.writeSuccessInfo(true, response, "删除成功！");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	/**
	 * 保存用户信息
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
			String roleId = request.getParameter("roleId");//角色组
			String orgIds = request.getParameter("orgIds");//组织机构组
			if(StringUtils.isBlank(user.getId())){//新增
				getService().insertUser(user, roleId,orgIds);
			}else {
				getService().updateUser(user, roleId,orgIds);//修改
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
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
				ExtJSONUtil.writeSuccessInfo(true, response, "删除成功！");
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
				ExtJSONUtil.writeSuccessInfo(true, response, "激活账号成功！");
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
				ExtJSONUtil.writeSuccessInfo(true, response, "暂停账号成功！");
			}
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	/**
	 * 获取所有角色，以树状提供选择
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
		 * 1、获取所有的角色
		 * 2、获取用户已经设置的角色，存放于Map(角色Id、用户Id)
		 * 3、遍历1中的角色组，转换成ext树状
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
				node.setChecked(false);//默认为未选中
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
		 * 1、获取所有的角色
		 * 2、获取用户已经设置的角色，存放于Map(角色Id、用户Id)
		 * 3、遍历1中的角色组，转换成ext树状
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
				node.setChecked(false);//默认为未选中
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
	 * 获取机构下的用户列表
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
	 * 获取组织机构树
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOrgTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//node是Ext树自带的参数
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
	 * 保存组织机构信息
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
			if(StringUtils.isBlank(orgVO.getOrgId())){//新增
				getService().insertOrg(orgVO);
			}else {
				getService().updateOrg(orgVO);//修改
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "保存成功！");
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
				ExtJSONUtil.writeSuccessInfo(true, response, "删除成功！");
			}
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	
	public OrgVO getSysOrgVO(HttpServletRequest request){
		OrgVO orgVO = new OrgVO();
		String orgId = request.getParameter("orgId");//机构主键
		String orgName = request.getParameter("orgName");//机构名称
		String orgCode = request.getParameter("orgCode");//机构编码
		String orgParentId = request.getParameter("orgParentId");//父机构ID
		String memo = request.getParameter("memo");//描述
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
		String roleId = request.getParameter("id");//机构主键
		String roleName = request.getParameter("roleName");//机构名称
		String roleCode = request.getParameter("roleCode");//机构编码
		String roleMemo = request.getParameter("roleMemo");//描述
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
		
		String id = request.getParameter("id");//机构主键
		String loginId = request.getParameter("loginId");//账号
		String password = request.getParameter("password");//密码
		String userNick = request.getParameter("userNick");//昵称
		String userName = request.getParameter("userName");//姓名
		String orgId = request.getParameter("orgId");//所在机构
		String loginType = request.getParameter("loginType");//登录类型
		String companyCode = request.getParameter("companyCode");//分公司
		String finCode = request.getParameter("finCode");//财务编码
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
