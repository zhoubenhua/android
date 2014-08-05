package com.yifang.house.bean;
/**
 * 价格趋势
 * @author Administrator
 *
 */
public class PriceTrends {
	private String propertyid;//物业类型 id
	private String url;//趋势图路径
	public String getPropertyid() {
		return propertyid;
	}
	public void setPropertyid(String propertyid) {
		this.propertyid = propertyid;
	}
	public String getUrl() {
		return url;
	}		
	public void setUrl(String url) {
		this.url = url;
	}
	
}
