package com.yifang.house.bean;
/**
 * 户型
 * @author Administrator
 *
 */
public class HouseApartment {
	private String picUrl; //图片路径  
	private String title; //图片主题
	private String area;//面积
	private String huxing; //户型
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getHuxing() {
		return huxing;
	}
	public void setHuxing(String huxing) {
		this.huxing = huxing;
	}
	

}
