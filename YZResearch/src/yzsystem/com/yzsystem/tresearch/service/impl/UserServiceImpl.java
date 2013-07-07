package com.yzsystem.tresearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.neo.common.model.GridJsonPageData;
import com.neo.common.service.impl.BaseServiceImpl;
import com.neo.common.vo.IValueObject;
import com.yzsystem.tresearch.dao.IUserDao;
import com.yzsystem.tresearch.po.UserDO;
import com.yzsystem.tresearch.service.IUserService;

public class UserServiceImpl extends BaseServiceImpl implements IUserService {
	private IUserDao userDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public IValueObject getDomainObjectPageJson(Object[] param, Integer start, Integer pagesize) {
		List<IValueObject> vos = userDao.getDataPage(param, start, pagesize);
		if(vos == null)
			vos = new ArrayList<IValueObject>();
		int total = userDao.getTotalCount(param);
		GridJsonPageData<IValueObject> data = new GridJsonPageData<IValueObject>();
		data.setTotalCount(total);
		data.setResults(vos);
		return data;
	}

	@Override
	public List<IValueObject> getUserNoPage(UserDO userDO) {
		return userDao.getDataNoPage(userDO);
	}
	
}
