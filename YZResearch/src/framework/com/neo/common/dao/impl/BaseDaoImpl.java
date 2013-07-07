package com.neo.common.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.neo.common.dao.IBaseDao;


public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	public Integer add(T data) {
		getHibernateTemplate().saveOrUpdate(data);
		return null;
	}

	public boolean deleteById(Integer id) {
		return false;
	}

	public T getDataById(Integer id) {
		return null;
	}

	public List<T> getDataPage(Integer start, Integer pagesize) {
		return null;
	}

	public Integer getTotalCount() {
		return null;
	}

	public void modify(T data) {
		getHibernateTemplate().saveOrUpdate(data);
	}

	
}
