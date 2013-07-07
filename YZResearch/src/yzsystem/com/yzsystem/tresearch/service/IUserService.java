package com.yzsystem.tresearch.service;

import java.util.List;

import com.neo.common.service.IBaseService;
import com.neo.common.vo.IValueObject;
import com.yzsystem.tresearch.po.UserDO;

public interface IUserService extends IBaseService {
	public IValueObject getDomainObjectPageJson(Object[] param, Integer start, Integer pagesize);
	public List<IValueObject> getUserNoPage(UserDO userDO);
}
