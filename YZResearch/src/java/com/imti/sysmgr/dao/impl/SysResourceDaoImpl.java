package com.imti.sysmgr.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.imti.framework.web.dao.impl.BaseDaoImpl;
import com.imti.sysmgr.dao.SysResourceDao;
import com.imti.sysmgr.ext.RsBean;

public class SysResourceDaoImpl extends BaseDaoImpl implements SysResourceDao {

	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<RsBean> getRsBeanList(final String parentId) {
		return (List<RsBean>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery("sqlSysRsBean");
				SQLQuery sqlQuery = session.createSQLQuery(query.getQueryString());
				sqlQuery.setResultSetMapping("rsSysRsBean");
				sqlQuery.setResultTransformer(Transformers.aliasToBean(RsBean.class));
				sqlQuery.setString(0, parentId);
				return sqlQuery.list();
			}
		});
	}

}
