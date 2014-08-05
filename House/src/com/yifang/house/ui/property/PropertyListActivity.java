package com.yifang.house.ui.property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yifang.house.AppContext;
import com.yifang.house.R;
import com.yifang.house.adapter.PropertyItemAdapter;
import com.yifang.house.api.APIAsyncTask;
import com.yifang.house.api.CallbackListener;
import com.yifang.house.api.Protocol;
import com.yifang.house.bean.CityArea;
import com.yifang.house.bean.Price;
import com.yifang.house.bean.Category;
import com.yifang.house.bean.Property;
import com.yifang.house.bean.PropertyListResult;
import com.yifang.house.bean.SortType;
import com.yifang.house.bean.SystemConfig;
import com.yifang.house.common.CommonUtil;
import com.yifang.house.common.Constant;
import com.yifang.house.http.RequestParams;
import com.yifang.house.ui.BaseActivity;
import com.yifang.house.widget.CategoryView;
import com.yifang.house.widget.PriceView;
import com.yifang.house.widget.RegionView;
import com.yifang.house.widget.SortView;
import com.yifang.house.widget.pullrefresh.PullToRefreshBase;
import com.yifang.house.widget.pullrefresh.PullToRefreshBase.Mode;
import com.yifang.house.widget.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.yifang.house.widget.pullrefresh.PullToRefreshListView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 楼盘列表
 * @author Administrator
 *
 */
public class PropertyListActivity extends BaseActivity {
	private Context context;
	private ImageView mapIv;
	private ImageView searchIv;
	private RelativeLayout areaRl;
	private TextView areaNameTv;
	private EditText keyEt;
	private RelativeLayout typeRl;
	private TextView typeNameTv;
	private RelativeLayout priceRl;
	private TextView priceNameTv;
	private RelativeLayout moreRl;
	private TextView moreNameTv;
	private AppContext appContext;
	private PullToRefreshListView propertyListLv;
	private int cutPage;
	private int totalPage;
	private int offset;
	private int pageSize;
	private List<Property> propertyList;
	private PropertyItemAdapter propertyAdapter;
	private int popType; //1是区域,2是价格 3是分类  4是更多
	private int loadPropertyFlag; //1是加载,2是刷新,3是分页加载  

	/**
	 * 区域窗口
	 */
	private int regionPopFlag;
	private PopupWindow regionPop;
	public  RegionView regionView;
	public   List<CityArea> regionTypeList;
	private String areaId;
	
	/**
	 * 价格窗口
	 */
	private int pricePopFlag;
	private PopupWindow pricePop;
	private  PriceView priceView;
	public  List<Price> priceList;
	private String priceId;

	/**类型窗口
	 */
	private int categoryPopFlag;
	private PopupWindow categoryPop;
	private  CategoryView categoryView;
	public  List<Category> categoryList;	
	private String categoryId;
	
	/**
	 * 区域窗口
	 */
	private int sortPopFlag;
	private PopupWindow sortPop;
	public SortView sortView;
	public  List<SortType> sortTypeList;
	private String sortId;
	

    
    @Override
	protected void initViews() {
    	mapIv = (ImageView)findViewById(R.id.map_iv);
    	searchIv = (ImageView)findViewById(R.id.search_iv);
    	areaRl = (RelativeLayout)findViewById(R.id.area_rl);
    	areaNameTv = (TextView)findViewById(R.id.area_name_tv);
    	typeRl = (RelativeLayout)findViewById(R.id.type_rl);
    	typeNameTv = (TextView)findViewById(R.id.type_name_tv);
    	priceRl = (RelativeLayout)findViewById(R.id.price_rl);
    	priceNameTv = (TextView)findViewById(R.id.price_name_tv);
    	moreRl = (RelativeLayout)findViewById(R.id.more_rl);
    	moreNameTv = (TextView)findViewById(R.id.more_name_tv);
    	keyEt = (EditText)findViewById(R.id.key_et);
    	propertyListLv = (PullToRefreshListView)findViewById(R.id.property_list_lv);
	}
    
    private OnItemClickListener propertyListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Property property = propertyList.get(arg2-1);			
			Intent intent = new Intent(context,PropertyDetailActivity.class);
			intent.putExtra(Constant.PROPERTY_ID, "199");
			startActivityLeft(intent);					
		}
    	
    };
    
	@Override
	protected void addListeners() {		
		areaRl.setOnClickListener(regionTabListener);				
		regionView.setOnSelectListener(regionSelectListener);
		priceRl.setOnClickListener(priceTabListener);		
		priceView.setOnSelectListener(priceSelectListener);
		typeRl.setOnClickListener(categoryTabListener);
		categoryView.setOnSelectListener(catetgorySelectListener);
		propertyListLv.setOnRefreshListener(loadPropertyListener);
		sortView.setOnSelectListener(sortSelectListener);
		moreRl.setOnClickListener(sortTabListener);
		propertyListLv.setOnItemClickListener(propertyListener);
	}


	public void openRegionPop() {
		if (regionPop == null) {
			regionPopFlag = 1;
			regionPop = new PopupWindow(regionView,  LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
			regionPop.setFocusable(false);
			regionPop.setOutsideTouchable(true);	
			regionPop.setBackgroundDrawable(new BitmapDrawable());
			View closeView = (View)regionView.findViewById(R.id.close_view);
			closeView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					regionPop.dismiss();
					regionPopFlag = -1;
				}
			});
			regionPop.showAsDropDown(areaRl);
			regionPop.setTouchInterceptor(new OnTouchListener() {
				public boolean onTouch(View arg0, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						regionPop.dismiss();
						return false;
					}     		
					return false; 
				}     
			}); 
			regionPop.update();
		} else {	
			regionPopFlag = regionPopFlag * -1;
			if(regionPopFlag >0) {
				regionPop.showAsDropDown(areaRl);	
				regionPop.update();
			} else {
				regionPop.dismiss();	
			}
		}
	}
	
	public void openSortPop() {
		if (sortPop == null) {
			sortPopFlag = 1;
			sortPop = new PopupWindow(sortView,  LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
			sortPop.setFocusable(false);
			sortPop.setOutsideTouchable(true);	
			sortPop.setBackgroundDrawable(new BitmapDrawable());
			View closeView = (View)sortView.findViewById(R.id.close_view);
			closeView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					sortPop.dismiss();
					sortPopFlag = -1;
				}
			});
			sortPop.showAsDropDown(moreRl);
			sortPop.setTouchInterceptor(new OnTouchListener() {
				public boolean onTouch(View arg0, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						sortPop.dismiss();
						return false;
					}     		
					return false; 
				}     
			}); 
			sortPop.update();
		} else {	
			sortPopFlag = sortPopFlag * -1;
			if(sortPopFlag >0) {
				sortPop.showAsDropDown(moreRl);	
				sortPop.update();
			} else {
				sortPop.dismiss();	
			}
		}
	}
    
	private OnRefreshListener2 loadPropertyListener =  new OnRefreshListener2<ListView>(){
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			loadPropertyFlag = 2;
			offset = 0;
			searchProperty();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			loadPropertyFlag = 3;
			searchProperty();
		}
			
	};
	
    private OnClickListener regionTabListener = new OnClickListener() {
		public void onClick(View arg0) {
			popType = 1;
			keyBoardCancle();
			if(regionTypeList != null) {
				openRegionPop();
			} else {
				loadSystemConfig();
			}
		}
	};
	
	public void openCategoryPop() {
		if (categoryPop == null) {
			categoryPopFlag = 1;
			categoryPop = new PopupWindow(categoryView,  LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
			categoryPop.setFocusable(false);
			categoryPop.setOutsideTouchable(true);	
			categoryPop.setBackgroundDrawable(new BitmapDrawable());
			View closeView = (View)categoryView.findViewById(R.id.close_view);
			if(closeView != null) {
				closeView.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						categoryPop.dismiss();
						categoryPopFlag = -1;
					}
				});
			}
			categoryPop.showAsDropDown(typeRl);
			categoryPop.setTouchInterceptor(new OnTouchListener() {
				public boolean onTouch(View arg0, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						categoryPop.dismiss();
						return false;
					}     		
					return false; 
				}     
			}); 
			categoryPop.update();
		} else {	
			categoryPopFlag = categoryPopFlag * -1;
			if(categoryPopFlag >0) {
				categoryPop.showAsDropDown(typeRl);	
				categoryPop.update();
			} else {
				categoryPop.dismiss();	
			}
		}
	}
    
    private OnClickListener categoryTabListener = new OnClickListener() {
		public void onClick(View arg0) {
			popType = 3;
			keyBoardCancle();
			if(categoryList != null) {
				openCategoryPop();
			} else {
				loadSystemConfig();
			}
		}
	};
	
	private OnClickListener sortTabListener = new OnClickListener() {
		public void onClick(View arg0) {
			popType = 4;
			keyBoardCancle();
			if(sortTypeList != null) {
				openSortPop();
			} else {
				loadSystemConfig();
			}
		}
	};
	
	 private OnClickListener priceTabListener = new OnClickListener() {
			public void onClick(View arg0) {
				popType = 2;
				keyBoardCancle();
				if(priceList != null) {
					openPricePop();
				} else {
					loadSystemConfig();
				}
			}
		};
	
	public void openPricePop() {
		if (pricePop == null) {
			pricePopFlag = 1;
			View closeView = (View)priceView.findViewById(R.id.close_view);
			closeView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					pricePop.dismiss();
					pricePopFlag = -1;
				}
			});
			pricePop = new PopupWindow(priceView, LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
			pricePop.setFocusable(false);
			pricePop.setOutsideTouchable(true);	
			pricePop.setBackgroundDrawable(new BitmapDrawable());
			pricePop.showAsDropDown(priceRl);
			pricePop.setTouchInterceptor(new OnTouchListener() {
				public boolean onTouch(View arg0, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						pricePop.dismiss();
						return true;
					}     		
					return false; 
				}     
			}); 
			pricePop.update();
		} else {	
			pricePopFlag = pricePopFlag * -1;
			if(pricePopFlag >0) {
				pricePop.showAsDropDown(priceRl);
				pricePop.update();
			} else {
				pricePop.dismiss();	
			}
		}
	}
    
	
	private PriceView.OnSelectListener priceSelectListener = new PriceView.OnSelectListener() {
		public void getValue(String name, String selectTypeId) {
			pricePopFlag = -1; 
			priceNameTv.setText(name);
			priceId = selectTypeId;
			if(pricePop != null)
				pricePop.dismiss();	
			loadPropertyFlag = 1;
			searchProperty();
		}
	};
	
	private CategoryView.OnSelectListener catetgorySelectListener = new CategoryView.OnSelectListener() {
		public void getValue(String name, String selectTypeId) {
			categoryPopFlag = -1; 
			typeNameTv.setText(name);
			categoryId = selectTypeId; 
			if(categoryPop != null)		
				categoryPop.dismiss();
			loadPropertyFlag = 1;
			searchProperty();
		}
	};
	
	private RegionView.OnSelectListener regionSelectListener = new RegionView.OnSelectListener() {
		public void getValue(String name, String selectAreaId) {
			regionPopFlag = -1;
			areaNameTv.setText(name);
			areaNameTv.setTextColor(getResources().getColorStateList(R.color.red));
			areaId = selectAreaId;
			if(regionPop != null)
				regionPop.dismiss();
			loadPropertyFlag = 1;
			searchProperty();
		}			
	};
	
	private SortView.OnSelectListener sortSelectListener = new SortView.OnSelectListener() {
		public void getValue(String name, String selectId,String type) {
			sortPopFlag = -1;
			moreNameTv.setText(name);
			moreNameTv.setTextColor(getResources().getColorStateList(R.color.red));
			sortId = selectId;
			if(sortPop != null)
				sortPop.dismiss();
			loadPropertyFlag = 1;
			searchProperty();
		}			
	};


	@Override
	protected void initData() {
		context = this.getApplicationContext();
		appContext = AppContext.getInstance();
		regionView = new RegionView( context);	
		priceView = new PriceView(context);	
		categoryView = new CategoryView(context);
		sortView = new SortView(context);
		cutPage = 0;
		offset = 0;
		totalPage = 0;
		pageSize = 10;
		propertyList = new ArrayList<Property>();
		propertyAdapter = new PropertyItemAdapter(propertyList,context);
		propertyListLv.setAdapter(propertyAdapter);
		keyBoardCancle();
		if(appContext.getSystemConfig() != null) {	
			SystemConfig systemConfig = appContext.getSystemConfig();
			if (systemConfig.getDistrict() != null) {
				regionTypeList = systemConfig.getDistrict();
				regionView.setRegiontTypeList(regionTypeList);
			}
			if (systemConfig.getPrice() != null) {
				priceList = systemConfig.getPrice();
				priceView.setPriceList(priceList);
			}
			if (systemConfig.getProperty() != null) {
				categoryList = systemConfig.getProperty();
				categoryView.setCategoryList(categoryList);
			}
			if (systemConfig.getMore() != null) {
				sortTypeList = systemConfig.getMore();
				sortView.setGroupList(sortTypeList);
			}
		}
		loadPropertyFlag = 1;
		searchProperty();
	}

	/**
	 * 加载视频失败
	 * @param data
	 */
	private void doFailedSystemConfig(String error) {
		CommonUtil.sendToast(context, error);
		dissmissProgressDialog();
	}
	
	/**
	 * 加载视频成功
	 * 
	 * @param data
	 */
	private void doSucessSystemConfig(String data) {
		SystemConfig result = JSON.parseObject(data, SystemConfig.class);
		appContext.setSystemConfig(result);
		if (result.getDistrict() != null) {
			regionTypeList = result.getDistrict();
			regionView.setRegiontTypeList(regionTypeList);
		}
		if (result.getPrice() != null) {
			priceList = result.getPrice();
			priceView.setPriceList(priceList);
		}
		if (result.getProperty() != null) {
			categoryList = result.getProperty();
			categoryView.setCategoryList(categoryList);
		}
		if (result.getMore() != null) {
			sortTypeList = result.getMore();
			sortView.setGroupList(sortTypeList);
		}
		switch (popType) {
		case 2:
			openPricePop();
			break;
		case 1:
			openRegionPop();
			break;
		case 3:
			openCategoryPop();
			break;

		}
		dissmissProgressDialog();
	}
	
	/**
	 * 加载视频
	 */
	private void loadSystemConfig() {
		if(CommonUtil.checkNetwork(context)) {
			//加载系统配置			
			showProgressDialog(null, getResources().getString(R.string.loading));
			RequestParams paramMap = new RequestParams();
			final APIAsyncTask api = new APIAsyncTask();			
			api.get(Protocol.SYSTEM_CONFIG,paramMap, new CallbackListener() {
				public void onSuccess(String data) {
					if (data != null) {		
						doSucessSystemConfig(data);			
					}		
				}

				public void onError(String error) {
					doFailedSystemConfig(error);
				}
			});
			api.execute();
		}
	}
	
	/**
	 * 搜索楼盘失败
	 * @param data
	 */
	private void doFailedSearchProperty(String error) {
		CommonUtil.sendToast(context, error);
		dissmissProgressDialog();
		propertyListLv.onRefreshComplete();
	}
	
	/**
	 *搜索楼盘成功	
	 * 
	 * @param data
	 */
	private void doSucessSearchProperty(String data) {
		PropertyListResult result = JSON.parseObject(data, PropertyListResult.class);
		if (result.getList() != null) {
			switch(loadPropertyFlag) {
			case 1:
			case 2:
				propertyList.clear();
				propertyList.addAll(result.getList());
			case 3:
				propertyList.addAll(result.getList());
			}
			propertyAdapter.notifyDataSetChanged();
			propertyListLv.onRefreshComplete();
			/**
			 * 判断有没有全部加载完
			 */
			int count = result.getCount();
			offset = (offset + pageSize);
			cutPage = cutPage + 1;
			totalPage = (count % pageSize == 0) ? count / pageSize
					: (count / pageSize + 1);
			if(cutPage == totalPage) {
				propertyListLv.setMode(Mode.PULL_FROM_START);
			} else {
				propertyListLv.setMode(Mode.BOTH);
			}
		}
		dissmissProgressDialog();
	}
	
	/**
	 * 搜索楼盘
	 */
	private void searchProperty() {
		if(CommonUtil.checkNetwork(context)) {	
			if(loadPropertyFlag == 1) {
				showProgressDialog(null, getResources().getString(R.string.loading));
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("pagesize", pageSize+"");
			map.put("offset", offset+"");
			
			RequestParams paramMap = new RequestParams();
			JSONObject json = (JSONObject) JSONObject.toJSON(map);
			//paramMap.put("data",json.toJSONString());

			final APIAsyncTask api = new APIAsyncTask();			
			api.get(Protocol.SEARCH_PROPERTY,paramMap, new CallbackListener() {
				public void onSuccess(String data) {
					if (data != null) {		
						doSucessSearchProperty(data);			
					}		
				}

				public void onError(String error) {
					doFailedSearchProperty(error);
				}
			});
			api.execute();
		}
	}

	
	@Override
	protected int getContentLayoutId() {
		return R.layout.property_list;
	}

}