package com.yifang.house.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class CommonUtil {
	/**CommonUtil
	 * Toast
	 * 
	 * @param mContext
	 * @param text
	 */
	public static void sendToast(Context mContext, String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}	
	
	/**
     * 获取外置SD卡路�?
     * 
     * @return
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当�?Java 应用程序相关的运行时对象
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
            	Log.i("CommonUtil:getSDCardPath", lineStr);
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure",
                                "");
                        return result;
                    }
                }
                // �?��命令是否执行失败�?
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束�?：非正常结束
                    Log.e("CommonUtil:getSDCardPath", "命令执行失败!");
                }
            }
            inBr.close();
            in.close();
            
        } catch (Exception e) {
            Log.e("CommonUtil:getSDCardPath", e.toString());

            return Environment.getExternalStorageDirectory().getPath();
        }

        return Environment.getExternalStorageDirectory().getPath();
    }
    
    /**
	 * 保存图片到本�?
	 * @param userId
	 * @return
	 */
	public static String saveBitmpToFile(Bitmap bitmap) {
		File file = new File(CommonUtil.getSDCardPath()
			+ "/temp.jpg"); 
		String filePath = "";
		FileOutputStream out = null;
		try {
			file.createNewFile();
			out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out); 
			out.flush();
			out.close();
			filePath = file.getPath();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
    
    /**
	 * 网络�?��
	 * @param mContext
	 * @return
	 */
	public static boolean checkNetwork(Context mContext){
		ConnectivityManager cwjManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		boolean flag = info != null && info.isAvailable() ? true : false;
		if(!flag) {	
			CommonUtil.sendToast(mContext, "请检查你的网络");
		}
		return flag;
	}
    
    /**
	 * 日期转成毫秒
	 * params 日期字符�?
	 */
	public static String dateStrConvertTimes(String dateStr) {
		String times = "";
		//DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = (Date) df.parse(dateStr);
			times = date.getTime()+"";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}									
		return times;
	}
	
	/**
	 * 日期字符串转成日�?
	 * params 日期
	 */
	public static Date dateStrConvertDate(String dateStr) {
		Date date = null;				
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {							
			date = (Date) df.parse(dateStr);			
		} catch (Exception e) {					
			e.printStackTrace();
		}									
		return date;
	}
	
	/**
	 * 毫秒转成日期
	 * @param times  毫秒
	 * @return
	 */
	public static String timesConvertDateStr(String times) {
		String dateStr = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dateStr = df.format(new Date(Long.valueOf(times)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateStr;
	}
	
    /**
	 * 获取当前时间
	 * 格式  2012-12-21 12:12:12
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"); 
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}
}
