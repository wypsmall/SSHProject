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
	 * 获取组织机构下的所有用户（包含禁用与在用的用户）
	 * @param orgId
	 * @param loginId
	 * @return
	 */
	public List getUserListByOrgId(String orgId,String loginId, String userName);
	/**
	 * 获取角色列表
	 * @return
	 */
	public List getRoleList(String roleName);
	/**
	 * 获取用户所在的角色
	 * @param userId
	 * @return
	 */
	public Map getRoleMapByUserId(String userId);
	/**
	 * 获取用户所在的组织机构
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
	 * @param orgName 固定死--"重要客户"
	 * @author 曹刚 新增日期：2010-8-5下午09:02:18
	 * @since 1.0
	 */
	public void saveUser(String userId, String loginId,String password, String userName,String nickName, 
			String uerType,String orgName);
	/**
	 *  root 为写死
	 * (此处写功能描述，换行用<br />，换段用<p/>）
	 * 
	 * @return
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2010-8-12上午11:41:55
	 * @since 1.0
	 */
	public List getFirstOrgList();
	/***
	 * 
	 * 根据人员id 查询组织机构
	 * 
	 * @param userId
	 * @return
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2010-8-16上午09:14:11
	 * @since 1.0
	 */
	public List getCmpByPerson(String userId);
	public void backPassWord(String userId);
}
