package com.yifang.house.ui;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.yifang.house.R;
import com.yifang.house.ui.property.PropertyListActivity;
import com.yifang.house.ui.user.LoginActivity;
import com.yifang.house.widget.IconPageIndicator;
import com.yifang.house.widget.IconPagerAdapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HomeActivity extends BaseActivity {
	private ImageView newHouseIv;
	private ImageView discountIv;
	private Context context;
	private ViewPager adVp;
	private IconPageIndicator circlePageIndicator;
	private int autoScrollTime;
	private int currentItem;
	
    
    private OnPageChangeListener circlePageChangeListener = new OnPageChangeListener() {
    	@Override
		public void onPageSelected(int arg0) {
			mHandler.removeCallbacks(autoScroll);
			mHandler.postDelayed(autoScroll, 2000);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {}
    };
    
    
    @Override
	protected void initViews() {
    	adVp = (ViewPager)findViewById(R.id.ad_vp);
    	newHouseIv = (ImageView)findViewById(R.id.new_house_iv);
    	discountIv = (ImageView)findViewById(R.id.discount_iv);
		circlePageIndicator = (IconPageIndicator)findViewById(R.id.circle_indicator);
	}

	@Override
	protected void addListeners() {
		circlePageIndicator.setOnPageChangeListener(circlePageChangeListener);
		newHouseIv.setOnClickListener(newHouseListener);
		discountIv.setOnClickListener(discountListener);
	}
	
	private OnClickListener newHouseListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivityLeft(new Intent(context,PropertyListActivity.class));
		}
	};
	
	private OnClickListener discountListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivityLeft(new Intent(context,LoginActivity.class));
		}
	};
	
	Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        	
        }
    };
    
	Runnable autoScroll = new Runnable() {
		public void run() {
			if(adVp.getAdapter() != null){
				if (adVp.getCurrentItem() == adVp.getAdapter().getCount() - 1) {
					currentItem = 0;
				}else {
					currentItem = adVp.getCurrentItem() + 1;
				}
				adVp.setCurrentItem(currentItem);
			}
		}
	};
	
	/**
     * ViewPager适配器
     */
    public class AdPagerAdapter extends PagerAdapter implements IconPagerAdapter{
        public List<String> _adList;
        public AdPagerAdapter(List<String> adList) {
            this._adList = adList;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
        	// 将ImageView从ViewPager移除
        	((ViewPager) arg0).removeView((View) arg2);
        }

        public void finishUpdate(View arg0) {
        }

        public int getCount() {
            return _adList != null ? _adList.size() : 0;
        }

        @Override
        public Object instantiateItem(View container, final int position) {
        	View view = View.inflate(context, R.layout.ad_item, null);
			((ViewPager) container).addView(view);
			ImageView imageView = (ImageView) view.findViewById(R.id.ad_iv);
//			imageView.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					Intent intent = new Intent(context,WebActivity.class);
//					intent.putExtra(Constant.WebIntentDef.WEB_URL, getResources().getString(R.string.web_url));
//					startActivity(intent);
//				}
//			});
			return view;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

		@Override
		public int getIconResId(int index) {
			return R.drawable.circle_indicator;
		}
    }
	
	@Override
	protected void initData() {
		context = this.getApplicationContext();
		autoScrollTime = 4000;
		currentItem = 0;
		List<String> adList = new ArrayList<String>();
		adList.add("");
		adList.add("");
		adList.add("");
		adList.add("");
		adVp.setAdapter(new AdPagerAdapter(adList));
		circlePageIndicator.setViewPager(adVp);
		mHandler.postDelayed(autoScroll, autoScrollTime);
	}

	
	@Override
	protected int getContentLayoutId() {
		return R.layout.home;
	}

}