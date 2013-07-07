package com.yzsystem.tperson.dao;

import com.imti.framework.web.dao.BaseDao;
import com.yzsystem.tperson.po.TPersonPO;

public interface TPersonDao extends BaseDao {
	public void updateTPerson(TPersonPO personPO);
}
