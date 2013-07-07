package com.yzsystem.exhibitions.dao;

import java.util.List;

import com.imti.framework.web.dao.BaseDao;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.vo.ExhProModuleVO;

public interface ExhProDao extends BaseDao {

	public List<ExhProModuleVO> getExhProAdvList(ExhProSearch search);

	public void submitExhProModule(String exhProMododuleId);

}
