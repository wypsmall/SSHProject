package com.yzsystem.exhibitions.service;

import java.util.List;

import com.imti.framework.web.service.BaseService;
import com.yzsystem.exhibitions.common.search.ExhProSearch;
import com.yzsystem.exhibitions.vo.ExhProModuleVO;
import com.yzsystem.exhibitions.vo.ExhibitionProjectVO;

public interface ExhProService extends BaseService {

	/**
	 * 新增会展项目（提供给同步模块调用）
	 * @param exhibitionProject
	 */
	public void insertExhPro(ExhibitionProjectVO exhibitionProject);
	/**
	 * 修改会展项目
	 * @param exhibitionProject
	 */
	public void updateExhPro(ExhibitionProjectVO exhibitionProject);
	/**
	 * 展会列表
	 * 
	 * @param search
	 * @return
	 * @author 曹刚 新增日期：2013-5-14下午01:34:45
	 * @since 1.0
	 */
	public List<ExhibitionProjectVO> getExhProList(ExhProSearch search);
	/**
	 * 展会统计
	 * 
	 * @param search
	 * @return
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-5-14下午01:34:54
	 * @since 1.0
	 */
	public int countExhPro(ExhProSearch search);
	/**
	 * 设置会展项目模板
	 * 
	 * @param hostunit
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-5-14下午05:25:07
	 * @since 1.0
	 */
	public void saveExhProModule(ExhProModuleVO mod);
	/**
	 * 获取展会项目对应的翻译模板
	 * @param search
	 * @return
	 */
	public List<ExhProModuleVO> getExhProTransList(ExhProSearch search);
	/**
	 * 获取广告模板列表
	 * @param search
	 * @return
	 */
	public List<ExhProModuleVO> getExhProAdvList(ExhProSearch search);
	/**
	 * 删除模板
	 * 
	 * @param exhProMododuleId
	 * @author 曹刚 新增日期：2013-5-15上午10:51:37
	 * @since 1.0
	 */
	public void deleteExhProModule(String exhProModuleId);
	/**
	 * 提交模板
	 * 
	 * @param exhProMododuleId
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-5-15上午11:06:23
	 * @since 1.0
	 */
	public void submitExhProModule(String exhProModuleId);
}
