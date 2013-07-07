package com.yzsystem.tresearch.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.neo.common.dao.impl.AirBaseDaoImpl;
import com.neo.common.exception.BaseException;
import com.neo.common.vo.IValueObject;
import com.yzsystem.tresearch.dao.IUserDao;
import com.yzsystem.tresearch.po.UserDO;

public class UserDaoImpl extends AirBaseDaoImpl implements IUserDao {

	@Override
	public List<IValueObject> getDataPage(Object[] param, Integer start, Integer pagesize) {
		try {
			Query query = getSession().getNamedQuery("sqlGetUserPageVO");
			StringBuffer buf = new StringBuffer(query.getQueryString());
			buf.append(getWhereByParam(param));
			buf.append(" order by tu.fid");
			SQLQuery sqlQuery = getSession().createSQLQuery(buf.toString());
			sqlQuery.setResultSetMapping("rsUserPageVO");
			sqlQuery.setResultTransformer(Transformers.aliasToBean(UserDO.class));
			sqlQuery.setFirstResult(start);
			sqlQuery.setMaxResults(pagesize);
			return sqlQuery.list();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		} 
		return null;
	}

	@Override
	public Integer getTotalCount(Object[] param) {
		List res = null;
		try {
			Query query = getSession().getNamedQuery("sqlGetUserPageVO");
			StringBuffer buf = new StringBuffer(query.getQueryString());
			buf.append(getWhereByParam(param));
			String sqlcount = "select count(*) from ( "+buf.toString()+" ) T";
			SQLQuery sqlQuery = getSession().createSQLQuery(sqlcount);
			res = sqlQuery.list();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		if(res == null || res.size()==0)
			return 0;
		else
			return Integer.valueOf(res.get(0).toString());
	}

	private String getWhereByParam(Object[] param) {
		String fid = (String)param[0];

		StringBuffer buf = new StringBuffer();
		if(fid!=null && fid.length()>0)
			buf.append(" tu.fid!='"+fid+"' and");
		buf.append(" 1=1");
		
		return buf.toString();
	}

	private String getWhereSql(UserDO userDO) {
		StringBuffer buf = new StringBuffer();
		if(userDO.getFuserName()!=null && userDO.getFuserName().length()>0)
			buf.append(" tu.fuser_name like '%"+userDO.getFuserName()+"%' and");
		buf.append(" 1=1");
		
		return buf.toString();
	}
	@Override
	public List<IValueObject> getDataNoPage(UserDO userDO) {
		try {
			Query query = getSession().getNamedQuery("sqlGetUserPageVO");
			StringBuffer buf = new StringBuffer(query.getQueryString());
			buf.append(getWhereSql(userDO));
			buf.append(" order by tu.fid");
			SQLQuery sqlQuery = getSession().createSQLQuery(buf.toString());
			sqlQuery.setResultSetMapping("rsUserPageVO");
			sqlQuery.setResultTransformer(Transformers.aliasToBean(UserDO.class));
			return sqlQuery.list();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		} 
		return null;
	}
}
