package com.neo.common.service;

import net.sf.json.JSONArray;

import com.neo.common.model.IDomainObject;
import com.neo.common.vo.IValueObject;

public interface IBaseService {
	public void addDomainObject(IDomainObject object,String strPK,String strRepeat);
	public void deleteByJsonArray(JSONArray array, Class cls, String strPK);
	public void modifyDomainObject(IDomainObject object);
	public IValueObject getDomainObjectPageJson(Class docls,Class vocls, String orderField, Integer start,Integer pagesize, boolean isQueryCache);
	public IValueObject getDomainObjectById(Class docls,Class vocls, String strPK, Integer id, boolean isQueryCache);
	public IValueObject getDataByField(Class docls,Class vocls, String strField, String value, boolean isQueryCache);
	public IValueObject getAllDomainObject(Class docls,Class vocls, boolean isQueryCache);
}
