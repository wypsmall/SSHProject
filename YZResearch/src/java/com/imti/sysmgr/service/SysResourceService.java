package com.imti.sysmgr.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.imti.framework.web.service.BaseService;
import com.imti.sysmgr.vo.SysOperatorVO;
import com.imti.sysmgr.vo.SysResourceVO;

public interface SysResourceService extends BaseService {

	/**
	 * <li>������Դ��IDɾ������Դ�µĲ�����ͬ��ɾ����ò�����Ӧ��Ȩ��
	 * @param rsIds
	 */
	public void deleteOperatorByRsIds(String[] rsIds);
	/**
	 * <li>������Դ��IDɾ������Դ
	 * <li>ͬʱɾ������Դ�µĲ���
	 * <li>ͬ��ɾ��������Ӧ��Ȩ��
	 * @param rsId
	 */
	public void deleteRsByRsId(String rsId);
	public int getLevelByRsId(String rsParentId);
	public List getChildrenByRsId(String rsId);
	public List getOperatorByRsId(String id);
	
	public List getAllChildrenByRsId(String folderId);
	/**
	 * ������Դ������ȡ��Դ����
	 * @param rsId
	 * @return
	 */
	public SysResourceVO getRsByRsId(String rsId);
	/**
	 * ��ȡ�û���Ӧ�ĺϷ��˵�Ȩ��
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
	//2313-04-15(��ȡ��Դ--������Դ��)
	public List<JSONObject> getRsTreeBeanList(String roleId);
}
