package com.yifang.house.api;

public class Connect {
	public static boolean isLocal=false;
	private static Connect connect;
	/**
	 * 后台服务器地址
	 */
	private String apiUrl;
	public static Connect getInstance(){
		if(connect==null){
			connect =new Connect();
		}  
		/**
		 * 判断是否是线下, 默认是线下
		 */
		if(isLocal){					
			/**			 
			 * 配置线下服务器地址			
			 */
			connect.setApiUrl("http://192.168.0.51:8888/MakerGatewayService/");
		} else{
			/**
			 * 配置线上服务器地址
			 */
			connect.setApiUrl("http://007.1f.cn/app/");
		}
		return connect;
	}
	

	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
}
