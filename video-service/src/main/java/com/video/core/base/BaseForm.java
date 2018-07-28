package com.video.core.base;

import java.io.Serializable;

public class BaseForm implements Serializable{
	private static final long serialVersionUID = 3673763622176140931L;
	
	/** 获取当前页 **/
	private int page = 0;
	/** 获取每页记录数 **/
	private int count = 10;
	
	/**
	 * 查询条件
	 */
	private String query;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
}
