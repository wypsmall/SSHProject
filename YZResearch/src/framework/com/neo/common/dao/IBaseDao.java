package com.neo.common.dao;

import java.util.List;

public interface IBaseDao<T>{
	public Integer add(T data);
	public void modify(T data);
	public boolean deleteById(Integer id);
	public Integer getTotalCount();
	public List<T> getDataPage(Integer start,Integer pagesize);
	public T getDataById(Integer id);
}
