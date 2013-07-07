package com.imti.sysmgr.service.impl;

import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.imti.sysmgr.dao.WarnDao;
import com.imti.sysmgr.service.WarnService;

public class WarnServiceImpl extends BaseServiceImpl implements WarnService {

	@Override
	public WarnDao getBaseDao() {
		return (WarnDao)SpringBeanUtil.getBean("warnDao");
	}

}
