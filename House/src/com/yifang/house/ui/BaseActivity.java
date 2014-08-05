package com.yifang.house.ui;
import com.yifang.house.AppManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;	
import android.view.Window;
import com.yifang.house.R;
import android.view.inputmethod.InputMethodManager;

public class BaseActivity extends Activity {
	private ProgressDialog loadingProgressDialog;

	/**				
	 * 隐藏键盘
	 */
	public void keyBoardCancle() {
		View view = getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 左右推动跳转
	 * 
	 * @param mActivity
	 * @param intent
	 */
	protected void startActivityLeft(Intent intent) {
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	protected void back() {
		finish();
		overridePendingTransition(R.anim.back_push_left_in,
				R.anim.back_push_left_out);// 左右
	}

	protected ProgressDialog showProgressDialog(String title, String message) {
		try {
			if (isFinishing() == false) {
				if (loadingProgressDialog == null) {
					loadingProgressDialog = ProgressDialog.show(this, title,
							message);
					loadingProgressDialog.setCancelable(true);
					loadingProgressDialog.setCanceledOnTouchOutside(true);
				} else {
					loadingProgressDialog.setTitle(title);
					loadingProgressDialog.setMessage(message);
					if (loadingProgressDialog.isShowing() == false) {
						loadingProgressDialog.show();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loadingProgressDialog;
	}
	
	protected void dissmissProgressDialog() {
		try{
			if (isFinishing() == false && loadingProgressDialog != null) {
				loadingProgressDialog.dismiss();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(getContentLayoutId());
		if (android.os.Build.VERSION.SDK_INT > 9) {     
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();    
			StrictMode.setThreadPolicy(policy); 
		} 

	    AppManager.getAppManager().addActivity(this);
		initViews();
		initData();
		addListeners();
	}
	
	/**
	 * 
	 *getContentLayoutId(这里用一句话描述这个方法的作用）
	 *（设置页面布局的id）
	 * @return
	 *int
	 * @exception
	 * @since 1.0.0
	 */
	protected int getContentLayoutId() {
		return 0;
	}

	/**
	 * 初始化控件
	 */
	protected void initViews() {
	}

	/**
	 * 初始化数据
	 */
	protected void initData() {
	}

	/**
	 * 添加事件
	 */
	protected void addListeners() {
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
