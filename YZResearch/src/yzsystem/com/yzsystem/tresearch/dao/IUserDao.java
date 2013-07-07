package com.yzsystem.tresearch.dao;

import java.util.List;

import com.neo.common.dao.IAirBaseDao;
import com.neo.common.vo.IValueObject;
import com.yzsystem.tresearch.po.UserDO;

public interface IUserDao extends IAirBaseDao {
	public List<IValueObject> getDataPage(Object[] param, Integer start,Integer pagesize);
	public Integer getTotalCount(Object[] param);
	public List<IValueObject> getDataNoPage(UserDO userDO);
}
