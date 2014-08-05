package com.yifang.house.bean;

import java.util.List;

public class SystemConfig {
	private List<Category> property; //物业类型
	private List<Price> price; //价格区间
	
	//private List<Arround> around; //附近
	//private List<Status> status;//状态
	//private List<Shi> shi;//户型
	//private List<Decoration> decoration;//装修
	//private List<Feature> features; //特色
	//private List<Sorting> sorting; //排序规则
	private List<CityArea> district;//区域级联
	private List<SortType> more;
			
	public List<SortType> getMore() {
		return more;
	}
	public void setMore(List<SortType> more) {
		this.more = more;
	}
	public List<Category> getProperty() {
		return property;
	}
	public void setProperty(List<Category> property) {
		this.property = property;
	}
	public List<Price> getPrice() {
		return price;
	}
	public void setPrice(List<Price> price) {
		this.price = price;
	}
	
//	public List<Arround> getAround() {
//		return around;	
//	}
//	public void setAround(List<Arround> around) {
//		this.around = around;
//	}
//	public List<Status> getStatus() {
//		return status;
//	}
//	public void setStatus(List<Status> status) {
//		this.status = status;
//	}
//	public List<Shi> getShi() {
//		return shi;
//	}
//	public void setShi(List<Shi> shi) {
//		this.shi = shi;
//	}
//	public List<Decoration> getDecoration() {
//		return decoration;
//	}
//	public void setDecoration(List<Decoration> decoration) {
//		this.decoration = decoration;
//	}
//	public List<Sorting> getSorting() {
//		return sorting;
//	}
//	public void setSorting(List<Sorting> sorting) {
//		this.sorting = sorting;
//	}
	public List<CityArea> getDistrict() {
		return district;
	}
	public void setDistrict(List<CityArea> district) {
		this.district = district;
	}
	
	

}
