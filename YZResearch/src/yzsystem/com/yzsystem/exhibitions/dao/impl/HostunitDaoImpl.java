package com.yzsystem.exhibitions.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.imti.framework.web.dao.impl.BaseDaoImpl;
import com.yzsystem.exhibitions.dao.HostunitDao;

public class HostunitDaoImpl extends BaseDaoImpl implements HostunitDao {

	private static final long serialVersionUID = 1L;

	
	@Override
	public void deleteHostunit(final String hostUnitId) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = " update com.yzsystem.exhibitions.po.HostunitPO  po  set po.delFlag=0 where po.hostUnitId=?";
				Query query = session.createQuery(hql);
				query.setString(0, hostUnitId);
				query.executeUpdate();
				return null;
			}
		});
	}
}
