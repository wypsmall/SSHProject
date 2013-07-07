package com.neo.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.neo.common.dao.IAirBaseDao;
import com.neo.common.model.GridJsonPageData;
import com.neo.common.model.IDomainObject;
import com.neo.common.service.IBaseService;
import com.neo.common.transfer.BaseTransfer;
import com.neo.common.vo.IValueObject;

public class BaseServiceImpl implements IBaseService {
	protected BaseTransfer baseTransfer;
	private IAirBaseDao airBaseDao;
	
	public void setBaseTransfer(BaseTransfer baseTransfer) {
		this.baseTransfer = baseTransfer;
	}

	public void setAirBaseDao(IAirBaseDao airBaseDao) {
		this.airBaseDao = airBaseDao;
	}

	@Override
	public void addDomainObject(IDomainObject object, String strPK,String strRepeat) {
		airBaseDao.add(object, strPK, strRepeat);
	}

	@Override
	public void deleteByJsonArray(JSONArray array, Class cls, String strPK) {
		for (Object object : array) {
			JSONObject jsonobj = (JSONObject)object;
			Integer id = (Integer)jsonobj.get(strPK);
			airBaseDao.deleteById(cls, strPK, id);
		}
	}

	@Override
	public IValueObject getAllDomainObject(Class docls, Class vocls,
			boolean isQueryCache) {
		List<IDomainObject> res = airBaseDao.getAllDomainObject(docls, isQueryCache);
		List<IValueObject> vos = baseTransfer.getVOList(vocls, res);
		if(vos == null)
			vos = new ArrayList<IValueObject>();
		return new GridJsonPageData<IValueObject>(vos.size(),vos);
	}

	@Override
	public IValueObject getDataByField(Class docls, Class vocls,
			String strField, String value, boolean isQueryCache) {
		IDomainObject doobj = airBaseDao.getDataByField(docls, strField, value, isQueryCache);
		IValueObject voobj = (IValueObject) baseTransfer.getVO(vocls, doobj);
		return voobj;
	}

	@Override
	public IValueObject getDomainObjectById(Class docls, Class vocls,
			String strPK, Integer id, boolean isQueryCache) {
		IDomainObject doobj = airBaseDao.getDataById(docls, strPK, id, isQueryCache);
		IValueObject voobj = (IValueObject) baseTransfer.getVO(vocls, doobj);
		return voobj;
	}

	@Override
	public IValueObject getDomainObjectPageJson(Class docls, Class vocls, String orderField,
			Integer start, Integer pagesize, boolean isQueryCache) {
		List<IDomainObject> res = airBaseDao.getDataPage(docls, orderField, start, pagesize, isQueryCache);
		List<IValueObject> vos = baseTransfer.getVOList(vocls, res);
		if(vos == null)
			vos = new ArrayList<IValueObject>();
		int total = airBaseDao.getTotalCount(docls);
		GridJsonPageData<IValueObject> data = new GridJsonPageData<IValueObject>();
		data.setTotalCount(total);
		data.setResults(vos);
		return data;
	}

	@Override
	public void modifyDomainObject(IDomainObject object) {
		airBaseDao.modify(object);
	}

}
