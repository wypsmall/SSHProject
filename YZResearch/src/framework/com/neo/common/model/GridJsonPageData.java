package com.neo.common.model;

import java.util.List;

import com.neo.common.vo.IValueObject;

public class GridJsonPageData<T> implements IValueObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2425230722129138946L;
	private Integer totalCount;
	private List<T> results;
	
	public GridJsonPageData() {
		super();
	}
	public GridJsonPageData(Integer totalCount, List<T> results) {
		super();
		this.totalCount = totalCount;
		this.results = results;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	
}
