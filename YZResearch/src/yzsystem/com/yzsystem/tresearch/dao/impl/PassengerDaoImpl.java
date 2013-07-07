package com.yzsystem.tresearch.dao.impl;

import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.imti.framework.web.dao.impl.BaseDaoImpl;
import com.yzsystem.tresearch.dao.PassengerDao;
import com.yzsystem.tresearch.vo.PassengerVO;

public class PassengerDaoImpl extends BaseDaoImpl implements PassengerDao{

	@Override
	public void batchSaveRoomNo(String jsonData, final String roomNo) {
		JSONArray array = JSONArray.fromObject(jsonData);
		StringBuffer sb = new StringBuffer();
		if (null != array && array.size() > 0) {
			sb.append("(");
			for (Object object : array) {
				JSONObject data = (JSONObject)object;
				sb.append(" ").append(data.getInt("uid")).append(",");
			}
			sb = sb.delete(sb.length()-1, sb.length());
			sb.append(")");
		}
		final String wheresql = sb.toString();
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String updateHQL = " update PassengerPO po set po.uroom='" + roomNo + "' " + 
									" where po.uid in " + wheresql;
				Query query = session.createQuery(updateHQL);
				query.executeUpdate();
				return null;
			}
		});
		
	}

}
