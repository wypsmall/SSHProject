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
		 * 1������jdbcʵ��
		 * 2��������IMTI_SYS_USER���������˺���Ч��
		 * 			IMTI_SYS_USER_ROLE �û����ɫ���������ڿ��ܻ�����û�ֱ����Ȩ����ͨ����ɫ��
		 * 			IMTI_SYS_ROLE ��ɫ
		 * 			IMTI_ROLE_RIGHT ��ɫȨ�ޱ�����type=1ʱ��ʾ��ԴȨ�� 0 ��ʾ����
		 * 			IMTI_SYS_RESOURCE ��Դ����������Դ��Ч��
		 * 			IMTI_SYS_OPERATOR ������
		 * 3��Ŀ�ģ����ָ���˺Ŷ�Ӧ��Ȩ��
		 */
		ImtiAssert.isNotBlank(loginId, "��¼�˺Ų���Ϊ�գ�");
		return getBaseDao().findRsOperatorMapByLoginId(loginId);
	}
	public Map getRsOperatorMapById(String userId) {
		/*
		 * 1������jdbcʵ��
		 * 2��������IMTI_SYS_USER���������˺���Ч��
		 * 			IMTI_SYS_USER_ROLE �û����ɫ���������ڿ��ܻ�����û�ֱ����Ȩ����ͨ����ɫ��
		 * 			IMTI_SYS_ROLE ��ɫ
		 * 			IMTI_ROLE_RIGHT ��ɫȨ�ޱ�����type=1ʱ��ʾ��ԴȨ�� 0 ��ʾ����
		 * 			IMTI_SYS_RESOURCE ��Դ����������Դ��Ч��
		 * 			IMTI_SYS_OPERATOR ������
		 * 3��Ŀ�ģ����ָ���˺Ŷ�Ӧ��Ȩ��
		 */
		ImtiAssert.isNotBlank(userId, "�Ƿ��û���");
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
		 *��ȡ��ɫ��Ӧ��Ȩ��map(��Դcode������Դcode.����code,����)
		 * 1������jdbcʵ��
		 * 2��������
		 * 			IMTI_SYS_ROLE ��ɫ
		 * 			IMTI_ROLE_RIGHT ��ɫȨ�ޱ�����type=1ʱ��ʾ��ԴȨ�� 0 ��ʾ����
		 * 			IMTI_SYS_RESOURCE ��Դ����������Դ��Ч��
		 * 			IMTI_SYS_OPERATOR ������
		 * 3��Ŀ�ģ����ָ���˺Ŷ�Ӧ��Ȩ��
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
		ImtiAssert.isNotBlank(orgVO, "����������");
		ImtiAssert.isNotBlank(orgVO.getOrgCode(), "�������벻��Ϊ��");
		ImtiAssert.isNotBlank(orgVO.getOrgName(), "�������Ʋ���Ϊ��");
		
		/*
		 * 1����ͬ�������£����������ƻ��߱�����ͬ����������
		 */
		
		String whereHql = " and po.orgParentId='" + orgVO.getOrgParentId() + "' and (po.orgName='" + orgVO.getOrgName() + "' or po.orgCode='" + orgVO.getOrgCode() + "')";
		List list = getByConditionWithNoPage(OrgPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("��" + orgVO.getOrgParentId() + "�����£������������ͬ���ƻ����Ļ���!");
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
		ImtiAssert.isNotBlank(orgVO, "����������");
		ImtiAssert.isNotBlank(orgVO.getOrgId(), "������ʶ����Ϊ��");
		ImtiAssert.isNotBlank(orgVO.getOrgCode(), "�������벻��Ϊ��");
		ImtiAssert.isNotBlank(orgVO.getOrgName(), "�������Ʋ���Ϊ��");
		/*
		 * 1����ͬ�������£����������ƻ��߱�����ͬ����������
		 */
		
		String whereHql = " and po.orgId<>'" + orgVO.getOrgId() + "' and po.orgParentId='" + orgVO.getOrgParentId() + "' and (po.orgName='" + orgVO.getOrgName() + "' or po.orgCode='" + orgVO.getOrgCode() + "')";
		List list = getByConditionWithNoPage(OrgPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("��" + orgVO.getOrgParentId() + "�����£������������ͬ���ƻ����Ļ���!");
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
		 * 1����ɫ���벻���ظ�
		 */
		ImtiAssert.isNotBlank(roleVO, "��ɫ������");
		ImtiAssert.isNotBlank(roleVO.getRoleCode(), "��ɫ���벻��Ϊ��");
		ImtiAssert.isNotBlank(roleVO.getRoleName(), "��ɫ���Ʋ���Ϊ��");
		String whereHql = " and po.roleCode='" + roleVO.getRoleCode() + "'";
		List list = getByConditionWithNoPage(SysRolePO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("��ɫ�����ظ�");
		}
		
		super.insert(roleVO);
		//���Ȩ��
		if(StringUtils.isNotBlank(rightRsIds)){
			String[] rightRsId = rightRsIds.split(",");
			for(int i = 0; i < rightRsId.length; i++){
				SysRoleRightVO roleRight = new SysRoleRightVO();
				roleRight.setOperatorId(rightRsId[i].split("_")[0]);//ID
				roleRight.setRightType(rightRsId[i].split("_")[1]);//����
				roleRight.setRoleId(roleVO.getId());
				insert(roleRight);
			}
		}
		//�ڸı��ɫ��Ȩ�޺����ȫ�ֵĽ�ɫȨ����Ϣ��ȫ�ֵ�map�б��棩
		Map rsMap = getRsOperatorMapByRoleId(roleVO.getId());
		ImtiMapFactory.setRsRoleMap(rsMap, roleVO.getId());

	}

	public void updateRole(SysRoleVO roleVO, String rightRsIds) {
		ImtiAssert.isNotBlank(roleVO, "��ɫ������");
		ImtiAssert.isNotBlank(roleVO.getRoleCode(), "��ɫ���벻��Ϊ��");
		ImtiAssert.isNotBlank(roleVO.getRoleName(), "��ɫ���Ʋ���Ϊ��");
		ImtiAssert.isNotBlank(roleVO.getId(), "��ɫID����Ϊ��");
		String whereHql = " and po.id<>'" + roleVO.getId() + "' and po.roleCode='" + roleVO.getRoleCode() + "'";
		List list = getByConditionWithNoPage(SysRolePO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("��ɫ�����ظ�");
		}
		//�޸�Ȩ�ޣ���ɾ��ԭ�е�Ȩ�ޣ����������ڵ�Ȩ�ޣ�
		deleteRoleRight(roleVO.getId()); 
		super.update(roleVO);
		//���Ȩ��
		if(StringUtils.isNotBlank(rightRsIds)){
			String[] rightRsId = rightRsIds.split(",");
			for(int i = 0; i < rightRsId.length; i++){
				SysRoleRightVO roleRight = new SysRoleRightVO();
				roleRight.setOperatorId(rightRsId[i].split("_")[0]);//ID
				roleRight.setRightType(rightRsId[i].split("_")[1]);//����
				roleRight.setRoleId(roleVO.getId());
				insert(roleRight);
			}
		}
		//�ڸı��ɫ��Ȩ�޺����ȫ�ֵĽ�ɫȨ����Ϣ��ȫ�ֵ�map�б��棩
		Map rsMap = getRsOperatorMapByRoleId(roleVO.getId());
		ImtiMapFactory.setRsRoleMap(rsMap, roleVO.getId());
	}

	private void deleteRoleRight(String roleId){
		String whereHql = " and po.roleId='" + roleId + "'";
		getBaseDao().delete(SysRoleRightPO.class, whereHql);
	}
	public void resetPassWord(UserVO user, String oldPassword, String newPassword, String loginPassword) {
		ImtiAssert.isNotBlank(user, "�û�����ʧ��");
		ImtiAssert.isNotBlank(user.getLoginId(), "�û��˺Ŷ�ʧ");
		ImtiAssert.isNotBlank(oldPassword, "�����벻����Ϊ��");
		ImtiAssert.isNotBlank(newPassword, "�����벻����Ϊ��");
		ImtiAssert.isNotBlank(loginPassword, "ȷ�����벻����Ϊ��");
		if(!newPassword.equals(loginPassword)){
			throw new RuntimeException("ȷ�������������벻��ͬ");
		}
		
		if("1".equals(user.getLoginType())){
			if(!oldPassword.equals(user.getPassword())){
				throw new RuntimeException("�������������");
			}
			user.setPassword(newPassword);
		}else if("2".equals(user.getLoginType())){
			if(!("DIGEST_BASE64_" + Base64Util.base64Encoder(oldPassword)).equals(user.getPassword())){
				throw new RuntimeException("�������������");
			}
			user.setPassword("DIGEST_BASE64_" + Base64Util.base64Encoder(newPassword));
		}else if("3".equals(user.getLoginType())){
			if(!("DIGEST_MD5_" + MD5Encoder.md5Encode(oldPassword)).equals(user.getPassword())){
				throw new RuntimeException("�������������");
			}
			user.setPassword("DIGEST_MD5_" + MD5Encoder.md5Encode(newPassword));
		}
		getBaseDao().resetPassWord(user.getLoginId(), user.getPassword());
		
	}

	public void deleteOrgByOrgId(String orgIds) {
		ImtiAssert.isNotBlank(orgIds, "������ʶ����Ϊ�գ�");
		String[] orgId = orgIds.split(",");
		if(orgId != null && orgId.length > 0){
			
			//FORѭ�����ݹ�ɾ���ӻ���
			for(int i = 0; i < orgId.length; i++){
				String parentHql = " and po.orgParentId='" + orgId[i] + "'";
				List list = getByConditionWithNoPage(OrgPO.class, parentHql);
				if(ImtiListUtil.isNotEmpty(list)){
					String[] subRsId = ImtiListUtil.getPropertyStringArray(list, "orgId");
					String[] _orgIds = ImtiListUtil.getPropertyStringArray(list, "orgId");
					deleteOrgByOrgId(StringUtil.arrayToString(subRsId));
				}
			}
			//ɾ����Դ
			String whereHql = " and po.orgId in" + StringUtil.arrayToSQLString(orgId, StringUtil.SQL_STRING_BRACKETS);
			getBaseDao().delete(OrgPO.class, whereHql);
			//ɾ���������û���ϵ
			getBaseDao().delete(UserOrgRealPO.class, " and po.orgId in "+ StringUtil.arrayToSQLString(orgId, StringUtil.SQL_STRING_BRACKETS));
		}
	}

	public void insertUser(UserVO user, String roleId,String orgIds) {
		ImtiAssert.isNotBlank(user, "�û�������Ϊ�գ�");
		/*
		 * 1���˺ż��
		 * 
		 */
		String nameHql = " and po.valid='1' and po.loginId='" + user.getLoginId() + "'";
		List list = getByConditionWithNoPage(UserPO.class, nameHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("ϵͳ���Ѿ�����һ����ͬ���˺ţ�");
		}
		if(StringUtils.isNotBlank(roleId)){
			String roleHql = " and po.id in " + StringUtil.arrayToSQLString(roleId.split(","), StringUtil.SQL_STRING_BRACKETS);
				List roleList = getByConditionWithNoPage(SysRolePO.class, roleHql);
				user.setRoleSet(ImtiListUtil.listToSet(roleList));
		}
		//������ܴ���
		if("3".equals(user.getLoginType())){
			user.setPassword("DIGEST_MD5_" + MD5Encoder.md5Encode(user.getPassword()));
		}else if("2".equals(user.getLoginType())){
			user.setPassword("DIGEST_BASE64_" + Base64Util.base64Encoder(user.getPassword()));
		}

		//�����û� ��֯�����Ĺ�ϵ��
		if(StringUtils.isNotBlank(orgIds)){
			String orgHql = " and po.orgId in " + StringUtil.arrayToSQLString(orgIds.split(","), StringUtil.SQL_STRING_BRACKETS);
			List orgList = getByConditionWithNoPage(OrgPO.class, orgHql);
			user.setOrgSet(ImtiListUtil.listToSet(orgList));
		}
		
		//2010-06-30 ���ܼ���û����ڷֹ�˾
		//�ݴ��������û����ڵķֹ�˾
		if(StringUtils.isBlank(user.getCompanyCode()) && !StringUtils.isBlank(user.getOrgId())){
			String cmpCode = getCmpCOde(user.getOrgId());
			user.setCompanyCode(cmpCode);
		}
		super.update(user);
		getCacheService().clearUserCache(user.getId());//�������
	}

	private String getCmpCOde(String orgId){
		OrgVO org = (OrgVO)getByPk(OrgPO.class, orgId);
		if(org != null && "root".equals(org.getOrgParentId())){
			//��ʾ��ǰ�ĵ�λ���Ƿֹ�˾
			return org.getOrgCode();
		}else if(org != null && !"root".equals(org.getOrgParentId())){
			return getCmpCOde(org.getOrgParentId());
		}
		return "";
	}
	
	
	public void updateUser(UserVO user, String roleId,String orgIds) {
		ImtiAssert.isNotBlank(user, "�û�������Ϊ�գ�");
		ImtiAssert.isNotBlank(user.getId(), "�û���ʶ����Ϊ�գ�");
		/*
		 * 1���˺ż��
		 */
		String nameHql = " and po.id<>'" + user.getId() + "' and po.valid='1' and po.loginId='" + user.getLoginId() + "'";
		List list = getByConditionWithNoPage(UserPO.class, nameHql);
		if(ImtiListUtil.isNotEmpty(list)){
			throw new RuntimeException("ϵͳ���Ѿ�����һ����ͬ���˺ţ�");
		}
		if(StringUtils.isNotBlank(roleId)){
			String roleHql = " and po.id in " + StringUtil.arrayToSQLString(roleId.split(","), StringUtil.SQL_STRING_BRACKETS);
				List roleList = getByConditionWithNoPage(SysRolePO.class, roleHql);
				user.setRoleSet(ImtiListUtil.listToSet(roleList));
		}
		
		
		
		//�ݴ��������û����ڵķֹ�˾
		if(StringUtils.isBlank(user.getCompanyCode()) && !StringUtils.isBlank(user.getOrgId())){
			String cmpCode = getCmpCOde(user.getOrgId());
			user.setCompanyCode(cmpCode);
		}
		
		//�����û� ��֯�����Ĺ�ϵ��
		if(StringUtils.isNotBlank(orgIds)){
			String orgHql = " and po.orgId in " + StringUtil.arrayToSQLString(orgIds.split(","), StringUtil.SQL_STRING_BRACKETS);
			List orgList = getByConditionWithNoPage(OrgPO.class, orgHql);
			user.setOrgSet(ImtiListUtil.listToSet(orgList));
		}
		super.update(user);
		getCacheService().clearUserCache(user.getId());//�������
	}

	public void deleteUserById(String userIds) {
		ImtiAssert.isNotBlank(userIds, "�û���ʶ����Ϊ�գ�");
		String[] userId = userIds.split(",");
		if(userId != null && userId.length > 0){
			//ɾ���û�����ɾ���û����ɫ�Ĺ�ϵ
			getBaseDao().deleteUser(userId);
		}

		getCacheService().clearUserCache(userIds);//�������
	}

	public void activationUser(String userIds) {
		ImtiAssert.isNotBlank(userIds, "�û���ʶ����Ϊ�գ�");
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
		getCacheService().clearUserCache(userIds);//�������
	}

	public void suspensionUser(String userIds) {
		ImtiAssert.isNotBlank(userIds, "�û���ʶ����Ϊ�գ�");
		ImtiAssert.isNotBlank(userIds, "�û���ʶ����Ϊ�գ�");
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
		getCacheService().clearUserCache(userIds);//�������
	}

	public void deleteRoleById(String roleIds) {
		ImtiAssert.isNotBlank(roleIds, "��ɫ��ʶ����Ϊ�գ�");
		String[] roleId = roleIds.split(",");
		if(roleId != null && roleId.length > 0){
			//ɾ����ɫ����ɾ���û����ɫ�Ĺ�ϵ����ɫȨ�޹�ϵ
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
		ImtiAssert.isNotBlank(userId, "�û�ID����Ϊ�գ�");
		ImtiAssert.isNotBlank(loginId, "�û��˺Ų���Ϊ�գ�");
		ImtiAssert.isNotBlank(userType, "�û����Ͳ���Ϊ�գ�");
		ImtiAssert.isNotBlank(orgName, "�û�������λ����Ϊ�գ�");
		OrgVO org = getOrgByName(orgName);
		if(StringUtils.isEmpty(password)){
			password = "123456";
		}
		password = "DIGEST_MD5_" + MD5Encoder.md5Encode(password);
		//�ȼ��ÿͻ��Ƿ�����������û�
		Object user = getByPk(UserPO.class, userId);
		if(null != user){
			UserVO userVO = (UserVO)user;
			throw new RuntimeException("�ÿͻ��Ѿ��������˺ţ�" + userVO.getLoginId() + "���û�����" + userVO.getUserName() + "��");
		}
		getBaseDao().saveUser(userId, loginId, password, userName, nickName, userType, 
				org.getOrgId());
		//����ͻ����ɫ��ϵ
		getCacheService().clearUserCache(userId);//�������
	}
	private OrgVO getOrgByName(String orgName){
		OrgVO org = new OrgVO();
		String whereHql = " and po.orgName='" + orgName + "'";
		List list = getByConditionWithNoPage(OrgPO.class, whereHql);
		if(ImtiListUtil.isNotEmpty(list)){
			return (OrgVO)list.get(0);
		}else {
			org.setMemo("ϵͳ�Զ�����");
			org.setOrgName(orgName);
			org.setOrgCode("JP_CUSTOMER");
			org.setOrgParentId("root");
			org.setOrgParentName("������֯����");
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
		ImtiAssert.isNotBlank(userId, "�û��˺Ų���Ϊ�գ�");
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
 