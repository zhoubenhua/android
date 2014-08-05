package com.yifang.house;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import com.yifang.house.common.LogUtil;


import android.content.Context;



public class AppException implements UncaughtExceptionHandler{
	// 捕获全局异常  
	private static AppException myCrashHandler ;  
	private Context context;
	//1.私有化构造方�? 
	private AppException(Context context){  
		this.context = context;
	} 
	
	public static synchronized AppException getInstance(Context context){  
		if(myCrashHandler!=null){  
			return myCrashHandler;  
		} else {  
			myCrashHandler  = new AppException(context);  
			return myCrashHandler;  
		}  
	}
	
	public void uncaughtException(Thread arg0, Throwable arg1) {  
		System.out.println("程序挂掉�?");  
		// 3.把错误的堆栈信息 获取出来   
		String errorinfo = "[---------程序挂掉-----]:"+getErrorInfo(arg1);  
		LogUtil.log(errorinfo);
		/**
		 * �?��
		 */
		//AppManager.getAppManager().AppExit(context);
	}
	
	/** 
	* 获取错误的信�? 
	* @param arg1 
	* @return 
	*/  
	private String getErrorInfo(Throwable arg1) {  
		Writer writer = new StringWriter();  
		PrintWriter pw = new PrintWriter(writer);  
		arg1.printStackTrace(pw);  
		pw.close();  
		String error= writer.toString();  
		return error;  
	}  

}
