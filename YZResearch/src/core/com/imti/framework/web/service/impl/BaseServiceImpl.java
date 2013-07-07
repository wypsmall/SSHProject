package com.imti.framework.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.imti.framework.common.BeanUtil;
import com.imti.framework.common.ImtiAssert;
import com.imti.framework.common.ImtiListUtil;
import com.imti.framework.common.StringUtils;
import com.imti.framework.component.vopomapping.utils.POUtil;
import com.imti.framework.component.vopomapping.utils.PropertyUtils;
import com.imti.framework.web.dao.BaseDao;
import com.imti.framework.web.po.BasePO;
import com.imti.framework.web.po.PkBean;
import com.imti.framework.web.service.BaseService;
import com.imti.framework.web.vo.BaseVO;

public abstract class BaseServiceImpl implements BaseService {

	public abstract BaseDao getBaseDao();

	public void deleteByPk(Class poClass, String[] pks) {
		ImtiAssert.isNotBlank(poClass, "查询对象不允许为null");
		ImtiAssert.isArrayNotBlank(pks, "主键不允许为null");
		
		getBaseDao().delete(poClass, pks);
	}

	public BaseVO getByPk(Class poClass, String pk) {
		ImtiAssert.isNotBlank(poClass, "查询对象不允许为null");
		ImtiAssert.isNotBlank(pk, "主键不允许为null");
		
		PkBean pkBean = PkBean.newInstance(poClass, new String[]{pk});
		BasePO po = (BasePO)getBaseDao().findByPk(pkBean);
		if(po != null){
			return (BaseVO)POUtil.copyPoToVO(po);
		}
		return null;
	}

	public void insert(BaseVO vo) {
		ImtiAssert.isNotBlank(vo, "保存对象不允许为null");
		
		BasePO po = (BasePO)POUtil.copyVoToPo(vo);
		getBaseDao().insert(po);
		Object object = POUtil.copyPoToVO(po);
		String pkField = BeanUtil.getPkField(vo);
		ImtiListUtil.setProperty(vo, pkField, ImtiListUtil.getProperty(object, pkField));
	}

	public void save(BaseVO vo) {
		ImtiAssert.isNotBlank(vo, "保存对象不允许为null");
		
		BasePO po = (BasePO)POUtil.copyVoToPo(vo);
		getBaseDao().save(po);
		vo = (BaseVO)POUtil.copyPoToVO(po);
	}

	public void update(BaseVO vo) {
		ImtiAssert.isNotBlank(vo, "保存对象不允许为null");
		
		BasePO po = (BasePO)POUtil.copyVoToPo(vo);
		getBaseDao().update(po);
	}

	public List getAll(Class poClass) {
		return getByConditionWithNoPage(poClass, "");
	}

	public List getByCondition(Class cla, String startStr, String limitStr, String whereHql) {
		ImtiAssert.isNotBlank(cla, "查询对象不允许为null");
		
		int start = 0;
		int limit = PropertyUtils.getIntProperty("imti.page.record", 20);
		if(StringUtils.isNotEmpty(startStr)){
			start = Integer.parseInt(startStr);
		}
		if(StringUtils.isNotEmpty(limitStr)){
			limit = Integer.parseInt(limitStr);
		}
		List poList = getBaseDao().find(cla, start, limit, whereHql);
		if(ImtiListUtil.isNotEmpty(poList)){
			return POUtil.copyPoListToVoList(poList);
		}
		return new ArrayList();
	}
	public List getByHql( String startStr, String limitStr, String hql){
		ImtiAssert.isNotBlank(hql, "查询对象不允许为null");
		int start = 0;
		int limit = PropertyUtils.getIntProperty("imti.page.record", 20);
		if(StringUtils.isNotEmpty(startStr)){
			start = Integer.parseInt(startStr);
		}
		if(StringUtils.isNotEmpty(limitStr)){
			limit = Integer.parseInt(limitStr);
		}
		List poList = getBaseDao().findByHql(start, limit, hql);
		if(ImtiListUtil.isNotEmpty(poList)){
			return POUtil.copyPoListToVoList(poList);
		}
		return new ArrayList();
	}
	public List getByConditionWithNoPage(Class cla, String whereHql) {
		ImtiAssert.isNotBlank(cla, "查询对象不允许为null");
		
		List poList = getBaseDao().find(cla, whereHql);
		if(ImtiListUtil.isNotEmpty(poList)){
			return POUtil.copyPoListToVoList(poList);
		}
		return new ArrayList();
	}

	public int getCountByCondition(Class cla, String whereHql) {
		ImtiAssert.isNotBlank(cla, "查询对象不允许为null");
		
		return getBaseDao().count(cla, whereHql);
	}
}
