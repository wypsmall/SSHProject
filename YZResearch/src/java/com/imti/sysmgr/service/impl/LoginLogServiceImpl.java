package com.imti.sysmgr.service.impl;

import com.imti.framework.web.dao.BaseDao;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.imti.sysmgr.service.LoginLogService;

public class LoginLogServiceImpl extends BaseServiceImpl implements
		LoginLogService {

	
	public BaseDao getBaseDao() {
		return (BaseDao)SpringBeanUtil.getBean("loginLogDao");
	}

}
