package com.imti.sysmgr.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.imti.common.util.ImtiUtil;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.common.StringUtils;
import com.imti.framework.common.constant.SessionConstant;
import com.imti.framework.common.ext.ExtJSONUtil;
import com.imti.framework.common.ext.ExtJsTreeNode;
import com.imti.framework.web.action.BaseExtAction;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.sysmgr.bean.ImtiMapFactory;
import com.imti.sysmgr.bean.RsTree;
import com.imti.sysmgr.po.SysOperatorPO;
import com.imti.sysmgr.po.SysResourcePO;
import com.imti.sysmgr.service.SysMgrService;
import com.imti.sysmgr.service.SysResourceService;
import com.imti.sysmgr.vo.SysOperatorVO;
import com.imti.sysmgr.vo.SysResourceVO;

public class SysResourceAction extends BaseExtAction {

	public SysResourceService getService() {
		return (SysResourceService)SpringBeanUtil.getBean("sysResourceService");
	}
	public SysMgrService getSysMgrService() {
		return (SysMgrService)SpringBeanUtil.getBean("sysMgrService");
	}
	
	public ActionForward getRsTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//node��Ext���Դ��Ĳ���
		String folderId = request.getParameter("node");
		List rsFolders=new ArrayList();
		if(StringUtils.isNotBlank(folderId)){
			rsFolders= getService().getChildrenByRsId(folderId);
			List<ExtJsTreeNode> treeNodeList = new ArrayList<ExtJsTreeNode>();
			for (int i = 0; i < rsFolders.size(); i++) {
				ExtJsTreeNode node = new ExtJsTreeNode();
				SysResourceVO folder = (SysResourceVO) rsFolders.get(i);
				node.setId(folder.getId());
				node.setText(folder.getName());
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
	public ActionForward getRsOpeTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//node��Ext���Դ��Ĳ���
		String folderId = request.getParameter("node");
		String roleId = request.getParameter("roleId");
		List rsList = new ArrayList();
		List treeNodeList = new ArrayList();
		List rsTreeList = new ArrayList();
		
		if(StringUtils.isNotBlank(folderId)){
			//װ����Դ��ֱ������Դ
			rsList= getService().getChildrenByRsId(folderId);
			for (int i = 0; i < rsList.size(); i++) {
				RsTree rsTree = new RsTree();
				SysResourceVO folder = (SysResourceVO) rsList.get(i);
				rsTree.setNodeId(folder.getId());
				rsTree.setNodeName(folder.getName());
				rsTree.setQtip(folder.getMemo());
				rsTree.setImgUrl(folder.getImgUrl());
				rsTree.setCode(folder.getCode());//��Դ���룺Ψһ��
				rsTree.setType("1");
				rsTreeList.add(rsTree);
			}
			//������Դ����������Դʱ����װ�ڴ���Դ�Ķ����в���
			if(ImtiListUtil.isEmpty(rsList)){
				SysResourceVO folder = getService().getRsByRsId(folderId);
				List opeList = getService().getOperatorByRsId(folderId);
				if(ImtiListUtil.isNotEmpty(opeList)){
					List children = new ArrayList();
					for(int j = 0; j < opeList.size(); j++){
						SysOperatorVO opeVO = (SysOperatorVO)opeList.get(j);
						RsTree rsOpTree = new RsTree();
						rsOpTree.setNodeId(opeVO.getId());
						rsOpTree.setNodeName(opeVO.getName());
						rsOpTree.setQtip(opeVO.getUrl());
						rsOpTree.setChildren(null);
						rsOpTree.setType("2");
						rsOpTree.setCode(folder.getCode() + "_" + opeVO.getCode());//��Դ������ϲ�������
						rsTreeList.add(rsOpTree);
					}
				}
			}
			
		}
		treeNodeList = chageRsTreeBean(rsTreeList, roleId);
		JSONArray json = JSONArray.fromObject(treeNodeList);
		ExtJSONUtil.write(json.toString(),response);
		return null;
	}
	public ActionForward getRsOpeTreeNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//node��Ext���Դ��Ĳ���
		String roleId = request.getParameter("roleId");
		List<JSONObject> treeNodeList = getService().getRsTreeBeanList(roleId);
		JSONArray json = JSONArray.fromObject(treeNodeList);
		ExtJSONUtil.write(json.toString(),response);
		return null;
	}
	private List chageRsTreeBean(List rsTreeList, String roleId){
		List treeNodeList = new ArrayList();
		//��Ȩ����Ϣ�ж�ȡ��ص���Ϣ���ж���Դ������Ƿ�ѡ��
		
		Map rsMap = ImtiMapFactory.getRsRoleMap(roleId);
		if(rsMap == null){
			rsMap = getSysMgrService().getRsOperatorMapByRoleId(roleId);
			ImtiMapFactory.setRsRoleMap(rsMap, roleId);
		}
			
		if(ImtiListUtil.isNotEmpty(rsTreeList)){
			for (int i = 0; i < rsTreeList.size(); i++) {
				RsTree rsOpTree = (RsTree) rsTreeList.get(i);
				JSONObject object = new JSONObject();
				object.put("id", rsOpTree.getNodeId());
				object.put("text", rsOpTree.getNodeName());
				object.put("qtip", rsOpTree.getQtip());
				object.put("checked", false);//Ĭ��û��ѡ��
				
				Object obj = rsMap.get(rsOpTree.getCode());
				if(obj != null){
					object.put("checked", true);
				}
				
				if("2".equals(rsOpTree.getType())){
					object.put("leaf", true);//����Ҷ�ӽڵ�
				}else {
					object.put("leaf", false);
					//��Ӳ���
					if(ImtiListUtil.isNotEmpty(rsOpTree.getChildren())){
						Iterator<RsTree> it = rsOpTree.getChildren().iterator();
						while(it.hasNext()){
							RsTree opeTree = (RsTree)it.next();
							JSONObject opeObject = new JSONObject();
							opeObject.put("id", opeTree.getNodeId());
							opeObject.put("text", opeTree.getNodeName());
							opeObject.put("qtip", opeTree.getQtip());
							opeObject.put("checked", false);//Ĭ��û��ѡ��
							opeObject.put("leaf", true);//Ҷ�ӽڵ�
							Object opeObj = rsMap.get(opeTree.getCode());
							if(opeObj != null){
								opeObject.put("checked", true);
							}
							treeNodeList.add(opeObject);
						}
					}
				}
				treeNodeList.add(object);
			}
		}
		return treeNodeList;
	}
	public ActionForward toRsList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = request.getParameter("pid");
		if(StringUtils.isNotBlank(pid)){
			//���ݸ���ԴID�����Ҹ���Դ�µ�������Դ
			String whereHql = " and po.parentId='" + pid + "' order by po.displayIndex";
			List list = getService().getByConditionWithNoPage(SysResourcePO.class, whereHql);
			ExtJSONUtil.gridNoPaged(list, response, null);
		}
		return null;
	}
	
	public ActionForward getOperatorList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rsId = request.getParameter("rsId");
		if(StringUtils.isNotBlank(rsId)){
			//���ݸ���ԴID�����Ҹ���Դ�µ�������Դ
			String whereHql = " and po.rsId='" + rsId + "' order by po.displayIndex";
			List list = getService().getByConditionWithNoPage(SysOperatorPO.class, whereHql);
			ExtJSONUtil.gridNoPaged(list, response, null);
		}
		return null;
	}
	
	public ActionForward saveRsOpe(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysOperatorVO sysOperator = getSysOperatorVO(request);
		try{
			if(StringUtils.isBlank(sysOperator.getId())){//��������
				getService().insertOperator(sysOperator);
			}else {
				getService().updateOperator(sysOperator);//�޸Ĳ���
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	private SysOperatorVO getSysOperatorVO(HttpServletRequest request) {
		SysOperatorVO operator = new SysOperatorVO();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String rsId = request.getParameter("rsId");
		String displayIndex = request.getParameter("displayIndex");
		String url = request.getParameter("url");
		String memo = request.getParameter("memo");
		String ztId = request.getParameter("ztId");
		operator.setId(id);
		operator.setName(name);
		operator.setCode(code);
		operator.setRsId(rsId);
		operator.setMemo(memo);
		operator.setUrl(url);
		if(StringUtils.isNotEmpty(displayIndex)){
			operator.setDisplayIndex(Integer.parseInt(displayIndex));
		}else {
			operator.setDisplayIndex(0);
		}
		operator.setZtId(StringUtils.isEmpty(ztId)?ImtiUtil.getZtId(request) : ztId);
		return operator;
	}
	public ActionForward saveRsModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysResourceVO sysResource = getSysResourceVO(request);
		try{
			if(StringUtils.isBlank(sysResource.getId())){//����
				getService().insertRsModule(sysResource);
			}else {
				getService().updateRsModule(sysResource);//�޸�
			}
			ExtJSONUtil.writeSuccessInfo(true, response, "����ɹ���");
		}catch (Exception ex){
			log.info(ex.getMessage());
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	
	/**
	 * ɾ����Դ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteRsModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rsIds = request.getParameter("rsIds");
		
		try{
			if(StringUtils.isNotBlank(rsIds)){
				getService().deleteRsByRsId(rsIds);
				ExtJSONUtil.writeSuccessInfo(true, response, "ɾ���ɹ���");
			}
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	/**
	 * ɾ����Դ�µĲ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteOprator(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String opeIds = request.getParameter("opeIds");
		
		try{
			if(StringUtils.isNotBlank(opeIds)){
				getService().deleteOpeByOperatorId(opeIds);
				ExtJSONUtil.writeSuccessInfo(true, response, "ɾ���ɹ���");
			}
		}catch (Exception ex){
			ExtJSONUtil.writeSuccessInfo(false, response, ex.getMessage());
		}
		return null;
	}
	
	/**
	 * �԰�ť������������Ȩ����֤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward permissionVerify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rsCode = request.getParameter("rsCode");
		/*
		 * rsCode:��Ϊ�������֣���������֮��ͨ�������������֮ǰ��ʾ��Դ�ı��루Ψһ�������֮���ʾ�������루�����ظ�����
		 * ���ߵ�����һ���ʾΨһ
		 */
		if(StringUtils.isEmpty(rsCode)){
			ExtJSONUtil.writeInfo(response, "false");
		}else {
			boolean permission = ImtiUtil.permissionVerify(request, rsCode);
			if(permission){
				ExtJSONUtil.writeInfo(response, "true");
			}else {
				ExtJSONUtil.writeInfo(response, "false");
			}
		}
		return null;
	}
	/**
	 * �԰�ť������������Ȩ����֤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPermissionCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> permissionMap = (Map<String, String>) request.getSession().getAttribute(SessionConstant.IMTI_SESSION_USER_PEMISSION);
		Set permissionSet = permissionMap.keySet();
		ExtJSONUtil.writeObject(permissionSet, response);
		return null;
	}
	public SysResourceVO getSysResourceVO(HttpServletRequest request){
		SysResourceVO sysResourceVO = new SysResourceVO();
		String rsId = request.getParameter("id");//��Դ����
		String rsName = request.getParameter("name");//��Դ����
		String rsCode = request.getParameter("code");//��Դ����
		String rsParentId = request.getParameter("parentId");//����ԴID
		int level = 1;
		if(!"root".equals(rsParentId)){
			level = getService().getLevelByRsId(rsParentId) + 1;
		}
		String displayIndex = request.getParameter("displayIndex");
		String imgUrl = request.getParameter("imgUrl");
		String url = request.getParameter("url");
		String memo = request.getParameter("memo");
		String ztId = request.getParameter("ztId");
		sysResourceVO.setId(rsId);
		sysResourceVO.setName(rsName);
		sysResourceVO.setCode(rsCode);
		sysResourceVO.setParentId(rsParentId);
		sysResourceVO.setLevel(level);
		if(StringUtils.isNotBlank(displayIndex)){
			sysResourceVO.setDisplayIndex(Integer.parseInt(displayIndex));
		}
		sysResourceVO.setImgUrl(imgUrl);
		sysResourceVO.setUrl(url);
		sysResourceVO.setValidFlag(SysResourceVO.RS_VALID_FLAG_YES);
		sysResourceVO.setMemo(memo);
		sysResourceVO.setZtId(StringUtils.isEmpty(ztId)?ImtiUtil.getZtId(request) : ztId);
		return sysResourceVO;
	}
	public ActionForward getLevelMenue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		Map permissionMap = ImtiUtil.getPermissionMap(request);
		List list = null;
		if(StringUtils.isNotBlank(parentId) && "root".equals(parentId)){
			list = getService().getMenu(parentId, permissionMap);
		}else if(StringUtils.isNotBlank(parentId)){
			list = getService().getSubMenu(parentId, permissionMap);
		}
		JSONArray json= JSONArray.fromObject( list );
		ExtJSONUtil.writeInfo(response, json.toString());
		return null;
	}
}
