package com.neo.common.transfer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 功能描述:
 * <p>
 * 版权�?��：中企动�?
 * <p>
 * 未经本公司许可，不得以任何方式复制或使用本程序任何部�?
 *
 * @author 王云�?新增日期�?008-7-15
 * @author 王云�?修改日期�?008-7-15
 */
// @SuppressWarnings("unchecked")
public class BaseTransfer {

/*	private static BaseTransfer trans= null;

	*//**
	* 方法用�?和描�? 创建单例
	* <p>方法的实现�?辑描�?
	* @return
	* @author huangtao 新增日期�?008-8-1
	* @author huangtao 修改日期�?008-8-1
	 *//*
	public static BaseTransfer getBaseTransfer(){
		if(trans == null){
			trans = new BaseTransfer();
		}
		return trans;
	}*/
	/**
	 * 方法用�?和描�? 单表单条记录的DO转VO
	 * <p>
	 * 方法的实现�?辑描�?
	 *
	 * @param voClassName
	 *            vo�?
	 * @param domainObjects
	 *            do对象�?
	 * @return
	 * @author huangtao 新增日期�?008-7-18
	 * @author huangtao 修改日期�?008-7-18
	 */
	public Object getVO(Class voClassName, Object... domainObjects) {
		return getObject(voClassName, domainObjects);
	}

	/**
	 * 方法用�?和描�? 单表单条记录的VO转DO
	 * <p>
	 * 方法的实现�?辑描�?
	 *
	 * @param domainObjectClassName
	 * @param viewObject
	 * @return
	 * @author huangtao 新增日期�?008-7-18
	 * @author huangtao 修改日期�?008-7-18
	 */
	public Object getDO(Class domainObjectClassName, Object... viewObject) {
		return getObject(domainObjectClassName, viewObject);
	}

	/**
	 * 方法用�?和描�? 单表多条记录的VO集合转DO集合
	 * <p>
	 * 方法的实现�?辑描�?
	 *
	 * @param voList
	 * @return
	 * @author huangtao 新增日期�?008-7-18
	 * @author huangtao 修改日期�?008-7-18
	 */
	public List getDOList(Class domainObjectClassName, List voList) {
		return getList(domainObjectClassName, voList);
	}

	/**
	 * 方法用�?和描�? 单表多条记录的DO转VO集合
	 * <p>
	 * 方法的实现�?辑描�?
	 *
	 * @param voClassName
	 * @param doList
	 * @return
	 * @author huangtao 新增日期�?008-7-18
	 * @author huangtao 修改日期�?008-7-18
	 */
	public List getVOList(Class voClassName, List doList) {
		return getList(voClassName, doList);

	}

	private List getList(Class className, List list) {
		if(nullList(list)){
			return null;
		}
		List templist = new ArrayList();
		for (Object obj : list) {
			Object destObj = getObject(className, obj);
			templist.add(destObj);
		}
		return templist;
	}

	private Object getObject(Class className, Object... objs) {
		Object obj = null;
		if(nullClass(className)|| nullObject(objs)){
			return null;
		}
		obj = newInstanceByClassName(className);
		copyObjectProperies(obj, objs);
		return obj;
	}

	private boolean nullClass(Class className) {
		return className == null;
	}

	private boolean nullObject(Object... objs) {
		return objs == null || objs.length == 0;
	}

	private Object newInstanceByClassName(Class className) {
		Object retObj = null;
		try {
			retObj = className.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return retObj;
	}

	public void copyObjectProperies(Object destObject, Object... sourceObjects) {
		if(destObject == null || sourceObjects == null ){
			return ;
		}
		for (Object obj : sourceObjects) {
			try {
				PropertyUtils.copyProperties(destObject, obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean nullList(List list) {
		return list == null || list.size() == 0;
	}

	public static void main(String[] args) {
		// DPOSBaseTransfer tr = new DPOSBaseTransfer();
		// Vo1 v1 = new Vo1();
		// Vo2 v2 = new Vo2();
		// v1.setName("huangtao");
		// v2.setPassword("123456");
		// Vo v = new Vo();
		// v=(Vo) tr.getVO(Vo.class, v1);
		// System.out.println();
		// List list = new ArrayList();
		// list.add(v1);
		// List vlist = tr.getVOList(Vo.class, list);
		// System.out.println();
	}
}
