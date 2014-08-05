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
 * 修改密码
 * @author Administrator
 *
 */
public class ModifyPasswordActivity extends BaseActivity {
	private Button backBt;
	private EditText passwordEt;
	private Button commitBt;
	private Context context;

	@Override
	protected int getContentLayoutId() {
		return R.layout.modify_password;
	}

	@Override
	protected void initViews() {
		Topbar topbar = new Topbar(findViewById(R.id.house_topbar));
		backBt = topbar.getLeftBt();
		topbar.setToolbarCentreText(getResources().getString(
				R.string.modify_password));
		passwordEt = (EditText)findViewById(R.id.password_et);
		commitBt = (Button)findViewById(R.id.commit_bt);
	}
	
	private OnClickListener backListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			back();
		}
	};
	
	private OnClickListener commitListener = new OnClickListener() {
		public void onClick(View v) {
			String password = passwordEt.getText().toString();
			if(validatePassowrd(password)) {
				
			}
		}
	};
	
	private boolean validatePassowrd(String password) {
		boolean flag;
		if(password.equals("")) {
			flag = false;
			CommonUtil.sendToast(context, getString(R.string.password_not_null));
		}  else {
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
		commitBt.setOnClickListener(commitListener);
	}
	

}
