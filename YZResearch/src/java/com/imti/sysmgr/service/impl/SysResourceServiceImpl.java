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
		ImtiAssert.isNotBlank(rsIds, "资源主键不能为空！");
		/*
		 * 1、删除角色权限
		 * 2、删除操作
		 */
		
		//删除角色权限
		String whereHql = " and po.operatorId in (select po1.id from com.imti.sysmgr.po.SysOperatorPO po1 " + 
							" where po1.rsId in" + StringUtil.arrayToSQLString(rsIds, StringUtil.SQL_STRING_BRACKETS) + ")";
		getBaseDao().delete(SysRoleRightPO.class, whereHql);
		
		//删除操作
		String whereHql1 = " and po.rsId in" + StringUtil.arrayToSQLString(rsIds, StringUtil.SQL_STRING_BRACKETS);
		getBaseDao().delete(SysOperatorPO.class, whereHql1);
	}
	
	public void deleteOpeByOperatorId(String opeIds) {
		ImtiAssert.isNotBlank(opeIds, "操作主键不能为空！");
		/*
		 * 1、删除角色权限
		 * 2、删除操作
		 */
		String[] opeId = opeIds.split(",");
		
		//删除角色权限
		String whereHql = " and po.operatorId in" + StringUtil.arrayToSQLString(opeId, StringUtil.SQL_STRING_BRACKETS);
		getBaseDao().delete(SysRoleRightPO.class, whereHql);
		
		//删除操作
		String whereHql1 = " and po.id in" + StringUtil.arrayToSQLString(opeId, StringUtil.SQL_STRING_BRACKETS);
		getBaseDao().delete(SysOperatorPO.class, whereHql1);
	}
	public void deleteRsByRsId(String rsId) {
		/*
		 * 1、删除操作
		 * 2、删除资源
		 */
		ImtiAssert.isNotBlank(rsId, "资源主键不能为空！");
		String[] rsIds = rsId.split(",");
		if(rsIds != null && rsIds.length > 0){
			//删除操作
			deleteOperatorByRsIds(rsIds);
			
			//FOR循环，递归删除子资源
			for(int i = 0; i < rsIds.length; i++){
				String parentHql = " and po.parentId='" + rsIds[i] + "'";
				List list = getByConditionWithNoPage(SysResourcePO.class, parentHql);
				if(ImtiListUtil.isNotEmpty(list)){
					String[] subRsId = ImtiListUtil.getPropertyStringArray(list, "id");
					deleteRsByRsId(StringUtil.arrayToString(subRsId));
				}
			}
			
			//删除资源
			String whereHql = " and po.id in" + StringUtil.arrayToSQLString(rsIds, StringUtil.SQL_STRING_BRACKETS);
			getBaseDao().delete(SysResourcePO.class, whereHql);
		}
	}

	/*
	 * 1、获取资源的层次
	 * 层次在ext做表现时无效，考虑到jsp展示时需要 
	 */
	public int getLevelByRsId(String rsParentId) {
		ImtiAssert.isNotBlank(rsParentId, "获取资源层次时资源主键不能为空！");
		String hql = " and po.id='" + rsParentId + "'";
		List list = getBaseDao().find(SysResourcePO.class, hql);
		if(ImtiListUtil.isNotEmpty(list)){
			return ((SysResourcePO)list.get(0)).getLevel();
		}
		return 0;
	}

	public void updateRsModule(SysResourceVO sysResource) {
		//参数约束判断
		ImtiAssert.isNotBlank(sysResource.getName(), "资源名称不能为空");
		ImtiAssert.isNotBlank(sysResource.getCode(), "资源编码不能为空");
		ImtiAssert.isNotBlank(sysResource.getParentId(), "父资源ID不能为空");
		ImtiAssert.isNotBlank(sysResource.getId(), "资源ID丢失");
		
		if(0 == sysResource.getDisplayIndex()){
			throw new RuntimeException("资源顺序号不能为0");
		}
		
		//1、编码检测
		String codeCheckHql = " and po.id<>'" + sysResource.getId() + "' and po.code='" + sysResource.getCode() + "'";
		List codeList = getBaseDao().find(SysResourcePO.class, codeCheckHql);
		if(ImtiListUtil.isNotEmpty(codeList)){
			throw new RuntimeException("已经存在资源编码相同的资源，请重新输入资源编码！");
		}
		
		//2、父资源的存在性检测
		SysResourcePO parentExist = null;
		String parentIdCheckHql = " and po.parentId='" + sysResource.getParentId() + "'";
		List parentList = getBaseDao().find(SysResourcePO.class, parentIdCheckHql);
		if(ImtiListUtil.isEmpty(parentList)){
			throw new RuntimeException("父资源不存在！");
		}else {
			parentExist = (SysResourcePO)parentList.get(0);
		}
		
		List<SysResourcePO> children = getBaseDao().find(SysResourcePO.class, "  and po.id<>'" + sysResource.getId() + "' and po.parentId='" + sysResource.getParentId() + "'");
		if(ImtiListUtil.isNotEmpty(children)){
			for(SysResourcePO po : children){
				if(po.getDisplayIndex() == sysResource.getDisplayIndex()){
					//序号检查
					throw new RuntimeException("序号【"+sysResource.getDisplayIndex()+"】冲突，请重新选择");
				}else if(sysResource.getName().equals(po.getName())){
					//名称检查
					throw new RuntimeException("在同一父资源下已经存在相同名称的资源，请重新输入资源名称！");
				}
			}
		}
		sysResource.setRsPath(parentExist.getRsPath() + ";" + sysResource.getId());
		super.update(sysResource);
	}

	public void insertRsModule(SysResourceVO sysResource) {
		
		//参数约束判断
		ImtiAssert.isNotBlank(sysResource.getName(), "资源名称不能为空");
		ImtiAssert.isNotBlank(sysResource.getCode(), "资源编码不能为空");
		ImtiAssert.isNotBlank(sysResource.getParentId(), "父资源ID不能为空");
		if(0 == sysResource.getDisplayIndex()){
			throw new RuntimeException("资源顺序号不能为0");
		}
		/*
		 * 进一步约束
		 * 1、资源的编码不允许为空
		 * 2、在统一资源下，子资源的名称不能相同
		 * 3、
		 */
		
		//1、编码检测
		String codeCheckHql = " and po.code='" + sysResource.getCode() + "'";
		List codeList = getBaseDao().find(SysResourcePO.class, codeCheckHql);
		if(ImtiListUtil.isNotEmpty(codeList)){
			throw new RuntimeException("已经存在资源编码相同的资源，请重新输入资源编码！");
		}
		
		//2、父资源的存在性检测
		if(!"root".equals(sysResource.getParentId())){
			String parentIdCheckHql = " and po.id='" + sysResource.getParentId() + "'";
			List<SysResourcePO> parentList = getBaseDao().find(SysResourcePO.class, parentIdCheckHql);
			if(ImtiListUtil.isEmpty(parentList)){
				throw new RuntimeException("父资源不存在！");
			}
		}
		
		List<SysResourcePO> children = getBaseDao().find(SysResourcePO.class, " and po.parentId='" + sysResource.getParentId() + "'");
		if(ImtiListUtil.isNotEmpty(children)){
			for(SysResourcePO po : children){
				if(po.getDisplayIndex() == sysResource.getDisplayIndex()){
					//序号检查
					throw new RuntimeException("序号【"+sysResource.getDisplayIndex()+"】冲突，请重新选择");
				}else if(sysResource.getName().equals(po.getName())){
					//名称检查
					throw new RuntimeException("在同一父资源下已经存在相同名称的资源，请重新输入资源名称！");
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
		po.setValidFlag(SysResourceVO.RS_VALID_FLAG_YES);//资源改成有效
		po.setParentId(sysResource.getParentId());//修改无效资源的父资源
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
		ImtiAssert.isNotBlank(rsId, "资源主键丢失");
		
		String strHQLWhere = " and po.id='" + rsId + "'";
		List list = super.getByConditionWithNoPage(SysResourcePO.class, strHQLWhere);
		if(ImtiListUtil.isNotEmpty(list)){
			return (SysResourceVO)list.get(0);
		}
		return null;
	}

	public List getMenu(String parentId, Map permissionMap) {
		ImtiAssert.isNotBlank(parentId, "获取模块时，父模块不能为空！");
		
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
		ImtiAssert.isNotBlank(parentId, "获取模块时，父模块不能为空！");
		List<MenueBean> menuList = new ArrayList<MenueBean>(); 
		if(permissionMap == null){
			return menuList;
		}
		
		List<RsBean> rsList = getBaseDao().getRsBeanList(parentId);//加载模块权限
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
					//创建新的根节点
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
		ImtiAssert.isNotBlank(sysOperator, "操作对象不能为空！");
		ImtiAssert.isNotBlank(sysOperator.getName(), "操作名称不能为空");
		ImtiAssert.isNotBlank(sysOperator.getCode(), "操作编码不能为空！");
		ImtiAssert.isNotBlank(sysOperator.getRsId(), "操作的所属资源不能为空");
		/*
		 * 1、在相同的资源下，名称不能相同，编码也不能相同
		 */
		String checkHql = " and po.rsId='" + sysOperator.getRsId() + "'";
		List list = getByConditionWithNoPage(SysOperatorPO.class, checkHql);
		if(ImtiListUtil.isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				SysOperatorVO operator = (SysOperatorVO)list.get(i);
				if(sysOperator.getName().equals(operator.getName()) || 
						sysOperator.getCode().equals(operator.getCode())){
					throw new RuntimeException("在该资源下，已经存在相同名称或编号的操作");
				}
			}
		}
		super.insert(sysOperator);
		
	}

	public void updateOperator(SysOperatorVO sysOperator) {
		ImtiAssert.isNotBlank(sysOperator, "操作对象不能为空！");
		ImtiAssert.isNotBlank(sysOperator.getName(), "操作名称不能为空");
		ImtiAssert.isNotBlank(sysOperator.getCode(), "操作编码不能为空！");
		ImtiAssert.isNotBlank(sysOperator.getRsId(), "操作的所属资源不能为空");
		ImtiAssert.isNotBlank(sysOperator.getId(), "操作标识丢失！");
		/*
		 * 1、在相同的资源下，名称不能相同，编码也不能相同
		 */
		String checkHql = " and po.id<>'" + sysOperator.getId() + "' and po.rsId='" + sysOperator.getRsId() + "'";
		List list = getByConditionWithNoPage(SysOperatorPO.class, checkHql);
		if(ImtiListUtil.isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				SysOperatorVO operator = (SysOperatorVO)list.get(i);
				if(sysOperator.getName().equals(operator.getName()) || 
						sysOperator.getCode().equals(operator.getCode())){
					throw new RuntimeException("在该资源下，已经存在相同名称或编号的操作");
				}
			}
		}
		super.update(sysOperator);
	}
	public List<JSONObject> getRsTreeBeanList(String roleId){
		//加载角色权限
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
			object.put("checked", false);//默认没有选中
			object.put("id", rsBean.getRsId()+"_1");//特殊标记来标识资源
			Object obj = rsMap.get(rsBean.getRsCode());
			if(obj != null){
				object.put("checked", true);
			}
			
			if(rsBean.isLeaf()){
				object.put("leaf", true);//操作叶子节点
				object.put("id", rsBean.getRsId()+"_0");//特殊标记来标识操作
			}else {
				object.put("leaf", false);
				//添加子树
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
					//创建新的根节点
					rootIdentify = rsBean.getRootId();
					root = new RsTreeBean(rsBean.getRootId(),rsBean.getRootCode(),rsBean.getRootName(),false);
					rsTreeList.add(root);
					//初始化本行数据
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
					//添加操作
					if(StringUtil.isNotBlank(rsBean.getOperatorId())){
						operator = new RsTreeBean(rsBean.getOperatorId(),rsBean.getOperatorCode(),rsBean.getOperatorName(),true);
						third.addChild(operator);
					}
				}else {
					//一级目录下(二级目录不相同)
					if(StringUtil.isNotBlank(rsBean.getSecondId()) && !secondIdentify.equals(rsBean.getSecondId())){
						secondIdentify = rsBean.getSecondId();
						second = new RsTreeBean(rsBean.getSecondId(),rsBean.getSecondCode(),rsBean.getSecondName(),false);
						root.addChild(second);
						//初始化三级目录
						if(StringUtil.isNotBlank(rsBean.getThirdId())){
							thirdIdentify = rsBean.getThirdId();
							third = new RsTreeBean(rsBean.getThirdId(),rsBean.getThirdCode(),rsBean.getThirdName(),false);
							second.addChild(third);
						}else {
							third = null;
						}
						//初始化操作
						if(StringUtil.isNotBlank(rsBean.getOperatorId())){
							operator = new RsTreeBean(rsBean.getOperatorId(),rsBean.getOperatorCode(),rsBean.getOperatorName(),true);
							third.addChild(operator);
						}
					}else {//一级、二级目录相同
						if(StringUtil.isNotBlank(rsBean.getThirdId()) && !rsBean.getThirdId().equals(thirdIdentify)){
							//三级目录不相同
							thirdIdentify = rsBean.getThirdId();
							third = new RsTreeBean(rsBean.getThirdId(),rsBean.getThirdCode(),rsBean.getThirdName(),false);
							second.addChild(third);
							//初始化操作
							if(StringUtil.isNotBlank(rsBean.getOperatorId())){
								operator = new RsTreeBean(rsBean.getOperatorId(),rsBean.getOperatorCode(),rsBean.getOperatorName(),true);
								third.addChild(operator);
							}
						}else {
							//1、2、3级目录都相同：直接添加操作进去
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
