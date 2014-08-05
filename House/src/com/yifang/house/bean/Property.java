package com.yifang.house.bean;

public class Property {
	private String id; //楼盘id
	private String name; //楼盘名称
	private String regionId; //区域id
	private String regionName; //区域名称
	private String price;//楼盘价格
	private String address; //楼盘地址
	private String logo;//楼盘图片地址
	private String haveCoupon; //是否有优惠
	private String status;
	private String featuresTxt;//特色数据
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
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getHaveCoupon() {
		return haveCoupon;
	}
	public void setHaveCoupon(String haveCoupon) {
		this.haveCoupon = haveCoupon;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFeaturesTxt() {
		return featuresTxt;
	}
	public void setFeaturesTxt(String featuresTxt) {
		this.featuresTxt = featuresTxt;
	}
	
	
}
