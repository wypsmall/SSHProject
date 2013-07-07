package com.yzsystem.tperson.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.imti.framework.web.dao.impl.BaseDaoImpl;
import com.yzsystem.tperson.dao.TPersonDao;
import com.yzsystem.tperson.po.TPersonPO;

public class TPersonDaoImpl extends BaseDaoImpl implements TPersonDao{

	@Override
	public void updateTPerson(final TPersonPO personPO) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String updateHQL = " update TPersonPO po set po.personName='" + personPO.getPersonName() + "' " + 
									" where po.personId=?" ;
				Query query = session.createQuery(updateHQL);
				query.setInteger(0, personPO.getPersonId());
				query.executeUpdate();
				return null;
			}
		});
	}

}
