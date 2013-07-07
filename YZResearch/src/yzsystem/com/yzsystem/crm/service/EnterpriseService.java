package com.yzsystem.crm.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.crm.common.search.EnterpriseSearch;
import com.yzsystem.crm.vo.EnterpriseVO;

public interface EnterpriseService extends BaseService {

	/**
	 * 查询企业资料
	 * 
	 * @param search
	 * @return
	 * @author 曹刚 新增日期：2013-5-16上午10:34:07
	 * @since 1.0
	 */
	public List<EnterpriseVO> getEnterpriseList(EnterpriseSearch search);

	/**
	 * 统计企业资料数量
	 * 
	 * @param search
	 * @return
	 * @author 曹刚 新增日期：2013-5-16上午10:34:30
	 * @since 1.0
	 */
	public int countEnterprise(EnterpriseSearch search);

}
