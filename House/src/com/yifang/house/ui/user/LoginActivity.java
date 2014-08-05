package com.yifang.house.ui.user;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yifang.house.R;
import com.yifang.house.common.CommonUtil;
import com.yifang.house.ui.BaseActivity;
import com.yifang.house.widget.Topbar;

public class LoginActivity extends BaseActivity {
	private Button backBt;
	private EditText userNameEt;
	private EditText passwordEt;
	private Button loginBt;
	private TextView telVercodeLoginTv;
	private TextView forgetPasswordTv;
	private TextView registerNewAccountTv;
	private Context context;

	@Override
	protected int getContentLayoutId() {
		return R.layout.user_login;
	}

	@Override
	protected void initViews() {
		Topbar topbar = new Topbar(findViewById(R.id.house_topbar));
		backBt = topbar.getLeftBt();
		topbar.setToolbarCentreText(getResources().getString(
				R.string.login));
		userNameEt = (EditText)findViewById(R.id.user_name_et);
		passwordEt = (EditText)findViewById(R.id.password_et);
		loginBt = (Button)findViewById(R.id.login_bt);
		telVercodeLoginTv = (TextView)findViewById(R.id.tel_vercode_login_tv);
		forgetPasswordTv = (TextView)findViewById(R.id.forget_password_tv);
		registerNewAccountTv = (TextView)findViewById(R.id.register_new_account_tv);
	}
	
	private OnClickListener backListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			back();
		}
	};
	
	private OnClickListener loginListener = new OnClickListener() {
		public void onClick(View v) {
			String userName = userNameEt.getText().toString();
			String password = passwordEt.getText().toString();
			if(validateLogin(userName,password)) {
				
			}
		}
	};
	
	private OnClickListener vercodeLoginListener = new OnClickListener() {
		public void onClick(View v) {
			startActivityLeft(new Intent(context,VercodeLoginActivity.class));
		}
	};
	
	private boolean validateLogin(String userName,String password) {
		boolean flag;
		if(userName.equals("")) {
			flag = false;
			CommonUtil.sendToast(context, getString(R.string.username_not_null));
		} else if(password.equals("")) {
			flag = false;
			CommonUtil.sendToast(context, getString(R.string.password_not_null));
		} else {
			flag = true;
		}
		return flag;
	}

	@Override
	protected void initData() {
		context = this.getApplicationContext();
	}

	@Override
	protected void addListeners() {
		backBt.setOnClickListener(backListener);
		loginBt.setOnClickListener(loginListener);
		telVercodeLoginTv.setOnClickListener(vercodeLoginListener);
	}
	

}
