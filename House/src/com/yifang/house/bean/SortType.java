package com.yifang.house.bean;

import java.util.List;

public class SortType {
	private String id;
	private String name;
	private List<SortChild> menu;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SortChild> getMenu() {
		return menu;
	}
	public void setMenu(List<SortChild> menu) {
		this.menu = menu;
	}
	

}
