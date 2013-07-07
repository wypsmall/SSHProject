package com.imti.sysmgr.dao;

import java.util.List;

import com.imti.framework.web.dao.BaseDao;
import com.imti.sysmgr.ext.RsBean;

public interface SysResourceDao extends BaseDao {

	public List<RsBean> getRsBeanList(String parentId);

}
