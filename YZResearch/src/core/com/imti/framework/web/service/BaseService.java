package com.imti.framework.web.service;

import java.util.List;

import com.imti.framework.web.vo.BaseVO;


public interface BaseService{

	/**
	 * 保存对象
	 * @param vo
	 */
	public void save(BaseVO vo);
	/**
	 * <li>保存对象
	 * @param vo
	 */
	public void insert(BaseVO vo);
	/**
	 * <li>修改--保存对象
	 * @param vo
	 */
	public void update(BaseVO vo);
	/**
	 * <li>查询所有
	 * @param poClass
	 * @return
	 */
	public List getAll(Class poClass);
	/**
	 * <li>根据主键查找对象
	 * @param voClass
	 * @param pk
	 * @return
	 */
	public BaseVO getByPk(Class poClass, String pk);
	/**
	 * <li>根据主键删除对象
	 * @param voClass
	 * @param pks
	 */
	public void deleteByPk(Class voClass, String[] pks);
	
	/**
	 * <li>条件查询不分页
	 * @param cla
	 * @param whereHql
	 * @return
	 */
	public List getByConditionWithNoPage(Class cla, String whereHql ) ;
	/**
	 * <li>条件查询带分页
	 * @param cla
	 * @param startStr
	 * @param limitStr
	 * @param whereHql
	 * @return
	 */
	public List getByCondition(Class cla, String startStr, String limitStr, String whereHql) ;
	
	/**
	 * <li>带条件查询总数
	 * @param cla
	 * @param whereHql
	 * @return
	 * @throws Exception
	 */
	public int getCountByCondition(Class cla, String whereHql);
	public List getByHql( String startStr, String limitStr, String hql);
}
