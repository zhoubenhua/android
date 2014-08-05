package com.yifang.house;
import com.yifang.house.bean.SystemConfig;

import android.app.Application;

public class AppContext extends Application{
	public static AppContext instance;
	public  SystemConfig systemConfig;

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}


	public void onCreate() {
		super.onCreate();	
		instance = this;
		AppException handler = AppException.getInstance(this);  
		Thread.setDefaultUncaughtExceptionHandler(handler);  
	} 
	

	public static AppContext getInstance() {
		return (AppContext) instance;
	}


}
