package com.yzsystem.tperson.service.impl;

import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.yzsystem.tperson.dao.TPersonDao;
import com.yzsystem.tperson.po.TPersonPO;
import com.yzsystem.tperson.service.TPersonService;

public class TPersonServiceImpl extends BaseServiceImpl implements
		TPersonService {

	@Override
	public TPersonDao getBaseDao() {
		return (TPersonDao)SpringBeanUtil.getBean("tpersonDao");
	}

	@Override
	public void updateTPerson(Integer personId, String personName) {
		TPersonPO personPO = new TPersonPO();
		personPO.setPersonId(personId);
		personPO.setPersonName(personName);
		getBaseDao().updateTPerson(personPO);
	}

}
