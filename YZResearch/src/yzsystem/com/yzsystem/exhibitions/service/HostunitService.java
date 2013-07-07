package com.yzsystem.exhibitions.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.exhibitions.common.search.HostunitSearch;
import com.yzsystem.exhibitions.vo.HostunitVO;

public interface HostunitService extends BaseService {

	/**
	 * 新增主办单位
	 * 
	 * @param hostunit
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-17上午09:28:03
	 * @since 1.0
	 */
	public void insetHostunit(HostunitVO hostunit);

	/**
	 * 修改主办单位资料
	 * 
	 * @param hostunit
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-17上午09:28:23
	 * @since 1.0
	 */
	public void updateHostunit(HostunitVO hostunit);

	/**
	 * 删除主办单位
	 * 
	 * @param hostUnitId
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-17上午09:50:55
	 * @since 1.0
	 */
	public void deleteHostunit(String hostUnitId);

	/**
	 * 根据条件获取“未删除”的主办单位
	 * 
	 * @param search
	 * @return
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-17上午09:53:43
	 * @since 1.0
	 */
	public List<HostunitVO> getValidHostunit(HostunitSearch search);

	/**
	 * 根据条件统计“未删除”的主办单位
	 * 
	 * @param search
	 * @return
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-4-17上午09:54:27
	 * @since 1.0
	 */
	public int countValidHostunit(HostunitSearch search);

}
