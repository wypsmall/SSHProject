package com.yzsystem.exhibitions.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.vo.ExhibitionEnterpriseVO;

public interface ExhProjEnterpriseService extends BaseService {

	/**
	 * 查找参展企业资料
	 * 
	 * @param search
	 * @return
	 * @author 曹刚 新增日期：2013-5-16上午11:22:01
	 * @since 1.0
	 */
	public List<ExhibitionEnterpriseVO> getExhEnterpriseList(ExhProSearch search);

	/**
	 * 统计参展企业数量
	 * 
	 * @param search
	 * @return
	 * @author 曹刚 新增日期：2013-5-16上午11:22:26
	 * @since 1.0
	 */
	public int countExhEnterprise(ExhProSearch search);

}
