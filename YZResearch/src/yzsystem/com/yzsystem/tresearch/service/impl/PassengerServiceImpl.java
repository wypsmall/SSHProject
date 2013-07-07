package com.yzsystem.tresearch.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.imti.framework.web.dao.BaseDao;
import com.imti.framework.web.environment.utils.SpringBeanUtil;
import com.imti.framework.web.service.impl.BaseServiceImpl;
import com.yzsystem.tresearch.dao.PassengerDao;
import com.yzsystem.tresearch.service.PassengerService;
import com.yzsystem.tresearch.vo.PassengerVO;

public class PassengerServiceImpl extends BaseServiceImpl implements PassengerService {

	@Override
	public PassengerDao getBaseDao() {
		return (PassengerDao)SpringBeanUtil.getBean("passengerDao");
	}

	@Override
	public void saveRoom(String jsonData) {
		JSONArray array = JSONArray.fromObject(jsonData);
		if (null != array && array.size() > 0) {
			for (Object object : array) {
				PassengerVO vo = new PassengerVO();
				JSONObject data = (JSONObject)object;
				vo.setUid(data.getInt("uid"));
				vo.setUname(data.getString("uname"));
				vo.setUsex(data.getInt("usex"));
				vo.setUcompany(data.getString("ucompany"));
				vo.setUroom(data.getString("uroom"));
				update(vo);
			}
		}
	}

	@Override
	public void batchSaveRoomNo(String jsonData, String roomNo) {
		getBaseDao().batchSaveRoomNo(jsonData, roomNo);
	}

}
