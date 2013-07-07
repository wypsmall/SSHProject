package com.imti.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.imti.framework.web.dao.BaseDao;
import com.imti.sysmgr.po.UserPO;

public interface SysMgrDao extends BaseDao {
	/**
	 * 根据用户主键，获取用户对象
	 * @param userId
	 * @return
	 */
	public UserPO findUserById(String userId);

	public Map findRsOperatorMapByLoginId(String loginId);

	public Map findRsOperatorMapById(String userId);
	
	public Map findRsOperatorMapByRoleId(String roleId);
	
	public void resetPassWord(String loginId, String password);

	public void deleteUser(String[] userId);

	public void deleteRole(String[] roleId);

	public void saveUser(String userId, String loginId, String password, String userName, String nickName, String userType, String orgId);

	public List getCmpByPerson(String userId);
}
