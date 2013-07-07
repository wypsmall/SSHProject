package com.yzsystem.tperson.service;

import com.imti.framework.web.service.BaseService;

public interface TPersonService extends BaseService {
	public void updateTPerson(Integer personId, String personName);
}
