package com.yzsystem.exhibitions.dao;

import com.imti.framework.web.dao.BaseDao;

public interface HostunitDao extends BaseDao {

	/**
	 * ������쵥λΪ����ɾ����
	 * �޸��ƶ��ֶεķ�ʽ�����Ͷ����ݿ��Ķ��и��²���
	 * @param hostUnitId
	 * @author �ܸ� �������ڣ�2013-4-17����10:09:00
	 * @since 1.0
	 */
	public void deleteHostunit(String hostUnitId);

}
