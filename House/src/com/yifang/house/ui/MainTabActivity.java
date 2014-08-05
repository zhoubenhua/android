package com.yifang.house.ui;
import com.yifang.house.R;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;

public class MainTabActivity extends TabActivity{
	
	private TabHost mTabHost;
	private Button homeBt;		
	private Button mineBt;
	private Button toolsBt;
	private Button findHouseBt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tab);	
		initViews();
		initData();
		
	}
	
	private void initData() {
		Intent intent = new Intent(this,HomeActivity.class);
		mTabHost.addTab(mTabHost.newTabSpec("ONE").setIndicator("ONE")
				.setContent(intent));		
		
	}

	
	private void initViews() {
		mTabHost = this.getTabHost();
		homeBt = (Button)this.findViewById(R.id.home_bt);
		mineBt = (Button)this.findViewById(R.id.mine_bt);
		toolsBt = (Button)this.findViewById(R.id.tools_bt);
		findHouseBt = (Button)this.findViewById(R.id.find_house_bt);
	}

}
