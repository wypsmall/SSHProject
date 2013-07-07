package com.imti.sysmgr.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.imti.framework.common.Base64Util;
import com.imti.framework.common.BeanUtil;
import com.imti.framework.common.ImtiAssert;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.common.MD5Encoder;
import com.imti.framework.common.StringUtil;
import com.imti.framework.common.StringUtils;
import com.imti.framework.component.vopomapping.utils.POUtil;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.imti.sysmgr.bean.ImtiMapFactory;
import com.imti.sysmgr.dao.SysMgrDao;
import com.imti.sysmgr.po.OrgPO;
import com.imti.sysmgr.po.SysRolePO;
import com.imti.sysmgr.po.SysRoleRightPO;
import com.imti.sysmgr.po.UserOrgRealPO;
import com.imti.sysmgr.po.UserPO;
import com.imti.sysmgr.service.CacheService;
import com.imti.sysmgr.service.SysMgrService;
import com.imti.sysmgr.vo.OrgVO;
import com.imti.sysmgr.vo.SysRoleRightVO;
import com.imti.sysmgr.vo.SysRoleVO;
import com.imti.sysmgr.vo.UserVO;

public class SysMgrServiceImpl extends BaseServiceImpl implements SysMgrService {

	protected String DEFAULT_PASSWORD = "123456";
	public SysMgrDao getBaseDao() {
		return (SysMgrDao)SpringBeanUtil.getBean("sysMgrDao");
	}

	public Map getRsOperatorMapByLoginId(String loginId) {
		/*
		 * 1、采用jdbc实现
		 * 2、关联表：IMTI_SYS_USER（条件：账号有效）
		 * 			IMTI_SYS_USER_ROLE 用户与角色关联（后期可能会存在用户直接授权，不通过角色）
		 * 			IMTI_SYS_ROLE 角色
		 * 			IMTI_ROLE_RIGHT 角色权限表，类型type=1时表示资源权限 0 表示操作
		 * 			IMTI_SYS_RESOURCE 资源表（条件：资源有效）
		 * 			IMTI_SYS_OPERATOR 操作表
		 * 3、目的：求出指定账号对应的权限
		 */
		ImtiAssert.isNotBlank(loginId, "登录账号不能为空！");
		return getBaseDao().findRsOperatorMapByLoginId(loginId);
	}
	public Map getRsOperatorMapById(String userId) {
		/*
		 * 1、采用jdbc实现
		 * 2、关联表：IMTI_SYS_USER（条件：账号有效）
		 * 			IMTI_SYS_USER_ROLE 用户与角色关联（后期可能会存在用户直接授权，不通过角色）
		 * 			IMTI_SYS_ROLE 角色
		 * 			IMTI_ROLE_RIGHT 角色权限表，类型type=1时表示资源权限 0 表示操作
		 * 			IMTI_SYS_RESOURCE 资源表（条件：资源有效）
		 * 			IMTI_SYS_OPERATOR 操作表
		 * 3、目的：求出指定账号对应的权限
		 */
		ImtiAssert.isNotBlank(userId, "非法用户！");
		return getBaseDao().findRsOperatorMapById(userId);
	}

	public UserVO getUserByLoginId(String loginId) {
		String whereHql = " and po.valid='1' and po.loginId='" + loginId + "'";
		List list = super.getByConditionWithNoPage(UserPO.class, whereHql);

		if(ImtiListUtil.isNotEmpty(list)){
			return (UserVO)list.get(0);
		}
		return null;
	}
	public UserVO getUserByLoginId(String loginId,String ztId) {
		String whereHql = " and po.valid='1' and po.loginId='" + loginId + "' and po.ztId = '"+ztId+"'";
		List list = super.getByConditionWithNoPage(UserPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			return (UserVO)list.get(0);
		}
		return null;
	}
	public Map getRsOperatorMapByRoleId(String roleId) {
		 /*
		 *获取角色对应的权限map(资源code或者资源code.操作code,类型)
		 * 1、采用jdbc实现
		 * 2、关联表：
		 * 			IMTI_SYS_ROLE 角色
		 * 			IMTI_ROLE_RIGHT 角色权限表，类型type=1时表示资源权限 0 表示操作
		 * 			IMTI_SYS_RESOURCE 资源表（条件：资源有效）
		 * 			IMTI_SYS_OPERATOR 操作表
		 * 3、目的：求出指定账号对应的权限
		 */
		if(StringUtils.isEmpty(roleId)){
			return new HashMap();
		}
		return getBaseDao().findRsOperatorMapByRoleId(roleId);
	}
	public List getOrgChildrenById(String parentId) {
		String whereHql = " and po.orgParentId='" + parentId + "'";
		return getByConditionWithNoPage(OrgPO.class, whereHql);
	}

	public void insertOrg(OrgVO orgVO) {
		ImtiAssert.isNotBlank(orgVO, "机构不存在");
		ImtiAssert.isNotBlank(orgVO.getOrgCode(), "机构编码不能为空");
		ImtiAssert.isNotBlank(orgVO.getOrgName(), "机构名称不能为空");
		
		/*
		 * 1、相同父机构下，不允许名称或者编码相同的两个机构
		 */
		
		String whereHql = " and po.orgParentId='" + orgVO.getOrgParentId() + "' and (po.orgName='" + orgVO.getOrgName() + "' or po.orgCode='" + orgVO.getOrgCode() + "')";
		List list = getByConditionWithNoPage(OrgPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("在" + orgVO.getOrgParentId() + "机构下，不允许存在相同名称或编码的机构!");
		}
		if("root".equals(orgVO.getOrgParentId())){
			orgVO.setOwnerCompany("root");
			orgVO.setDisplayIndex(1);
		}else{
			orgVO.setOwnerCompany("root_" + orgVO.getZtId());
			orgVO.setDisplayIndex(2);
		}
		super.insert(orgVO);
	}
	
	public void updateOrg(OrgVO orgVO) {
		ImtiAssert.isNotBlank(orgVO, "机构不存在");
		ImtiAssert.isNotBlank(orgVO.getOrgId(), "机构标识不能为空");
		ImtiAssert.isNotBlank(orgVO.getOrgCode(), "机构编码不能为空");
		ImtiAssert.isNotBlank(orgVO.getOrgName(), "机构名称不能为空");
		/*
		 * 1、相同父机构下，不允许名称或者编码相同的两个机构
		 */
		
		String whereHql = " and po.orgId<>'" + orgVO.getOrgId() + "' and po.orgParentId='" + orgVO.getOrgParentId() + "' and (po.orgName='" + orgVO.getOrgName() + "' or po.orgCode='" + orgVO.getOrgCode() + "')";
		List list = getByConditionWithNoPage(OrgPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("在" + orgVO.getOrgParentId() + "机构下，不允许存在相同名称或编码的机构!");
		}
		super.update(orgVO);
	}

	public List getUserListByOrgId(String orgId,String loginId, String userName) {
		/*
		if(StringUtils.isBlank(orgId)){
			return new ArrayList();
		}
		*/
		String whereHql = " and userType='0' " ;
		if(StringUtil.isNotBlank(orgId)){
			whereHql=whereHql+" and po.orgId='" + orgId + "'";
		}
		if(StringUtil.isNotBlank(loginId)){
			whereHql+=" and po.loginId like '%"+loginId+"%'";
		}
		if(StringUtil.isNotBlank(userName)){
			whereHql+=" and po.userName like '%"+userName+"%'";
		}
		whereHql+="  order by po.valid DESC";
		List list = getByConditionWithNoPage(UserPO.class, whereHql);
		return list;
	}

	public List getRoleList(String roleName) {
		String whereHql = "";
		try{
			if(StringUtils.isNotBlank(roleName)){
				if(roleName.indexOf("%") != -1){
					roleName = URLDecoder.decode(roleName,"UTF-8");
				} 
				whereHql = " and po.roleName like '%" + roleName + "%'";
			}
		}catch(Exception e){
		}
		
		whereHql = whereHql + " order by po.roleName";
		List list = getByConditionWithNoPage(SysRolePO.class, whereHql);
		return list;
	}

	public Map getRoleMapByUserId(String userId) {
		if(StringUtils.isEmpty(userId)){
			return new HashMap();
		}
		Map roleMap = new HashMap();
		UserPO po = getBaseDao().findUserById(userId);
		if(po != null && po.getRoleSet() != null){
			Set roleSet = po.getRoleSet();
			Iterator<SysRolePO> it = roleSet.iterator();
			while(it.hasNext()){
				SysRolePO role = (SysRolePO)it.next();
				roleMap.put(role.getId(), userId);
			}
			
		}
		return roleMap;
	}
    
	
	public Map getOrgMapByUserId(String userId) {
		if(StringUtils.isEmpty(userId)){
			return new HashMap();
		}
		Map orgMap = new HashMap();
		UserPO po = getBaseDao().findUserById(userId);
		if(po != null && po.getOrgSet() != null){
			Set orgSet = po.getOrgSet();
			Iterator<OrgPO> it = orgSet.iterator();
			while(it.hasNext()){
				OrgPO org = (OrgPO)it.next();
				orgMap.put(org.getOrgId(), userId);
			}
			
		}
		return orgMap;
	}
	/*
	public Map getOrgMapByUserId(String userId) {
		if(StringUtils.isEmpty(userId)){
			return new HashMap();
		}
		Map orgMap = new HashMap();
		String whereHql = " and po.userId ='"+userId+"'";
		List list = this.getByConditionWithNoPage(UserOrgRealPO.class, whereHql);
		Iterator<UserOrgRealPO> it = list.iterator();
		while(it.hasNext()){
			UserOrgRealPO org = (UserOrgRealPO)it.next();
			orgMap.put(org.getOrgId(), userId);
		}
		return orgMap;
	}*/
	
	public void insertRole(SysRoleVO roleVO, String rightRsIds) {
		/*
		 * 1、角色编码不能重复
		 */
		ImtiAssert.isNotBlank(roleVO, "角色不存在");
		ImtiAssert.isNotBlank(roleVO.getRoleCode(), "角色编码不能为空");
		ImtiAssert.isNotBlank(roleVO.getRoleName(), "角色名称不能为空");
		String whereHql = " and po.roleCode='" + roleVO.getRoleCode() + "'";
		List list = getByConditionWithNoPage(SysRolePO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("角色编码重复");
		}
		
		super.insert(roleVO);
		//添加权限
		if(StringUtils.isNotBlank(rightRsIds)){
			String[] rightRsId = rightRsIds.split(",");
			for(int i = 0; i < rightRsId.length; i++){
				SysRoleRightVO roleRight = new SysRoleRightVO();
				roleRight.setOperatorId(rightRsId[i].split("_")[0]);//ID
				roleRight.setRightType(rightRsId[i].split("_")[1]);//类型
				roleRight.setRoleId(roleVO.getId());
				insert(roleRight);
			}
		}
		//在改变角色的权限后更新全局的角色权限信息（全局的map中保存）
		Map rsMap = getRsOperatorMapByRoleId(roleVO.getId());
		ImtiMapFactory.setRsRoleMap(rsMap, roleVO.getId());

	}

	public void updateRole(SysRoleVO roleVO, String rightRsIds) {
		ImtiAssert.isNotBlank(roleVO, "角色不存在");
		ImtiAssert.isNotBlank(roleVO.getRoleCode(), "角色编码不能为空");
		ImtiAssert.isNotBlank(roleVO.getRoleName(), "角色名称不能为空");
		ImtiAssert.isNotBlank(roleVO.getId(), "角色ID不能为空");
		String whereHql = " and po.id<>'" + roleVO.getId() + "' and po.roleCode='" + roleVO.getRoleCode() + "'";
		List list = getByConditionWithNoPage(SysRolePO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("角色编码重复");
		}
		//修改权限（先删除原有的权限，再新增现在的权限）
		deleteRoleRight(roleVO.getId()); 
		super.update(roleVO);
		//添加权限
		if(StringUtils.isNotBlank(rightRsIds)){
			String[] rightRsId = rightRsIds.split(",");
			for(int i = 0; i < rightRsId.length; i++){
				SysRoleRightVO roleRight = new SysRoleRightVO();
				roleRight.setOperatorId(rightRsId[i].split("_")[0]);//ID
				roleRight.setRightType(rightRsId[i].split("_")[1]);//类型
				roleRight.setRoleId(roleVO.getId());
				insert(roleRight);
			}
		}
		//在改变角色的权限后更新全局的角色权限信息（全局的map中保存）
		Map rsMap = getRsOperatorMapByRoleId(roleVO.getId());
		ImtiMapFactory.setRsRoleMap(rsMap, roleVO.getId());
	}

	private void deleteRoleRight(String roleId){
		String whereHql = " and po.roleId='" + roleId + "'";
		getBaseDao().delete(SysRoleRightPO.class, whereHql);
	}
	public void resetPassWord(UserVO user, String oldPassword, String newPassword, String loginPassword) {
		ImtiAssert.isNotBlank(user, "用户对象丢失！");
		ImtiAssert.isNotBlank(user.getLoginId(), "用户账号丢失");
		ImtiAssert.isNotBlank(oldPassword, "旧密码不允许为空");
		ImtiAssert.isNotBlank(newPassword, "新密码不允许为空");
		ImtiAssert.isNotBlank(loginPassword, "确认密码不允许为空");
		if(!newPassword.equals(loginPassword)){
			throw new RuntimeException("确认密码与新密码不相同");
		}
		
		if("1".equals(user.getLoginType())){
			if(!oldPassword.equals(user.getPassword())){
				throw new RuntimeException("旧密码输入错误！");
			}
			user.setPassword(newPassword);
		}else if("2".equals(user.getLoginType())){
			if(!("DIGEST_BASE64_" + Base64Util.base64Encoder(oldPassword)).equals(user.getPassword())){
				throw new RuntimeException("旧密码输入错误！");
			}
			user.setPassword("DIGEST_BASE64_" + Base64Util.base64Encoder(newPassword));
		}else if("3".equals(user.getLoginType())){
			if(!("DIGEST_MD5_" + MD5Encoder.md5Encode(oldPassword)).equals(user.getPassword())){
				throw new RuntimeException("旧密码输入错误！");
			}
			user.setPassword("DIGEST_MD5_" + MD5Encoder.md5Encode(newPassword));
		}
		getBaseDao().resetPassWord(user.getLoginId(), user.getPassword());
		
	}

	public void deleteOrgByOrgId(String orgIds) {
		ImtiAssert.isNotBlank(orgIds, "机构标识不能为空！");
		String[] orgId = orgIds.split(",");
		if(orgId != null && orgId.length > 0){
			
			//FOR循环，递归删除子机构
			for(int i = 0; i < orgId.length; i++){
				String parentHql = " and po.orgParentId='" + orgId[i] + "'";
				List list = getByConditionWithNoPage(OrgPO.class, parentHql);
				if(ImtiListUtil.isNotEmpty(list)){
					String[] subRsId = ImtiListUtil.getPropertyStringArray(list, "orgId");
					String[] _orgIds = ImtiListUtil.getPropertyStringArray(list, "orgId");
					deleteOrgByOrgId(StringUtil.arrayToString(subRsId));
				}
			}
			//删除资源
			String whereHql = " and po.orgId in" + StringUtil.arrayToSQLString(orgId, StringUtil.SQL_STRING_BRACKETS);
			getBaseDao().delete(OrgPO.class, whereHql);
			//删除机构与用户关系
			getBaseDao().delete(UserOrgRealPO.class, " and po.orgId in "+ StringUtil.arrayToSQLString(orgId, StringUtil.SQL_STRING_BRACKETS));
		}
	}

	public void insertUser(UserVO user, String roleId,String orgIds) {
		ImtiAssert.isNotBlank(user, "用户对象不能为空！");
		/*
		 * 1、账号检测
		 * 
		 */
		String nameHql = " and po.valid='1' and po.loginId='" + user.getLoginId() + "'";
		List list = getByConditionWithNoPage(UserPO.class, nameHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("系统中已经存在一个相同的账号！");
		}
		if(StringUtils.isNotBlank(roleId)){
			String roleHql = " and po.id in " + StringUtil.arrayToSQLString(roleId.split(","), StringUtil.SQL_STRING_BRACKETS);
				List roleList = getByConditionWithNoPage(SysRolePO.class, roleHql);
				user.setRoleSet(ImtiListUtil.listToSet(roleList));
		}
		//密码加密处理
		if("3".equals(user.getLoginType())){
			user.setPassword("DIGEST_MD5_" + MD5Encoder.md5Encode(user.getPassword()));
		}else if("2".equals(user.getLoginType())){
			user.setPassword("DIGEST_BASE64_" + Base64Util.base64Encoder(user.getPassword()));
		}

		//插入用户 组织机构的关系表
		if(StringUtils.isNotBlank(orgIds)){
			String orgHql = " and po.orgId in " + StringUtil.arrayToSQLString(orgIds.split(","), StringUtil.SQL_STRING_BRACKETS);
			List orgList = getByConditionWithNoPage(OrgPO.class, orgHql);
			user.setOrgSet(ImtiListUtil.listToSet(orgList));
		}
		
		//2010-06-30 智能检测用户所在分公司
		//容错处理：加载用户所在的分公司
		if(StringUtils.isBlank(user.getCompanyCode()) && !StringUtils.isBlank(user.getOrgId())){
			String cmpCode = getCmpCOde(user.getOrgId());
			user.setCompanyCode(cmpCode);
		}
		super.update(user);
		getCacheService().clearUserCache(user.getId());//清除缓存
	}

	private String getCmpCOde(String orgId){
		OrgVO org = (OrgVO)getByPk(OrgPO.class, orgId);
		if(org != null && "root".equals(org.getOrgParentId())){
			//表示当前的单位就是分公司
			return org.getOrgCode();
		}else if(org != null && !"root".equals(org.getOrgParentId())){
			return getCmpCOde(org.getOrgParentId());
		}
		return "";
	}
	
	
	public void updateUser(UserVO user, String roleId,String orgIds) {
		ImtiAssert.isNotBlank(user, "用户对象不能为空！");
		ImtiAssert.isNotBlank(user.getId(), "用户标识不能为空！");
		/*
		 * 1、账号检测
		 */
		String nameHql = " and po.id<>'" + user.getId() + "' and po.valid='1' and po.loginId='" + user.getLoginId() + "'";
		List list = getByConditionWithNoPage(UserPO.class, nameHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("系统中已经存在一个相同的账号！");
		}
		if(StringUtils.isNotBlank(roleId)){
			String roleHql = " and po.id in " + StringUtil.arrayToSQLString(roleId.split(","), StringUtil.SQL_STRING_BRACKETS);
				List roleList = getByConditionWithNoPage(SysRolePO.class, roleHql);
				user.setRoleSet(ImtiListUtil.listToSet(roleList));
		}
		
		
		
		//容错处理：加载用户所在的分公司
		if(StringUtils.isBlank(user.getCompanyCode()) && !StringUtils.isBlank(user.getOrgId())){
			String cmpCode = getCmpCOde(user.getOrgId());
			user.setCompanyCode(cmpCode);
		}
		
		//插入用户 组织机构的关系表
		if(StringUtils.isNotBlank(orgIds)){
			String orgHql = " and po.orgId in " + StringUtil.arrayToSQLString(orgIds.split(","), StringUtil.SQL_STRING_BRACKETS);
			List orgList = getByConditionWithNoPage(OrgPO.class, orgHql);
			user.setOrgSet(ImtiListUtil.listToSet(orgList));
		}
		super.update(user);
		getCacheService().clearUserCache(user.getId());//清除缓存
	}

	public void deleteUserById(String userIds) {
		ImtiAssert.isNotBlank(userIds, "用户标识不能为空！");
		String[] userId = userIds.split(",");
		if(userId != null && userId.length > 0){
			//删除用户并且删除用户与角色的关系
			getBaseDao().deleteUser(userId);
		}

		getCacheService().clearUserCache(userIds);//清除缓存
	}

	public void activationUser(String userIds) {
		ImtiAssert.isNotBlank(userIds, "用户标识不能为空！");
		String[] fid = userIds.split(",");
		String whereHql = " and po.id in" + StringUtil.arrayToSQLString(fid, StringUtil.SQL_STRING_BRACKETS);
		List list = getByConditionWithNoPage(UserPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				UserVO user = (UserVO)list.get(i);
				if("1".equals(user.getValid())){
					continue;
				}
				user.setValid("1");
				super.update(user);
			}
		}
		getCacheService().clearUserCache(userIds);//清除缓存
	}

	public void suspensionUser(String userIds) {
		ImtiAssert.isNotBlank(userIds, "用户标识不能为空！");
		ImtiAssert.isNotBlank(userIds, "用户标识不能为空！");
		String[] fid = userIds.split(",");
		String whereHql = " and po.id in" + StringUtil.arrayToSQLString(fid, StringUtil.SQL_STRING_BRACKETS);
		List list = getByConditionWithNoPage(UserPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			for(int i = 0; i < list.size(); i++){
				UserVO user = (UserVO)list.get(i);
				if("0".equals(user.getValid())){
					continue;
				}
				user.setValid("0");
				super.update(user);
			}
		}
		getCacheService().clearUserCache(userIds);//清除缓存
	}

	public void deleteRoleById(String roleIds) {
		ImtiAssert.isNotBlank(roleIds, "角色标识不能为空！");
		String[] roleId = roleIds.split(",");
		if(roleId != null && roleId.length > 0){
			//删除角色并且删除用户与角色的关系、角色权限关系
			getBaseDao().deleteRole(roleId);
		}
	}

	public List getCompany(String startStr, String limitStr) {
		String whereHql = " and po.orgParentId='root'";
		int start = 0;
		int limit = 5;
		if(StringUtils.isNotEmpty(startStr)){
			start = Integer.parseInt(startStr);
		}
		if(StringUtils.isNotEmpty(limitStr)){
			limit = Integer.parseInt(limitStr);
		}
		
		List poList = getBaseDao().find(OrgPO.class, start, limit, whereHql);
		if(ImtiListUtil.isNotEmpty(poList)){
			return POUtil.copyPoListToVoList(poList);
		}
		return new ArrayList();
	}
	public void saveUser(String userId, String loginId,String password, String userName,String nickName, 
			String userType,String orgName){
		ImtiAssert.isNotBlank(userId, "用户ID不能为空！");
		ImtiAssert.isNotBlank(loginId, "用户账号不能为空！");
		ImtiAssert.isNotBlank(userType, "用户类型不能为空！");
		ImtiAssert.isNotBlank(orgName, "用户归属单位不能为空！");
		OrgVO org = getOrgByName(orgName);
		if(StringUtils.isEmpty(password)){
			password = "123456";
		}
		password = "DIGEST_MD5_" + MD5Encoder.md5Encode(password);
		//先检测该客户是否给他建立了用户
		Object user = getByPk(UserPO.class, userId);
		if(null != user){
			UserVO userVO = (UserVO)user;
			throw new RuntimeException("该客户已经分配了账号：" + userVO.getLoginId() + "；用户名【" + userVO.getUserName() + "】");
		}
		getBaseDao().saveUser(userId, loginId, password, userName, nickName, userType, 
				org.getOrgId());
		//保存客户与角色关系
		getCacheService().clearUserCache(userId);//清除缓存
	}
	private OrgVO getOrgByName(String orgName){
		OrgVO org = new OrgVO();
		String whereHql = " and po.orgName='" + orgName + "'";
		List list = getByConditionWithNoPage(OrgPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			return (OrgVO)list.get(0);
		}else {
			org.setMemo("系统自动创建");
			org.setOrgName(orgName);
			org.setOrgCode("JP_CUSTOMER");
			org.setOrgParentId("root");
			org.setOrgParentName("骏鹏组织机构");
			org.setOrgParentCode("root");
			insert(org);
			return org;
		}
	}
	/**
	 * 
	 */
	public List getFirstOrgList() {
		String whereHql = " and po.orgParentId='root'";
		List poList = getBaseDao().find(OrgPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(poList)){
			return POUtil.copyPoListToVoList(poList);
		}
		return new ArrayList();
	}
	/**
	 * 
	 */
	public List getCmpByPerson(String userId){
		return this.getBaseDao().getCmpByPerson(userId);
	}
	private CacheService getCacheService(){
		String className = PropertyUtils.getProperty("imti_user_cache_impl", "com.imti.sysmgr.service.impl.DefaultCacheServiceImpl");
		CacheService cacheService = (CacheService)BeanUtil.newInstance(className);
		return cacheService;
	}

	@Override
	public void backPassWord(String userId) {
		ImtiAssert.isNotBlank(userId, "用户账号不能为空！");
		UserVO user = (UserVO)getByPk(UserPO.class, userId);
		if("1".equals(user.getLoginType())){
			user.setPassword(DEFAULT_PASSWORD);
		}else if("2".equals(user.getLoginType())){
			user.setPassword("DIGEST_BASE64_" + Base64Util.base64Encoder(DEFAULT_PASSWORD));
		}else if("3".equals(user.getLoginType())){
			user.setPassword("DIGEST_MD5_" + MD5Encoder.md5Encode(DEFAULT_PASSWORD));
		}
		getBaseDao().resetPassWord(user.getLoginId(), user.getPassword());
	}
}
 