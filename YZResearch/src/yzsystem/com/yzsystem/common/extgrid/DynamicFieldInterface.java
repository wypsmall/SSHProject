package com.yzsystem.common.extgrid;

public interface DynamicFieldInterface {
	/**
	 * 显示名称
	 * 
	 * @return
	 * @author 曹刚 新增日期：2013-5-15下午04:55:49
	 * @since 1.0
	 */
	public String getFieldName();
	/**
	 * 显示字段
	 * 
	 * @return
	 * @see (此处写相关联的一些类名，如果没有，请去掉此注释，如果多于一个，请换行新增一个@see）
	 * @author 曹刚 新增日期：2013-5-15下午04:56:41
	 * @since 1.0
	 */
	public String getFieldValue();
	/**
	 * 扩展
	 * 
	 * @return
	 * @author 曹刚 新增日期：2013-5-15下午04:57:07
	 * @since 1.0
	 */
	public String getFieldArg();
	
}
