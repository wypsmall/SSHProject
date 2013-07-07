package com.neo.common.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import com.neo.common.dao.IAirBaseDao;
import com.neo.common.exception.BaseException;
import com.neo.common.model.IDomainObject;
public class AirBaseDaoImpl extends BaseDaoImpl<IDomainObject> implements
		IAirBaseDao {

	  /**
     * 判断对象某列的�?在数据库中不存在重复
     *
     * @param names 在POJO里相对应的属性名,列组合时以�?号分�?br>
     *              �?name,loginid,password"
     */
    public boolean isNotUnique(Object entity, String names) {
        Assert.hasText(names);
        Criteria criteria = getSession().createCriteria(entity.getClass()).setProjection(Projections.rowCount());
        String[] nameList = names.split(",");
        try {
            for (String name : nameList) {
                criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
            }

            String keyName = getSessionFactory().getClassMetadata(entity.getClass()).getIdentifierPropertyName();
            if (keyName != null) {
                Object id = PropertyUtils.getProperty(entity, keyName);
                //如果是update,排除自身
                if (id != null)
                    criteria.add(Restrictions.not(Restrictions.eq(keyName, id)));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return ((Integer) criteria.uniqueResult()) > 0;
    }


	@Override
	public Integer add(IDomainObject data, String strPK, String strRepeat) {
		try {
			if(strRepeat != null && strRepeat.length() > 0) {
				String className = data.getClass().getName();
				Object rvValue = getProperty(data, strRepeat);
				StringBuffer buf = new StringBuffer("From "+className+" T WHERE T."+strRepeat+"='"+rvValue+"'");
				Query queryObject = getSession().createQuery(buf.toString());
				List res = queryObject.list();
				if(res!=null && res.size()>=1)
					BaseException.ThrowException("AirBaseDaoImpl add Error! ["+strRepeat+" field repeat! "+rvValue+"]");
			}
			this.getHibernateTemplate().save(data);
			return (Integer)getProperty(data,strPK);
		} catch (Exception e) {
			e.printStackTrace();
			BaseException.ThrowException(e.getMessage());
		} 
		return null;
	}
	@Override
	public boolean deleteById(Class cls, String strPK, Integer id) {
		try {
			String className = cls.getName();
			StringBuffer buf = new StringBuffer("DELETE FROM "+className+" T WHERE T."+strPK+"="+id);
			Query queryObject = getSession().createQuery(buf.toString());
			queryObject.executeUpdate();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		return true;
	}

	@Override
	public IDomainObject getDataById(Class cls, String strPK, Integer id, boolean isQueryCache) {
		try {
			String className = cls.getName();
			StringBuffer buf = new StringBuffer("FROM "+className+" T WHERE T."+strPK+"="+id);
			Query queryObject = getSession().createQuery(buf.toString());
			queryObject.setCacheable(isQueryCache);
			return (IDomainObject)queryObject.list().get(0);
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		return null;
	}

	
	@Override
	public void modify(IDomainObject data) {
		try {
			getHibernateTemplate().saveOrUpdate(data);
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
	}
	
	@Override
	public Integer getTotalCount(Class cls) {
		List res = null;
		try {
			String className = cls.getName();
			StringBuffer buf = new StringBuffer("SELECT COUNT(*) FROM "+className);
			Query queryObject = getSession().createQuery(buf.toString());
			res = queryObject.list();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		if(res == null || res.size()==0)
			return 0;
		else
			return Integer.valueOf(res.get(0).toString());
	}
	
	@Override
	public List<IDomainObject> getDataPage(Class cls,String orderField,Integer start, Integer pagesize, boolean isQueryCache) {
		try {
			String className = cls.getName();
			StringBuffer buf = new StringBuffer("FROM "+className+" ORDER BY "+orderField + " desc");
			Query queryObject = getSession().createQuery(buf.toString());
			queryObject.setFirstResult(start);
			queryObject.setMaxResults(pagesize);
			queryObject.setCacheable(isQueryCache);
			return queryObject.list();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		return null;
	}

	@Override
	public List<IDomainObject> getAllDomainObject(Class cls, boolean isQueryCache) {
		try {
			String className = cls.getName();
			StringBuffer buf = new StringBuffer("FROM "+className);
			Query queryObject = getSession().createQuery(buf.toString());
			queryObject.setCacheable(isQueryCache);
			return queryObject.list();
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		return null;
	}
	
	
	private Object getProperty(Object obj,String fieldName) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class cls = obj.getClass();
		String firstChar = fieldName.substring(0,1).toUpperCase();
		String getMethodName = "get" + firstChar + fieldName.substring(1);
		Method getMethod = cls.getDeclaredMethod(getMethodName, new Class[]{});
		return getMethod.invoke(obj, new Object[]{});
	}
	@Override
	public IDomainObject getDataByField(Class cls, String strField, String value, boolean isQueryCache) {
		try {
			String className = cls.getName();
			StringBuffer buf = new StringBuffer("FROM "+className+" T WHERE T."+strField+"='"+value+"'");
			Query queryObject = getSession().createQuery(buf.toString());
			queryObject.setCacheable(isQueryCache);
			return (IDomainObject)queryObject.list().get(0);
		} catch (Exception e) {
			BaseException.ThrowException(e.getMessage());
		}
		return null;
	}
}
