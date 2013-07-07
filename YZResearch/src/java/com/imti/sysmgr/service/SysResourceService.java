package com.imti.sysmgr.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.imti.framework.web.service.BaseService;
import com.imti.sysmgr.vo.SysOperatorVO;
import com.imti.sysmgr.vo.SysResourceVO;

public interface SysResourceService extends BaseService {

	/**
	 * <li>根据资源的ID删除该资源下的操作，同事删除与该操作对应的权限
	 * @param rsIds
	 */
	public void deleteOperatorByRsIds(String[] rsIds);
	/**
	 * <li>根据资源的ID删除该资源
	 * <li>同时删除该资源下的操作
	 * <li>同事删除操作对应的权限
	 * @param rsId
	 */
	public void deleteRsByRsId(String rsId);
	public int getLevelByRsId(String rsParentId);
	public List getChildrenByRsId(String rsId);
	public List getOperatorByRsId(String id);
	
	public List getAllChildrenByRsId(String folderId);
	/**
	 * 根据资源主键获取资源对象
	 * @param rsId
	 * @return
	 */
	public SysResourceVO getRsByRsId(String rsId);
	/**
	 * 获取用户对应的合法菜单权限
	 * @param parentId
	 * @param permissionMap
	 */
	public List getMenu(String parentId, Map permissionMap);
	public List getSubMenu(String parentId, Map permissionMap);
	public void insertRsModule(SysResourceVO sysResource);
	public void updateRsModule(SysResourceVO sysResource);
	public void insertOperator(SysOperatorVO sysOperator);
	public void updateOperator(SysOperatorVO sysOperator);
	public void deleteOpeByOperatorId(String opeIds);
	//2313-04-15(获取资源--操作资源数)
	public List<JSONObject> getRsTreeBeanList(String roleId);
}
