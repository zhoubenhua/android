package com.yifang.house;

public class AppConfig {
	public static final String DEFAULT_APP_KEY = "9998332BDD771EA0A50B0F78B9552F9C";
	
	public static final String SHARED_PREFS_KEY = "order.prefs";
	
	public static String REQUEST_URI = "http://db.ppsms.com:3580/rest/service.php";
	private String sharedPreferencesName;
	private static AppConfig  instance;
	private AppConfig() {
		sharedPreferencesName = "order_pref";
	}
	
	public static AppConfig getInstance() {
		if(instance == null) {
			instance = new AppConfig();	
		}
		return instance;
	}

	public String getSharedPreferencesName() {
		return sharedPreferencesName;
	}
	

}
