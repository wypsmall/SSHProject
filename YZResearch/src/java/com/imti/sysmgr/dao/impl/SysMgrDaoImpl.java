package com.imti.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.common.StringUtil;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;
import com.imti.framework.web.dao.impl.BaseDaoImpl;
import com.imti.sysmgr.dao.SysMgrDao;
import com.imti.sysmgr.po.SysRolePO;
import com.imti.sysmgr.po.UserPO;
import com.imti.sysmgr.vo.OrgVO;

public class SysMgrDaoImpl extends BaseDaoImpl implements SysMgrDao {

	public UserPO findUserById(String userId) {
		Criteria  criteria = getSession().createCriteria(UserPO.class);
		criteria.add(Expression.eq("id",userId));
		List<UserPO> list = criteria.list();
		if(ImtiListUtil.isNotEmpty(list)){
			return (UserPO)list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> findRsOperatorMapById(final String userId){
		return (Map<String, String>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Map<String, String> pemissionMap = new HashMap<String, String>();
				String sql = "select (t0.fcode+'_'+t5.fcode) as code1,t4.fright_type" + 
						 " from IMTI_SYS_USER t1, IMTI_SYS_USER_ROLE t2, IMTI_SYS_ROLE t3, imti_sys_role_right t4, IMTI_SYS_OPERATOR t5 ,IMTI_SYS_RESOURCE t0" + 
						 " where t1.fid=? " + 
						 " and t1.fid=t2.fuser_id " + 
						 " and t2.frole_id=t3.fid " + 
						 " and t3.fid=t4.frole_id " + 
						 " and t4.fright_type='0' " + 
						 " and t4.foperator_id=t5.fid " + 
						 " and t5.frs_id=t0.fid " + 
						 " union  " + 
						 " select t10.fcode,t9.fright_type " + 
						 " from IMTI_SYS_USER t6, IMTI_SYS_USER_ROLE t7, IMTI_SYS_ROLE t8, imti_sys_role_right t9, IMTI_SYS_RESOURCE t10 " + 
						 " where t6.fid=? " + 
						 " and t6.fid=t7.fuser_id " + 
						 " and t7.frole_id=t8.fid " + 
						 " and t8.fid=t9.frole_id " + 
						 " and t9.fright_type='1' " + 
						 " and t9.FOPERATOR_ID=t10.fid";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setString(0, userId);
				sqlQuery.setString(1, userId);
				List<Object> list = sqlQuery.list();
				for(Object object : list){
					Object[] objArr = (Object[])object;
					String code = (String)objArr[0];
					String type = (String)objArr[1];
					pemissionMap.put(code, type);
				}
				return pemissionMap;
			}
		});
	}
	public Map findRsOperatorMapByLoginId(String loginId) {
		/*
		 * 分成两个部分
		 * 1、资源部分放到map(资源编码，1)
		 * 2、操作部分放到map(操作编码_资源编码，0)
		 */
		Map pemissionMap = new HashMap();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select t0.fcode||'_'||t5.fcode as code1,t4.fright_type " + 
					 " from IMTI_SYS_USER t1, IMTI_SYS_USER_ROLE t2, IMTI_SYS_ROLE t3, imti_sys_role_right t4, IMTI_SYS_OPERATOR t5 ,IMTI_SYS_RESOURCE t0" + 
					 " where t1.flogin_id=? " + 
					 " and t1.fid=t2.fuser_id " + 
					 " and t2.frole_id=t3.fid " + 
					 " and t3.fid=t4.frole_id " + 
					 " and t4.fright_type='0' " + 
					 " and t4.foperator_id=t5.fid " + 
					 " and t5.frs_id=t0.fid " + 
					 " union  " + 
					 " select t10.fcode,t9.fright_type " + 
					 " from IMTI_SYS_USER t6, IMTI_SYS_USER_ROLE t7, IMTI_SYS_ROLE t8, imti_sys_role_right t9, IMTI_SYS_RESOURCE t10 " + 
					 " where t6.flogin_id=? " + 
					 " and t6.fid=t7.fuser_id " + 
					 " and t7.frole_id=t8.fid " + 
					 " and t8.fid=t9.frole_id " + 
					 " and t9.fright_type='1' " + 
					 " and t9.FOPERATOR_ID=t10.fid";
		try {
			conn = session.connection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, loginId);
			rs = ps.executeQuery();
			while(rs.next()){
				String code = rs.getString("code1");
				String type = rs.getString("fright_type");
				pemissionMap.put(code, type);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(conn != null){
					conn.close();
				}
				if(session != null){
					session.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pemissionMap;
	}
	@SuppressWarnings("unchecked")
	public Map findRsOperatorMapByRoleId(final String roleId) {
		/*
		 * 分成两个部分
		 * 1、资源部分放到map(资源编码，1)
		 * 2、操作部分放到map(操作编码_资源编码，0)
		 */
		return (Map<String, String>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Map<String, String> pemissionMap = new HashMap<String, String>();
				String sql = "select (t3.fcode+'_'+t2.fcode) code ,t1.fright_type " + 
							" from imti_sys_role_right t1, imti_sys_operator t2,imti_sys_resource t3 " +
							" where t1.foperator_id=t2.fid " +
							" and t1.fright_type='0' " +
							" and t2.frs_id=t3.fid " +
							" and t1.FROLE_ID=? " +
							" union " +
							" select t5.fcode code ,t4.fright_type " +
							" from imti_sys_role_right t4, imti_sys_resource t5 " +
							" where t4.foperator_id=t5.fid " +
							" and t4.FROLE_ID=? " +
							" and t4.fright_type='1'";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setString(0, roleId);
				sqlQuery.setString(1, roleId);
				List<Object> list = sqlQuery.list();
				for(Object object : list){
					Object[] objArr = (Object[])object;
					String code = (String)objArr[0];
					String type = (String)objArr[1];
					pemissionMap.put(code, type);
				}
				return pemissionMap;
			}
		});
	}
	
	public void resetPassWord(String loginId, String password) {
		/*
		 * 修改密码
		 */
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update IMTI_SYS_USER u set u.FPASSWORD=? where u.FVALID='1' and u.FLOGIN_ID=?";
		try {
			conn = session.connection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, loginId);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ps != null){
					ps.close();
				}
				if(conn != null){
					conn.close();
				}
				if(session != null){
					session.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteUser(String[] userId) {
		/*
		 * 1、删除用户与角色关系
		 * 2、删除用户
		 */
		
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from imti_sys_user_role where FUSER_ID in" + StringUtil.arrayToSQLString(userId, StringUtil.SQL_STRING_BRACKETS);
		try {
			conn = session.connection();
			ps = conn.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ps != null){
					ps.close();
				}
				if(conn != null){
					conn.close();
				}
				if(session != null){
					session.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		String whereHql = " and po.id in" + StringUtil.arrayToSQLString(userId, StringUtil.SQL_STRING_BRACKETS);
		super.delete(UserPO.class, whereHql);
	}
	public void saveUser(String userId, String loginId, String password, String userName, String nickName, 
			String userType, String orgId){
		/*
		 * 修改密码
		 */
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		String sql = "INSERT INTO IMTI_SYS_USER(FID,FLOGIN_ID,FPASSWORD,FUSER_NICK,FUSER_NAME,FVALID,FLOGIN_TYPE,FORG_ID,FCOMPANY_CODE,USER_TYPE) " + 
					" VALUES(?,?,?,?,?,?,?,?,?,?)";
		String sql2 = "INSERT INTO IMTI_SYS_USER_ROLE(FUSER_ID,FROLE_ID) " + 
		" VALUES(?,?)";
		try {
			conn = session.connection();
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sql2);
			ps.setString(1, userId);
			ps.setString(2, loginId);
			ps.setString(3, password);
			ps.setString(4, nickName);
			ps.setString(5, userName);
			ps.setString(6, "1");//是否有效
			ps.setString(7, "3");//MD5加密
			ps.setString(8, orgId);
			ps.setString(9, "JP_CUSTOMER");
			ps.setString(10, userType);
			
			ps1.setString(1, userId);
			ps1.setString(2, PropertyUtils.getProperty("import_customer_id", "402881572a41b741012a426681880164"));
			ps.execute();
			ps1.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ps != null){
					ps.close();
				}
				if(ps1 != null){
					ps1.close();
				}
				if(conn != null){
					conn.close();
				}
				if(session != null){
					session.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteRole(String[] roleId) {
		/*
		 * 1、删除用户与角色关系
		 * 2、权限关系
		 * 3、删除角色
		 */
		
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Connection conn = null;
		PreparedStatement userRoleRealPs = null;
		PreparedStatement roleRightPs = null;
		String userRoleRealSql = "delete from IMTI_SYS_USER_ROLE where FROLE_ID in" + StringUtil.arrayToSQLString(roleId, StringUtil.SQL_STRING_BRACKETS);
		String roleRightSql = "delete from IMTI_SYS_ROLE_RIGHT where FROLE_ID in" + StringUtil.arrayToSQLString(roleId, StringUtil.SQL_STRING_BRACKETS);
		
		try {
			conn = session.connection();
			//删除用户角色关系
			userRoleRealPs = conn.prepareStatement(userRoleRealSql);
			userRoleRealPs.execute();

			//删除角色权限
			roleRightPs = conn.prepareStatement(roleRightSql);
			roleRightPs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(userRoleRealPs != null){
					userRoleRealPs.close();
				}
				if(roleRightPs != null){
					roleRightPs.close();
				}
				if(conn != null){
					conn.close();
				}
				if(session != null){
					session.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//删除角色本身
		String whereHql = " and po.id in" + StringUtil.arrayToSQLString(roleId, StringUtil.SQL_STRING_BRACKETS);
		super.delete(SysRolePO.class, whereHql);
	}
	
	public List getCmpByPerson(String userId){
		/*
		 * 根据人员获得该人员关联的业务员所在的分公司
		 */
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List orgList = new ArrayList();
		//根据登录人关联的业务员的分公司的查询
	/*	String sql = " select o.* from imti_sys_org o," +
				" (select  distinct(i.bch_company_code) from jp_busi_real r ,jp_user_type_info i " +
				" where  r.busi_id=i.user_id " +
				" and r.user_id=?)t where" +
				" t.bch_company_code=o.forg_code ";*/
		String sql = "SELECT G.* FROM  IMTI_SYS_USER_ORG_REAL R, IMTI_SYS_ORG G WHERE R.ORG_ID=G.FORG_ID AND R.USER_ID=?";
		try {
			conn = session.connection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while(rs.next()){
				OrgVO orgvo = new OrgVO();
				orgvo.setMemo(rs.getString("FMEMO"));
				orgvo.setOrgCode(rs.getString("FORG_CODE"));
				orgvo.setOrgId(rs.getString("FORG_ID"));
				orgvo.setOrgName(rs.getString("FORG_NAME"));
				orgvo.setOrgParentId(rs.getString("FORG_PARENT_ID"));
				orgvo.setType(rs.getString("TYPE"));
				orgList.add(orgvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ps != null){
					ps.close();
				}
				if(conn != null){
					conn.close();
				}
				if(session != null){
					session.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return orgList;
	}
	
}
