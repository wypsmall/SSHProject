package com.neo.common.dao;

import java.util.List;

import com.neo.common.model.IDomainObject;

public interface IAirBaseDao extends IBaseDao<IDomainObject> {
	public Integer add(IDomainObject data,String strPK,String strRepeat);
	public boolean deleteById(Class cls, String strPK, Integer id);
	public IDomainObject getDataById(Class cls, String strPK, Integer id, boolean isQueryCache);
	public IDomainObject getDataByField(Class cls, String strField, String value, boolean isQueryCache);
	public Integer getTotalCount(Class cls);
	public List<IDomainObject> getDataPage(Class cls,String orderField,Integer start,Integer pagesize, boolean isQueryCache);
	public List<IDomainObject> getAllDomainObject(Class cls, boolean isQueryCache);
	//add by huangtao 2011-04-20
	public boolean isNotUnique(Object entity, String names);
}
