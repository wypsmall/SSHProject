package com.imti.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.imti.framework.common.ImtiAssert;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.common.StringUtil;
import com.imti.framework.common.StringUtils;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.imti.sysmgr.bean.ImtiMapFactory;
import com.imti.sysmgr.dao.SysResourceDao;
import com.imti.sysmgr.ext.MenueBean;
import com.imti.sysmgr.ext.RsBean;
import com.imti.sysmgr.ext.RsTreeBean;
import com.imti.sysmgr.po.SysOperatorPO;
import com.imti.sysmgr.po.SysResourcePO;
import com.imti.sysmgr.po.SysRoleRightPO;
import com.imti.sysmgr.service.SysMgrService;
import com.imti.sysmgr.service.SysResourceService;
import com.imti.sysmgr.vo.SysOperatorVO;
import com.imti.sysmgr.vo.SysResourceVO;

public class SysResourceServiceImpl extends BaseServiceImpl implements
		SysResourceService {

	
	public SysResourceDao getBaseDao() {
		return (SysResourceDao)SpringBeanUtil.getBean("sysResourceDao");
	}
	private SysMgrService getSysMgrService() {
		return (SysMgrService)SpringBeanUtil.getBean("sysMgrService");
	}
	
	public void deleteOperatorByRsIds(String[] rsIds) {
		ImtiAssert.isNotBlank(rsIds, "��Դ��������Ϊ�գ�");
		/*
		 * 1��ɾ����ɫȨ��
		 * 2��ɾ������
		 */
		
		//ɾ����ɫȨ��
		String whereHql = " and po.operatorId in (select po1.id from com.imti.sysmgr.po.SysOperatorPO po1 " + 
							" where po1.rsId in" + StringUtil.arrayToSQLString(rsIds, StringUtil.SQL_STRING_BRACKETS) + ")";
		getBaseDao().delete(SysRoleRightPO.class, whereHql);
		
		//ɾ������
		String whereHql1 = " and po.rsId in" + StringUtil.arrayToSQLString(rsIds, StringUtil.SQL_STRING_BRACKETS);
		getBaseDao().delete(SysOperatorPO.class, whereHql1);
	}
	
	public void deleteOpeByOperatorId(String opeIds) {
		ImtiAssert.isNotBlank(opeIds, "������������Ϊ�գ�");
		/*
		 * 1��ɾ����ɫȨ��
		 * 2��ɾ������
		 */
		String[] opeId = opeIds.split(",");
		
		//ɾ����ɫȨ��
		String whereHql = " and po.operatorId in" + StringUtil.arrayToSQLString(opeId, StringUtil.SQL_STRING_BRACKETS);
		getBaseDao().delete(SysRoleRightPO.class, whereHql);
		
		//ɾ������
		String whereHql1 = " and po.id in" + StringUtil.arrayToSQLString(opeId, StringUtil.SQL_STRING_BRACKETS);
		getBaseDao().delete(SysOperatorPO.class, whereHql1);
	}
	public void deleteRsByRsId(String rsId) {
		/*
		 * 1��ɾ������
		 * 2��ɾ����Դ
		 */
		ImtiAssert.isNotBlank(rsId, "��Դ��������Ϊ�գ�");
		String[] rsIds = rsId.split(",");
		if(rsIds != null && rsIds.length > 0){
			//ɾ������
			deleteOperatorByRsIds(rsIds);
			
			//FORѭ�����ݹ�ɾ������Դ
			for(int i = 0; i < rsIds.length; i++){
				String parentHql = " and po.parentId='" + rsIds[i] + "'";
				List list = getByConditionWithNoPage(SysResourcePO.class, parentHql);
				if(ImtiListUtil.isNotEmpty(list)){
					String[] subRsId = ImtiListUtil.getPropertyStringArray(list, "id");
					deleteRsByRsId(StringUtil.arrayToString(subRsId));
				}
			}
			
			//ɾ����Դ
			String whereHql = " and po.id in" + StringUtil.arrayToSQLString(rsIds, StringUtil.SQL_STRING_BRACKETS);
			getBaseDao().delete(SysResourcePO.class, whereHql);
		}
	}

	/*
	 * 1����ȡ��Դ�Ĳ��
	 * �����ext������ʱ��Ч�����ǵ�jspչʾʱ��Ҫ 
	 */
	public int getLevelByRsId(String rsParentId) {
		ImtiAssert.isNotBlank(rsParentId, "��ȡ��Դ���ʱ��Դ��������Ϊ�գ�");
		String hql = " and po.id='" + rsParentId + "'";
		List list = getBaseDao().find(SysResourcePO.class, hql);
		if(ImtiListUtil.isNotEmpty(list)){
			return ((SysResourcePO)list.get(0)).getLevel();
		}
		return 0;
	}

	public void updateRsModule(SysResourceVO sysResource) {
		//����Լ���ж�
		ImtiAssert.isNotBlank(sysResource.getName(), "��Դ���Ʋ���Ϊ��");
		ImtiAssert.isNotBlank(sysResource.getCode(), "��Դ���벻��Ϊ��");
		ImtiAssert.isNotBlank(sysResource.getParentId(), "����ԴID����Ϊ��");
		ImtiAssert.isNotBlank(sysResource.getId(), "��ԴID��ʧ");
		
		if(0 == sysResource.getDisplayIndex()){
			throw new RuntimeException("��Դ˳��Ų���Ϊ0");
		}
		
		//1��������
		String codeCheckHql = " and po.id<>'" + sysResource.getId() + "' and po.code='" + sysResource.getCode() + "'";
		List codeList = getBaseDao().find(SysResourcePO.class, codeCheckHql);
		if(ImtiListUtil.isNotEmpty(codeList)){
			throw new RuntimeException("�Ѿ�������Դ������ͬ����Դ��������������Դ���룡");
		}
		
		//2������Դ�Ĵ����Լ��
		SysResourcePO parentExist = null;
		String parentIdCheckHql = " and po.parentId='" + sysResource.getParentId() + "'";
		List parentList = getBaseDao().find(SysResourcePO.class, parentIdCheckHql);
		if(ImtiListUtil.isEmpty(parentList)){
			throw new RuntimeException("����Դ�����ڣ�");
		}else {
			parentExist = (SysResourcePO)parentList.get(0);
		}
		
		List<SysResourcePO> children = getBaseDao().find(SysResourcePO.class, "  and po.id<>'" + sysResource.getId() + "' and po.parentId='" + sysResource.getParentId() + "'");
		if(ImtiListUtil.isNotEmpty(children)){
			for(SysResourcePO po : children){
				if(po.getDisplayIndex() == sysResource.getDisplayIndex()){
					//��ż��
					throw new RuntimeException("��š�"+sysResource.getDisplayIndex()+"����ͻ��������ѡ��");
				}else if(sysResource.getName().equals(po.getName())){
					//���Ƽ��
					throw new RuntimeException("��ͬһ����Դ���Ѿ�������ͬ���Ƶ���Դ��������������Դ���ƣ�");
				}
			}
		}
		sysResource.setRsPath(parentExist.getRsPath() + ";" + sysResource.getId());
		super.update(sysResource);
	}

	public void insertRsModule(SysResourceVO sysResource) {
		
		//����Լ���ж�
		ImtiAssert.isNotBlank(sysResource.getName(), "��Դ���Ʋ���Ϊ��");
		ImtiAssert.isNotBlank(sysResource.getCode(), "��Դ���벻��Ϊ��");
		ImtiAssert.isNotBlank(sysResource.getParentId(), "����ԴID����Ϊ��");
		if(0 == sysResource.getDisplayIndex()){
			throw new RuntimeException("��Դ˳��Ų���Ϊ0");
		}
		/*
		 * ��һ��Լ��
		 * 1����Դ�ı��벻����Ϊ��
		 * 2����ͳһ��Դ�£�����Դ�����Ʋ�����ͬ
		 * 3��
		 */
		
		//1��������
		String codeCheckHql = " and po.code='" + sysResource.getCode() + "'";
		List codeList = getBaseDao().find(SysResourcePO.class, codeCheckHql);
		if(ImtiListUtil.isNotEmpty(codeList)){
			throw new RuntimeException("�Ѿ�������Դ������ͬ����Դ��������������Դ���룡");
		}
		
		//2������Դ�Ĵ����Լ��
		if(!"root".equals(sysResource.getParentId())){
			String parentIdCheckHql = " and po.id='" + sysResource.getParentId() + "'";
			List<SysResourcePO> parentList = getBaseDao().find(SysResourcePO.class, parentIdCheckHql);
			if(ImtiListUtil.isEmpty(parentList)){
				throw new RuntimeException("����Դ�����ڣ�");
			}
		}
		
		List<SysResourcePO> children = getBaseDao().find(SysResourcePO.class, " and po.parentId='" + sysResource.getParentId() + "'");
		if(ImtiListUtil.isNotEmpty(children)){
			for(SysResourcePO po : children){
				if(po.getDisplayIndex() == sysResource.getDisplayIndex()){
					//��ż��
					throw new RuntimeException("��š�"+sysResource.getDisplayIndex()+"����ͻ��������ѡ��");
				}else if(sysResource.getName().equals(po.getName())){
					//���Ƽ��
					throw new RuntimeException("��ͬһ����Դ���Ѿ�������ͬ���Ƶ���Դ��������������Դ���ƣ�");
				}
			}
		}
		super.insert(sysResource);
		updateRsModule(sysResource);
	}

	private void copyRsVOToPO(SysResourceVO sysResource, SysResourcePO po){
		po.setCode(sysResource.getCode());
		po.setDisplayIndex(sysResource.getDisplayIndex());
		po.setImgUrl(sysResource.getImgUrl());
		po.setLevel(sysResource.getLevel());
		po.setMemo(sysResource.getMemo());
		po.setName(sysResource.getName());
		po.setUrl(sysResource.getUrl());
		po.setValidFlag(SysResourceVO.RS_VALID_FLAG_YES);//��Դ�ĳ���Ч
		po.setParentId(sysResource.getParentId());//�޸���Ч��Դ�ĸ���Դ
	}

	public List getChildrenByRsId(String rsId) {
		if(StringUtils.isNotBlank(rsId)){
			String whereHql = " and po.validFlag='1' and po.parentId='" + rsId + "' order by po.displayIndex";
			return getByConditionWithNoPage(SysResourcePO.class, whereHql);
		}
		return new ArrayList();
	}

	public List getOperatorByRsId(String rsId) {
		String strHQLWhere = " and po.rsId='" + rsId + "'";
		return super.getByConditionWithNoPage(SysOperatorPO.class, strHQLWhere);
	}

	public List getAllChildrenByRsId(String folderId) {
		String strHQLWhere = " and po.rsPath like '%"+folderId+"%'";
		return super.getByConditionWithNoPage(SysResourcePO.class, strHQLWhere);
	}

	public SysResourceVO getRsByRsId(String rsId) {
		ImtiAssert.isNotBlank(rsId, "��Դ������ʧ");
		
		String strHQLWhere = " and po.id='" + rsId + "'";
		List list = super.getByConditionWithNoPage(SysResourcePO.class, strHQLWhere);
		if(ImtiListUtil.isNotEmpty(list)){
			return (SysResourceVO)list.get(0);
		}
		return null;
	}

	public List getMenu(String parentId, Map permissionMap) {
		ImtiAssert.isNotBlank(parentId, "��ȡģ��ʱ����ģ�鲻��Ϊ�գ�");
		
		List menuList = new ArrayList(); 
		if(permissionMap == null){
			return menuList;
		}
		
		String whereHql = " and po.validFlag='1' and po.parentId='" + parentId + "' order by po.displayIndex";
		List list = getByConditionWithNoPage(SysResourcePO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				SysResourceVO resource = (SysResourceVO)list.get(i);
				Object object = permissionMap.get(resource.getCode());
				if(object != null && "1".equals(object.toString())){
					menuList.add(resource);
				}
			}
		}
		return menuList;
	}
	public List getSubMenu(String parentId, Map permissionMap){
		ImtiAssert.isNotBlank(parentId, "��ȡģ��ʱ����ģ�鲻��Ϊ�գ�");
		List<MenueBean> menuList = new ArrayList<MenueBean>(); 
		if(permissionMap == null){
			return menuList;
		}
		
		List<RsBean> rsList = getBaseDao().getRsBeanList(parentId);//����ģ��Ȩ��
		changeToMenueBeanTree(rsList, menuList);
		return menuList;
	}
	private void changeToMenueBeanTree(List<RsBean> rsList, List<MenueBean> rsTreeList) {
		String rootIdentify = "";
		MenueBean root = null;
		for(RsBean rsBean : rsList){
			if(StringUtil.isEmpty(rootIdentify)){
				rootIdentify = rsBean.getRootId();
				root = new MenueBean(rsBean.getRootId(),rsBean.getRootName(),rsBean.getRootImgUrl(),rsBean.getRootRsUrl());
				rsTreeList.add(root);
				if(StringUtil.isNotBlank(rsBean.getSecondId())){
					root.addChild(new MenueBean(rsBean.getSecondId(),rsBean.getSecondName(),rsBean.getSecondImgUrl(),rsBean.getSecondRsUrl()));
				}
			}else {
				if(StringUtil.isNotBlank(rsBean.getRootId()) && !rsBean.getRootId().equals(rootIdentify)){
					//�����µĸ��ڵ�
					rootIdentify = rsBean.getRootId();
					root = new MenueBean(rsBean.getRootId(),rsBean.getRootName(),rsBean.getRootImgUrl(),rsBean.getRootRsUrl());
					rsTreeList.add(root);
					if(StringUtil.isNotBlank(rsBean.getSecondId())){
						root.addChild(new MenueBean(rsBean.getSecondId(),rsBean.getSecondName(),rsBean.getSecondImgUrl(),rsBean.getSecondRsUrl()));
					}
				}else {
					if(StringUtil.isNotBlank(rsBean.getSecondId())){
						root.addChild(new MenueBean(rsBean.getSecondId(),rsBean.getSecondName(),rsBean.getSecondImgUrl(),rsBean.getSecondRsUrl()));
					}
				}
			}
		}
	}
	public void insertOperator(SysOperatorVO sysOperator) {
		ImtiAssert.isNotBlank(sysOperator, "����������Ϊ�գ�");
		ImtiAssert.isNotBlank(sysOperator.getName(), "�������Ʋ���Ϊ��");
		ImtiAssert.isNotBlank(sysOperator.getCode(), "�������벻��Ϊ�գ�");
		ImtiAssert.isNotBlank(sysOperator.getRsId(), "������������Դ����Ϊ��");
		/*
		 * 1������ͬ����Դ�£����Ʋ�����ͬ������Ҳ������ͬ
		 */
		String checkHql = " and po.rsId='" + sysOperator.getRsId() + "'";
		List list = getByConditionWithNoPage(SysOperatorPO.class, checkHql);
		if(ImtiListUtil.isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				SysOperatorVO operator = (SysOperatorVO)list.get(i);
				if(sysOperator.getName().equals(operator.getName()) || 
						sysOperator.getCode().equals(operator.getCode())){
					throw new RuntimeException("�ڸ���Դ�£��Ѿ�������ͬ���ƻ��ŵĲ���");
				}
			}
		}
		super.insert(sysOperator);
		
	}

	public void updateOperator(SysOperatorVO sysOperator) {
		ImtiAssert.isNotBlank(sysOperator, "����������Ϊ�գ�");
		ImtiAssert.isNotBlank(sysOperator.getName(), "�������Ʋ���Ϊ��");
		ImtiAssert.isNotBlank(sysOperator.getCode(), "�������벻��Ϊ�գ�");
		ImtiAssert.isNotBlank(sysOperator.getRsId(), "������������Դ����Ϊ��");
		ImtiAssert.isNotBlank(sysOperator.getId(), "������ʶ��ʧ��");
		/*
		 * 1������ͬ����Դ�£����Ʋ�����ͬ������Ҳ������ͬ
		 */
		String checkHql = " and po.id<>'" + sysOperator.getId() + "' and po.rsId='" + sysOperator.getRsId() + "'";
		List list = getByConditionWithNoPage(SysOperatorPO.class, checkHql);
		if(ImtiListUtil.isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				SysOperatorVO operator = (SysOperatorVO)list.get(i);
				if(sysOperator.getName().equals(operator.getName()) || 
						sysOperator.getCode().equals(operator.getCode())){
					throw new RuntimeException("�ڸ���Դ�£��Ѿ�������ͬ���ƻ��ŵĲ���");
				}
			}
		}
		super.update(sysOperator);
	}
	public List<JSONObject> getRsTreeBeanList(String roleId){
		//���ؽ�ɫȨ��
		Map<String,String> rsMap = ImtiMapFactory.getRsRoleMap(roleId);
		if(rsMap == null){
			rsMap = getSysMgrService().getRsOperatorMapByRoleId(roleId);
			ImtiMapFactory.setRsRoleMap(rsMap, roleId);
		}
		List<RsBean> rsList = getBaseDao().getRsBeanList("root");
		List<RsTreeBean> rsTreeList = new ArrayList<RsTreeBean>();
		List<JSONObject> rsTreeNodeList = new ArrayList<JSONObject>();
		changeToRsTree(rsList, rsTreeList);
		changeToTree(rsTreeList, rsTreeNodeList, rsMap);
		return rsTreeNodeList;
	}

	private void changeToTree(List<RsTreeBean> rsList, List<JSONObject> treeNodeList, Map<String,String> rsMap) {
		
		for (RsTreeBean rsBean : rsList) {
			JSONObject object = new JSONObject();
			object.put("text", rsBean.getRsName());
			object.put("qtip", rsBean.getRsName());
			object.put("checked", false);//Ĭ��û��ѡ��
			object.put("id", rsBean.getRsId()+"_1");//����������ʶ��Դ
			Object obj = rsMap.get(rsBean.getRsCode());
			if(obj != null){
				object.put("checked", true);
			}
			
			if(rsBean.isLeaf()){
				object.put("leaf", true);//����Ҷ�ӽڵ�
				object.put("id", rsBean.getRsId()+"_0");//����������ʶ����
			}else {
				object.put("leaf", false);
				//�������
				if(ImtiListUtil.isNotEmpty(rsBean.getChildren())){
					List<JSONObject> children = new ArrayList<JSONObject>();
					changeToTree(rsBean.getChildren(), children, rsMap);
					object.put("children", children);
				}else {
					object.put("leaf", true);
				}
			}
			treeNodeList.add(object);
		}
	}

	private void changeToRsTree(List<RsBean> rsList, List<RsTreeBean> rsTreeList) {
		String rootIdentify = "";
		String secondIdentify = "";
		String thirdIdentify = "";
		RsTreeBean root = null;
		RsTreeBean second = null;
		RsTreeBean third = null;
		RsTreeBean operator = null;
		for(RsBean rsBean : rsList){
			if(StringUtil.isEmpty(rootIdentify)){
				rootIdentify = rsBean.getRootId();
				secondIdentify = rsBean.getSecondId();
				thirdIdentify = rsBean.getThirdId();
				root = new RsTreeBean(rsBean.getRootId(),rsBean.getRootCode(),rsBean.getRootName(),false);
				rsTreeList.add(root);
				if(StringUtil.isNotBlank(secondIdentify)){
					second = new RsTreeBean(rsBean.getSecondId(),rsBean.getSecondCode(),rsBean.getSecondName(),false);
					root.addChild(second);
				}
				if(StringUtil.isNotBlank(thirdIdentify)){
					third = new RsTreeBean(rsBean.getThirdId(),rsBean.getThirdCode(),rsBean.getThirdName(),false);
					second.addChild(third);
				}
				if(StringUtil.isNotBlank(rsBean.getOperatorId())){
					operator = new RsTreeBean(rsBean.getOperatorId(),rsBean.getOperatorCode(),rsBean.getOperatorName(),true);
					third.addChild(operator);
				}
			}else {
				if(StringUtil.isNotBlank(rsBean.getRootId()) && !rsBean.getRootId().equals(rootIdentify)){
					//�����µĸ��ڵ�
					rootIdentify = rsBean.getRootId();
					root = new RsTreeBean(rsBean.getRootId(),rsBean.getRootCode(),rsBean.getRootName(),false);
					rsTreeList.add(root);
					//��ʼ����������
					if(StringUtil.isNotBlank(rsBean.getSecondId())){
						secondIdentify = rsBean.getSecondId();
						second = new RsTreeBean(rsBean.getSecondId(),rsBean.getSecondCode(),rsBean.getSecondName(),false);
						root.addChild(second);
					}else {
						second = null;
					}
					if(StringUtil.isNotBlank(rsBean.getThirdId())){
						thirdIdentify = rsBean.getThirdId();
						third = new RsTreeBean(rsBean.getThirdId(),rsBean.getThirdCode(),rsBean.getThirdName(),false);
						second.addChild(third);
					}else {
						third = null;
					}
					//��Ӳ���
					if(StringUtil.isNotBlank(rsBean.getOperatorId())){
						operator = new RsTreeBean(rsBean.getOperatorId(),rsBean.getOperatorCode(),rsBean.getOperatorName(),true);
						third.addChild(operator);
					}
				}else {
					//һ��Ŀ¼��(����Ŀ¼����ͬ)
					if(StringUtil.isNotBlank(rsBean.getSecondId()) && !secondIdentify.equals(rsBean.getSecondId())){
						secondIdentify = rsBean.getSecondId();
						second = new RsTreeBean(rsBean.getSecondId(),rsBean.getSecondCode(),rsBean.getSecondName(),false);
						root.addChild(second);
						//��ʼ������Ŀ¼
						if(StringUtil.isNotBlank(rsBean.getThirdId())){
							thirdIdentify = rsBean.getThirdId();
							third = new RsTreeBean(rsBean.getThirdId(),rsBean.getThirdCode(),rsBean.getThirdName(),false);
							second.addChild(third);
						}else {
							third = null;
						}
						//��ʼ������
						if(StringUtil.isNotBlank(rsBean.getOperatorId())){
							operator = new RsTreeBean(rsBean.getOperatorId(),rsBean.getOperatorCode(),rsBean.getOperatorName(),true);
							third.addChild(operator);
						}
					}else {//һ��������Ŀ¼��ͬ
						if(StringUtil.isNotBlank(rsBean.getThirdId()) && !rsBean.getThirdId().equals(thirdIdentify)){
							//����Ŀ¼����ͬ
							thirdIdentify = rsBean.getThirdId();
							third = new RsTreeBean(rsBean.getThirdId(),rsBean.getThirdCode(),rsBean.getThirdName(),false);
							second.addChild(third);
							//��ʼ������
							if(StringUtil.isNotBlank(rsBean.getOperatorId())){
								operator = new RsTreeBean(rsBean.getOperatorId(),rsBean.getOperatorCode(),rsBean.getOperatorName(),true);
								third.addChild(operator);
							}
						}else {
							//1��2��3��Ŀ¼����ͬ��ֱ����Ӳ�����ȥ
							if(StringUtil.isNotBlank(rsBean.getOperatorId())){
								operator = new RsTreeBean(rsBean.getOperatorId(),rsBean.getOperatorCode(),rsBean.getOperatorName(),true);
								third.addChild(operator);
							}else {
								operator = null;
							}
						}
					}
					
				}
			}
		}
	}
}
