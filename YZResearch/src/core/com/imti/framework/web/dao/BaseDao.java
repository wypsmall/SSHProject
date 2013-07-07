package com.imti.framework.web.dao;

import java.io.Serializable;
import java.util.List;

import com.imti.framework.web.po.BasePO;
import com.imti.framework.web.po.PkBean;

public interface BaseDao extends Serializable {
	public BasePO findByPk(PkBean pkBean);
	public void insert(BasePO basePO) ;
	public void save(BasePO basePO);
	public void update(BasePO basePO) ;
	public List find(Class cla, int start, int limit, String whereHql);
	public List find(Class cla, String whereHql);
	public int count(Class cla, String whereHql);
	public void delete(Class poClass, String[] pks);
	public void delete(Class poClass, String whereHql);
	public BasePO get(Class cla, String id);
	public List findByHql(int start, int limit, String hql);
	public int countByHql(String hql);
}
