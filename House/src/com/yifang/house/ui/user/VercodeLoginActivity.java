package com.yifang.house.ui.user;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.yifang.house.R;
import com.yifang.house.common.CommonUtil;
import com.yifang.house.ui.BaseActivity;
import com.yifang.house.widget.Topbar;
/**
 * 验证码登录
 * @author Administrator
 *
 */
public class VercodeLoginActivity extends BaseActivity {
	private Button backBt;
	private EditText userNameEt;
	private EditText vercodeEt;
	private Button loginBt;
	private Context context;

	@Override
	protected int getContentLayoutId() {
		return R.layout.vercode_login;
	}

	@Override
	protected void initViews() {
		Topbar topbar = new Topbar(findViewById(R.id.house_topbar));
		backBt = topbar.getLeftBt();
		topbar.setToolbarCentreText(getResources().getString(
				R.string.login));
		userNameEt = (EditText)findViewById(R.id.user_name_et);
		vercodeEt = (EditText)findViewById(R.id.vercode_et);
		loginBt = (Button)findViewById(R.id.login_bt);
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
			String vercode = vercodeEt.getText().toString();
			if(validateLogin(userName,vercode)) {
				
			}
		}
	};
	
	private boolean validateLogin(String userName,String vercode) {
		boolean flag;
		if(userName.equals("")) {
			flag = false;
			CommonUtil.sendToast(context, getString(R.string.username_not_null));
		} else if(vercode.equals("")) {
			flag = false;
			CommonUtil.sendToast(context, getString(R.string.vercode_not_null));
		} else {
			flag = true;
		}
		return flag;
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
	}

	@Override
	protected void addListeners() {
		backBt.setOnClickListener(backListener);
		loginBt.setOnClickListener(loginListener);
	}
	

}
