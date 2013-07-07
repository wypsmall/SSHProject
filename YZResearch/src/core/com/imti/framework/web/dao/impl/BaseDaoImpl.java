package com.imti.framework.web.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.imti.framework.common.StringUtil;
import com.imti.framework.component.vopomapping.constant.DataType;
import com.imti.framework.component.vopomapping.vomapping.SimpleProperty;
import com.imti.framework.web.dao.BaseDao;
import com.imti.framework.web.log.ExtLog;
import com.imti.framework.web.po.BasePO;
import com.imti.framework.web.po.PkBean;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	private ExtLog log  = new ExtLog(BaseDaoImpl.class);
	
	public void insert(BasePO basePO) {
		getHibernateTemplate().save(basePO);
	}

	public void save(BasePO basePO) {
		getHibernateTemplate().saveOrUpdate(basePO);
	}

	public void update(final BasePO basePO) {
		getHibernateTemplate().merge(basePO);
	}

	private BasePO getBasePO(String className){
		BasePO po = null;
		try {
			po =  (BasePO)Class.forName(className).newInstance();
    	} catch (InstantiationException e) {
         	log.error(e.getMessage());
        } catch (IllegalAccessException e) {
         	log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
         	log.error(e.getMessage());
        }
        return po;
	}

	public int count(Class cla, String whereHql) {
		String hql = "select count(*) from " + 
			cla.getName() +
			" po where 1=1 ";
		if(StringUtils.isNotEmpty(whereHql)){
			hql = hql +  whereHql ;
		}
		List list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size() > 0){
			Long count = (Long) list.get(0);
			return count.intValue();
		}
		return 0;
	}

	public void delete(Class poClass, String[] pks) {
		BasePO po = getBasePO(poClass.getName());
		SimpleProperty sp = po.getPkFields();
		String whereHql = "";
		
		if(DataType.STRING.equals(sp.getPoType()) || DataType.CSTRING.equals(sp.getPoType())){//主键字符串类型
			whereHql = " and po." + sp.getPoProperty() + " in" + StringUtil.arrayToSQLString(pks, StringUtil.SQL_STRING_BRACKETS) ;
		}else {//通常主键类型为字符串或者整形
			whereHql = " and po." + sp.getPoProperty() + " in (" + StringUtil.arrayToString(pks) + ")";
		}
		final String hql = " delete from " + poClass.getName() + " po where 1=1 " + whereHql;
		
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.executeUpdate();
				return null;
			}
		});
	}
	
	public void delete(Class poClass, String whereHql) {
		final String hql = " delete from " + poClass.getName() + " po where 1=1 " + whereHql;
		
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.executeUpdate();
				return null;
			}
		});
	}
	
	
	
	public List find(final Class cla, final int start, final int limit, final String whereHql) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				String hql = " from " + cla.getName() +" po where 1=1 " ;
				if(StringUtils.isNotEmpty(whereHql)){
					hql = hql +  whereHql;
				}
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}

	public List find(final Class cla, final String whereHql) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				String hql = " from " + cla.getName() +" po where 1=1 " ;
				if(StringUtils.isNotEmpty(whereHql)){
					hql = hql +  whereHql;
				}
				Query q = session.createQuery(hql);
				return q.list();
			}
		});
	}
	public BasePO findByPk(PkBean pkBean) {
		Object po = null;
		String pkString = ((getBasePO(pkBean.getPoClass())).getPkFields()).getPoProperty();
		final String hql = "from " + pkBean.getPoClass() + " po" + " where po." + pkString + "=" + pkBean.getPkValues() ;
		po = getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				List list = query.list();
				if(list != null && !list.isEmpty()){
					return (BasePO)list.get(0);
				}
				return null;
			}
		});
		if(po != null){
			return (BasePO)po;
		}
		return null;
	}

	public BasePO get(Class cla, String id) {
		Object po = null;
		po = getHibernateTemplate().get(cla, id);
		if(po != null){
			return (BasePO)po;
		}
		return null;
	}
	public List findByHql(final int start, final int limit, final String hql){
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}
	@SuppressWarnings("unchecked")
	public int countByHql(final String hql){
		List list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException,
					HibernateException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
		if(list != null && list.size() > 0){
			Long count = (Long) list.get(0);
			return count.intValue();
		}
		return 0;
	}
}
