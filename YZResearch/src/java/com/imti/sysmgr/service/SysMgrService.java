package com.imti.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.imti.framework.web.service.BaseService;
import com.imti.sysmgr.vo.OrgVO;
import com.imti.sysmgr.vo.SysRoleVO;
import com.imti.sysmgr.vo.UserVO;

public interface SysMgrService extends BaseService {

	public UserVO getUserByLoginId(String loginId);
	public UserVO getUserByLoginId(String loginId,String ztId);
	public Map<String, String> getRsOperatorMapByLoginId(String loginId);
	public Map<String, String> getRsOperatorMapById(String userId);
	public Map<String, String> getRsOperatorMapByRoleId(String roleId);
	public List getOrgChildrenById(String parentId);
	public void insertOrg(OrgVO orgVO);
	public void updateOrg(OrgVO orgVO);
	/**
	 * ��ȡ��֯�����µ������û����������������õ��û���
	 * @param orgId
	 * @param loginId
	 * @return
	 */
	public List getUserListByOrgId(String orgId,String loginId, String userName);
	/**
	 * ��ȡ��ɫ�б�
	 * @return
	 */
	public List getRoleList(String roleName);
	/**
	 * ��ȡ�û����ڵĽ�ɫ
	 * @param userId
	 * @return
	 */
	public Map getRoleMapByUserId(String userId);
	/**
	 * ��ȡ�û����ڵ���֯����
	 * @param userId
	 * @return
	 */
	public Map getOrgMapByUserId(String userId);
	
	public void insertRole(SysRoleVO roleVO, String rightRsIds);
	public void updateRole(SysRoleVO roleVO, String rightRsIds);
	public void deleteRoleById(String roleIds);
	public void resetPassWord(UserVO user, String oldPassword, String newPassword, String loginPassword);
	public void deleteOrgByOrgId(String orgIds);
	public void insertUser(UserVO user, String roleId,String orgIds);
	public void updateUser(UserVO user, String roleId,String orgIds);
	public void deleteUserById(String userIds);
	public void activationUser(String userIds);
	public void suspensionUser(String userIds);
	public List getCompany(String startStr, String limitStr);
	/**
	 * 
	 * @param userId
	 * @param loginId
	 * @param userName
	 * @param nickName
	 * @param uerType
	 * @param orgName �̶���--"��Ҫ�ͻ�"
	 * @author �ܸ� �������ڣ�2010-8-5����09:02:18
	 * @since 1.0
	 */
	public void saveUser(String userId, String loginId,String password, String userName,String nickName, 
			String uerType,String orgName);
	/**
	 *  root Ϊд��
	 * (�˴�д����������������<br />��������<p/>��
	 * 
	 * @return
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2010-8-12����11:41:55
	 * @since 1.0
	 */
	public List getFirstOrgList();
	/***
	 * 
	 * ������Աid ��ѯ��֯����
	 * 
	 * @param userId
	 * @return
	 * @see (�˴�д�������һЩ���������û�У���ȥ����ע�ͣ��������һ�����뻻������һ��@see��
	 * @author �ܸ� �������ڣ�2010-8-16����09:14:11
	 * @since 1.0
	 */
	public List getCmpByPerson(String userId);
	public void backPassWord(String userId);
}
