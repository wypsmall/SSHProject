package com.imti.framework.web.service;

import java.util.List;

import com.imti.framework.web.vo.BaseVO;


public interface BaseService{

	/**
	 * �������
	 * @param vo
	 */
	public void save(BaseVO vo);
	/**
	 * <li>�������
	 * @param vo
	 */
	public void insert(BaseVO vo);
	/**
	 * <li>�޸�--�������
	 * @param vo
	 */
	public void update(BaseVO vo);
	/**
	 * <li>��ѯ����
	 * @param poClass
	 * @return
	 */
	public List getAll(Class poClass);
	/**
	 * <li>�����������Ҷ���
	 * @param voClass
	 * @param pk
	 * @return
	 */
	public BaseVO getByPk(Class poClass, String pk);
	/**
	 * <li>��������ɾ������
	 * @param voClass
	 * @param pks
	 */
	public void deleteByPk(Class voClass, String[] pks);
	
	/**
	 * <li>������ѯ����ҳ
	 * @param cla
	 * @param whereHql
	 * @return
	 */
	public List getByConditionWithNoPage(Class cla, String whereHql ) ;
	/**
	 * <li>������ѯ����ҳ
	 * @param cla
	 * @param startStr
	 * @param limitStr
	 * @param whereHql
	 * @return
	 */
	public List getByCondition(Class cla, String startStr, String limitStr, String whereHql) ;
	
	/**
	 * <li>��������ѯ����
	 * @param cla
	 * @param whereHql
	 * @return
	 * @throws Exception
	 */
	public int getCountByCondition(Class cla, String whereHql);
	public List getByHql( String startStr, String limitStr, String hql);
}
