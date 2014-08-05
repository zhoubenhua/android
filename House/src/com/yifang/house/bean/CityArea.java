package com.yifang.house.bean;

import java.util.List;

public class CityArea {
	private String id; //区域父类id
	private String name; //区域父类名字
	private List<CityAddress> menu; //地址列表
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
	public List<CityAddress> getMenu() {
		return menu;
	}
	public void setMenu(List<CityAddress> menu) {
		this.menu = menu;
	}
	

}
