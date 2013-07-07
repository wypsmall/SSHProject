package com.yzsystem.exhibitions.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.imti.framework.web.dao.impl.BaseDaoImpl;
import com.yzsystem.common.constant.StatusConstant;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.dao.ExhProDao;
import com.yzsystem.exhibitions.vo.ExhProModuleVO;

public class ExhProDaoImpl extends BaseDaoImpl implements ExhProDao {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhProModuleVO> getExhProAdvList(final ExhProSearch search) {
		return (List<ExhProModuleVO>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(session.getNamedQuery("sqlExhProModule").getQueryString());
				sqlQuery.setInteger(0, search.getExhProId());
				sqlQuery.setString(1, search.getExhProModuleCode());
				sqlQuery.setResultSetMapping("rsExhProModule");
				sqlQuery.setResultTransformer(Transformers.aliasToBean(ExhProModuleVO.class));
				return sqlQuery.list();
			}
		});
	}
	@Override
	public void submitExhProModule(final String exhProMododuleId) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String updateHQL = " update com.yzsystem.exhibitions.po.ExhProModulePO po set po.lockStatus=" + StatusConstant.LOCK_STATUS_YES +
									" where po.exhProModuleId=?" ;
				Query query = session.createQuery(updateHQL);
				query.setInteger(0, Integer.valueOf(exhProMododuleId));
				query.executeUpdate();
				return null;
			}
		});
	}
}
