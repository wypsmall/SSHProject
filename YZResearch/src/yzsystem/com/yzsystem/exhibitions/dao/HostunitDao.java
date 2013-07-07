package com.yzsystem.exhibitions.dao;

import com.imti.framework.web.dao.BaseDao;

public interface HostunitDao extends BaseDao {

	/**
	 * 标记主办单位为“已删除”
	 * 修改制定字段的方式来降低对数据库表的多列更新操作
	 * @param hostUnitId
	 * @author 曹刚 新增日期：2013-4-17上午10:09:00
	 * @since 1.0
	 */
	public void deleteHostunit(String hostUnitId);

}
