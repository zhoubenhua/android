package com.yifang.house.api;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.yifang.house.AppConfig;
import com.yifang.house.common.LogUtil;
import com.yifang.house.common.TimeUtil;
import com.yifang.house.http.AsyncHttpClient;
import com.yifang.house.http.AsyncHttpResponseHandler;
import com.yifang.house.http.RequestParams;



public class APIAsyncTask {
	private CallbackListener listener;
	private String protocol;
	private boolean requestType;// true 为 get false 为post
	private AsyncHttpClient http;
	
	// private String sign;
	// private static APIAsyncTask asyncTask;
	public void get(String protocol, Object obj, CallbackListener listener) {
		this.listener = listener;
		this.protocol = protocol;
		this.requestType = true;
		convert(obj);
	}

	public void post(String protocol, Object obj, CallbackListener listener) {
		this.listener = listener;
		this.protocol = protocol;
		this.requestType = false;
		convert(obj);
	}

	public void execute() {

	}

	private AsyncHttpClient getAsyncHttp() {
		if (http == null) {
			http = new AsyncHttpClient();
			http.setTimeout(1000*10);
			//http.addHeader("Host", ConfigConnect.getConnect().getApiHost());
		}
		return http;
	}

	private void convert(Object obj) {
		StringBuffer params = new StringBuffer();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("format", "json");
		try {
			String timestamp = URLEncoder.encode(TimeUtil.getCurrentDate(),"UTF-8");
			map.put("timestamp", timestamp);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();			
		}				
					
		map.put("method", protocol);
		map.put("city", "007");
		int i = 0;
		for (String key : map.keySet()) {
			params.append(i == 0 ? "" : "&");
			params.append(key + "=" + map.get(key));
			i++;
		}
		try {
			params.append(copy(obj)).toString();
			AsyncHttpClient client = this.getAsyncHttp();
			String str = requestType?"GET":"POST";
			String baseUrl ;
			baseUrl = Connect.getInstance().getApiUrl();
			String url = baseUrl+"?"+params.toString();
			LogUtil.log("[----send---"+str+" ----]:"+url);
			if(requestType) {
				client.get(url, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						LogUtil.log("[---------result----GET-----]:"+response);
						requestFinished(response);			
					}						

					public void onFinish() {
						LogUtil.log("onFinish");
					}			
					
					public void onFailure(Throwable error,
							String content) {
						error.printStackTrace();
						requestFailed(error,content);
						LogUtil.log("onFailure");
					}			
				});

			} else {
				HttpEntity entity = new StringEntity( params.toString(), HTTP.UTF_8);  
				client.post(null, baseUrl, entity, "application/x-www-form-urlencoded",
						new AsyncHttpResponseHandler() {
							public void onSuccess(String response) {
								LogUtil.log("[---------result----POST-----]:"+response);
								requestFinished(response);
							}

							public void onFinish() {
								LogUtil.log("onFinish");
							}

							public void onFailure(Throwable error,
									String content) {
								error.printStackTrace();
								requestFailed(error,content);
								LogUtil.log("onFailure");

							}

						});

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 处理服务器返回的json数据
	 */
	private void requestFinished(String result) {

		if (StringUtils.isNotBlank(result)) {

			try {
				JSONObject jsons = new JSONObject(result);
				int code = jsons.getInt("code");
				switch (code) {
				case 200:
					String body = jsons.get("data").toString();
					if (body.equals("{}")) {
						this.listener.onError("请求出错");
					} else {
						this.listener.onSuccess(body);
					}
					break;
				default:
					String msg = jsons.get("msg").toString();
					if(StringUtils.isNotEmpty(msg)) {
						this.listener.onError(msg);
					} else {
						this.listener.onError("其他错误"); 
					}
					break;
				}

			} catch (JSONException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	/**
	 * ����ʧ��
	 */
	private void requestFailed(Throwable error,String content) {
		LogUtil.log("error:"+error.getMessage()+":"+content);
		if(error instanceof SocketTimeoutException || error instanceof ConnectTimeoutException || error instanceof ConnectException || error instanceof InterruptedIOException){
			this.listener.onError("服务器连接超时");
		}else if(error instanceof SocketException){
			this.listener.onError("服务器连接失败");
		}else if(error instanceof HttpResponseException){
			this.listener.onError("服务器错误");
		}else{
			this.listener.onError("服务器连接失败");
		}
		
	}
	
	private String copy(Object obj) throws IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Class<?> classType = obj.getClass();
		if(classType.equals(RequestParams.class)){
			if(StringUtils.isNotEmpty(obj.toString())) {
				return "&"+obj.toString();
			} else {
				return "";
			}
			
		} else {			
						
			Object objectCopy = classType.getConstructor(new Class[] {})
					.newInstance(new Object[] {});
			Field[] fields = classType.getDeclaredFields();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if (fieldName.equals("serialVersionUID")) {
					continue;
				}
				String stringLetter = fieldName.substring(0, 1).toUpperCase();
				String getName = "get" + stringLetter + fieldName.substring(1);
				String setName = "set" + stringLetter + fieldName.substring(1);
				Method getMethod = classType.getMethod(getName, new Class[] {});
				Method setMethod = classType.getMethod(setName,
						new Class[] { field.getType() });
				Object value = getMethod.invoke(obj, new Object[] {});
				if (value == null)
					value = "";
				sb.append("&" + fieldName + "=" + value);
				setMethod.invoke(objectCopy, new Object[] { value });
			}		
			return sb.toString();
		
		}
	}
	
//	private String copy(Map<String, String> map)  {
//		StringBuffer sb = new StringBuffer();
//		for (String key : map.keySet()) {
//			sb.append("&" + key + "=" + map.get(key));
//		}
//		return sb.toString();
//
//	}

//	private String copyEncode(Map<String, String> map) {
//		StringBuffer sb = new StringBuffer();
//		for (String key : map.keySet()) {
//			sb.append("&" + key + "=" + URLEncoder.encode(map.get(key)));
//		}
//		return sb.toString();
//
//	}

}
