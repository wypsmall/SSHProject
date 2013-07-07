package com.yzsystem.tresearch.dao;

import com.imti.framework.web.dao.BaseDao;

public interface PassengerDao extends BaseDao {
	public void batchSaveRoomNo(String jsonData, String roomNo);
}
