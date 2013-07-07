package com.yzsystem.tresearch.service;

import com.imti.framework.web.service.BaseService;

public interface PassengerService extends BaseService {
	public void saveRoom(String jsonData);
	public void batchSaveRoomNo(String jsonData, String roomNo);
}
