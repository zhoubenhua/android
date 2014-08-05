package com.yifang.house.ui.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yifang.house.R;
import com.yifang.house.adapter.BbsItemAdapter;
import com.yifang.house.adapter.CarrerCounselorItemAdapter;
import com.yifang.house.adapter.HouseApartmentItemAdapter;
import com.yifang.house.api.APIAsyncTask;
import com.yifang.house.api.CallbackListener;
import com.yifang.house.api.Protocol;
import com.yifang.house.bean.Bbs;
import com.yifang.house.bean.CarretCounselor;
import com.yifang.house.bean.HouseApartment;
import com.yifang.house.bean.PropertyDetail;
import com.yifang.house.bean.PropertyListResult;
import com.yifang.house.common.CommonUtil;
import com.yifang.house.common.Constant;
import com.yifang.house.http.RequestParams;
import com.yifang.house.ui.BaseActivity;
import com.yifang.house.widget.DownloadImageView;
import com.yifang.house.widget.MyListView;
import com.yifang.house.widget.pullrefresh.PullToRefreshBase.Mode;

public class PropertyDetailActivity extends BaseActivity {
	private DownloadImageView propertyPicIv;
	/**
	 * 楼盘基本信息
	 */
	private TextView bbsCountIv;
	private TextView propertyNameTv;
	private TextView propertyPriceTv;
	private LinearLayout rangeLl;
	private TextView rangeTv;
	private LinearLayout typeLl;
	private TextView typeTv;
	private TextView openTv;
	private LinearLayout openLl;
	private TextView featureTitleTv;
	private LinearLayout featureLl;
	/**
	 * 看房优惠
	 */
	private TextView discountTimeTv;
	private LinearLayout discountLl;
	private TextView discountTitleTv;
	/**
	 * 看房团购
	 */
	private TextView groupTimeTv;
	private LinearLayout groupLl;
	private TextView groupTitleTv;
	/**
	 * 最新动态			
	 */
	private LinearLayout dynamicLl;
	private DownloadImageView dynamicPicIv;
	private TextView dynamicTitleTv;
	private TextView dynamicContentTv;
	/**
	 * 周边
	 */
	private LinearLayout aroundLl;
	private TextView addressTv;
	private Button busBt;
	private Button schoolBt;
	private Button superBt;
	private Button repastBt;
	private Button medicalBt;
	private TextView areaContentTv;
	/**
	 * 户型
	 */
	private LinearLayout apartmentLl;
	private GridView apartmentPicGv;
	private HorizontalScrollView aparementHv;
	/**
	 * 楼盘参数
	 */
	private LinearLayout propertyParametersLl;
	private TextView openTimeTv;
	private TextView tradTimeTv;
	private TextView buildAreaTv;
	private TextView useAreaTv;
	private TextView rongjilvTv;
	private TextView houseNumTv;
	private TextView parkNumTv;
	private TextView propertyYearsTv;
	private TextView decorationTv;
	private TextView propertyCostsTv;
	private TextView delopersTv;
	private TextView propertyCompanyTv;
	private TextView constructionUnnitTv;
	/**
	 * 楼盘介绍
	 */
	private TextView propertyDetailTv;
	private LinearLayout propertyDetailLl;
	/**
	 * 楼盘特色
	 */
	private LinearLayout propertyFeaturesLl;
	private TextView propertyFeaturesTv;
	/**
	 * 价格走势
	 */
	private LinearLayout priceThrendsLl;			
	private DownloadImageView priceThrendsPicIv;	
	/**
	 * 业主论坛
	 */
	private LinearLayout bbsLl;
	private MyListView bbsLv;
	/**
	 * 职业顾问
	 */
	private LinearLayout careerCounselorLl;
	private MyListView careerCounselorLv;
	/**
	 * 其他
	 */
	private ImageView samePricePropertyIv;
	private ImageView nearPropertyIv;  
	private ImageView recommendPropertyIv;
	private ImageView nearLookIv;
	
	private Context context;
	private PropertyDetail propertyDetail;
	private String id;
	

	@Override
	protected int getContentLayoutId() {
		return R.layout.property_detail;
	}

	private void setApartmentData(List<HouseApartment> apartmentList) {
		int horizontalSpacing = (int)getResources().getDimension(R.dimen.dimen_7_dip);
		int width = (int)getResources().getDimension(R.dimen.dimen_123_dip);
		LayoutParams params = new LayoutParams(apartmentList.size() * (width+horizontalSpacing), 
				LayoutParams.WRAP_CONTENT);  
		apartmentPicGv.setLayoutParams(params);  
		apartmentPicGv.setColumnWidth(width);  
		apartmentPicGv.setHorizontalSpacing(horizontalSpacing);  
		apartmentPicGv.setStretchMode(GridView.NO_STRETCH);  
		apartmentPicGv.setNumColumns(apartmentList.size());  
		HouseApartmentItemAdapter houseApartmentAdapter = new HouseApartmentItemAdapter(apartmentList,context);
		apartmentPicGv.setAdapter(houseApartmentAdapter);
	}
	
	@Override
	protected void initViews() {
		propertyPicIv = (DownloadImageView)findViewById(R.id.property_pic_iv);
		
		bbsCountIv = (TextView)findViewById(R.id.bbs_count_tv);
		propertyNameTv = (TextView)findViewById(R.id.property_name_tv);
		propertyPriceTv = (TextView)findViewById(R.id.property_price_tv);
		rangeLl = (LinearLayout)findViewById(R.id.range_ll);
		rangeTv = (TextView)findViewById(R.id.range_tv);
		typeLl = (LinearLayout)findViewById(R.id.type_ll);
		typeTv = (TextView)findViewById(R.id.type_tv);
		openLl = (LinearLayout)findViewById(R.id.open_ll);
		openTv = (TextView)findViewById(R.id.open_tv);				
		featureLl = (LinearLayout)findViewById(R.id.feature_ll);
		featureTitleTv = (TextView)findViewById(R.id.feature_title_tv);
		
		discountLl = (LinearLayout)findViewById(R.id.discount_ll);
		discountTitleTv = (TextView)findViewById(R.id.discount_title_tv);
		discountTimeTv = (TextView)findViewById(R.id.discount_time_tv);
		
		groupLl = (LinearLayout)findViewById(R.id.group_ll);
		groupTitleTv = (TextView)findViewById(R.id.group_title_tv);
		groupTimeTv = (TextView)findViewById(R.id.group_time_tv);
		
		dynamicLl = (LinearLayout)findViewById(R.id.dynamic_ll);
		dynamicPicIv = (DownloadImageView)findViewById(R.id.dynamic_pic_iv);
		dynamicTitleTv = (TextView)findViewById(R.id.dynamic_title_tv);
		dynamicContentTv = (TextView)findViewById(R.id.dynamic_content_tv);
		
		aroundLl = (LinearLayout)findViewById(R.id.around_ll);
		addressTv = (TextView)findViewById(R.id.address_tv);
		busBt = (Button)findViewById(R.id.bus_bt);
		schoolBt = (Button)findViewById(R.id.school_bt);
		superBt = (Button)findViewById(R.id.super_bt);			
		repastBt = (Button)findViewById(R.id.repast_bt);
		medicalBt = (Button)findViewById(R.id.medical_bt);
		areaContentTv = (TextView)findViewById(R.id.area_content_tv);
		
		apartmentLl = (LinearLayout)findViewById(R.id.apartment_ll);
		apartmentPicGv = (GridView)findViewById(R.id.apartment_pic_gv);
		aparementHv = (HorizontalScrollView)findViewById(R.id.apartment_hv);
		
		propertyParametersLl = (LinearLayout)findViewById(R.id.property_parameters_ll);
		openTimeTv = (TextView)findViewById(R.id.open_time_tv);
		tradTimeTv = (TextView)findViewById(R.id.trad_time_tv);
		buildAreaTv = (TextView)findViewById(R.id.build_area_tv);
		useAreaTv = (TextView)findViewById(R.id.use_area_tv);			
		rongjilvTv = (TextView)findViewById(R.id.rongjilv_tv);
		houseNumTv = (TextView)findViewById(R.id.house_num_tv);
		parkNumTv = (TextView)findViewById(R.id.park_num_tv);
		propertyYearsTv = (TextView)findViewById(R.id.property_years_tv);
		decorationTv = (TextView)findViewById(R.id.decoration_tv);
		propertyCostsTv = (TextView)findViewById(R.id.property_costs_tv);
		delopersTv = (TextView)findViewById(R.id.developers_tv);			
		propertyCompanyTv = (TextView)findViewById(R.id.property_company_tv);
		constructionUnnitTv = (TextView)findViewById(R.id.construction_unnit_tv);
		
		propertyDetailTv = (TextView)findViewById(R.id.property_detail_tv);
		propertyDetailLl = (LinearLayout)findViewById(R.id.property_detail_ll);			
		
		propertyFeaturesLl = (LinearLayout)findViewById(R.id.property_features_ll);		
		propertyFeaturesTv = (TextView)findViewById(R.id.property_features_tv);	
		
		priceThrendsLl = (LinearLayout)findViewById(R.id.price_trends_ll);
		priceThrendsPicIv = (DownloadImageView)findViewById(R.id.price_thrends_pic_iv);
		
		bbsLl = (LinearLayout)findViewById(R.id.bbs_ll);
		bbsLv = (MyListView)findViewById(R.id.bbs_lv);
		
		careerCounselorLl = (LinearLayout)findViewById(R.id.career_counselor_ll);
		careerCounselorLv = (MyListView)findViewById(R.id.career_counselor_lv);
		
		samePricePropertyIv = (ImageView)findViewById(R.id.same_price_propery_iv);
		nearPropertyIv = (ImageView)findViewById(R.id.near_propery_iv);
		recommendPropertyIv = (ImageView)findViewById(R.id.recommend_propery_iv);
		nearLookIv = (ImageView)findViewById(R.id.near_look_iv);
		
	}
	
	private void setBbsData(List<Bbs> bbsList) {
		BbsItemAdapter bbsAdapter = new BbsItemAdapter(bbsList,context);
		bbsLv.setAdapter(bbsAdapter);
	}
	
	private void setCarrerCounselorData(List<CarretCounselor> carrerList) {
		CarrerCounselorItemAdapter bbsAdapter = new CarrerCounselorItemAdapter(carrerList,context);
		careerCounselorLv.setAdapter(bbsAdapter);
	}
	
	/**
	 * 加载楼盘详情失败
	 * @param data
	 */
	private void doFailedPropertyDetail(String error) {
		CommonUtil.sendToast(context, error);
		dissmissProgressDialog();
		
	}
	
	/**
	 * 楼盘特色信息
	 */
	private void setPropertyFeaturesInfo() {
		if(StringUtils.isNotEmpty(propertyDetail.getFeatures())) {
			propertyFeaturesTv.setText(propertyDetail.getFeatures());
		}
	}
	
	/**
	 * 楼盘参数信息
	 */
	private void setPropertyParametersInfo() {
		if(StringUtils.isNotEmpty(propertyDetail.getOpentime())) {
			openTimeTv.setText(propertyDetail.getOpentime());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getBuildArea())) {
			buildAreaTv.setText(propertyDetail.getBuildArea());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getYongdiArea())) {
			useAreaTv.setText(propertyDetail.getYongdiArea());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getRongjilv())) {
			rongjilvTv.setText(propertyDetail.getRongjilv());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getHouseNum())) {
			houseNumTv.setText(propertyDetail.getHouseNum());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getParkNum())) {
			parkNumTv.setText(propertyDetail.getParkNum());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getBuildCompany())) {
			propertyCompanyTv.setText(propertyDetail.getBuildCompany());
		}
	}
	
	/**
	 * 楼盘基本信息
	 */
	private void setPropertyBaseInfo() {
		if(StringUtils.isNotEmpty(propertyDetail.getAreaRange())) {
			rangeTv.setText(propertyDetail.getAreaRange());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getAveragePrice())) {
			propertyPriceTv.setText(propertyDetail.getAveragePrice());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getFloorName())) {
			propertyNameTv.setText(propertyDetail.getFloorName());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getPropertyTypeList())) {
			typeTv.setText(propertyDetail.getPropertyTypeList());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getOpenTime())) {
			openTv.setText(propertyDetail.getOpenTime());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getFeatures())) {
			featureTitleTv.setText(propertyDetail.getFeatures());
		}
	}
	
	/**
	 * 楼盘介绍信息
	 */
	private void setPropertyDetailInfo() {
		if(StringUtils.isNotEmpty(propertyDetail.getOverview())) {
			propertyDetailTv.setText(propertyDetail.getOverview());
		}
	}
	
	/**
	 * 动态信息
	 */
	private void setPropertyDynamicInfo() { 
		if(StringUtils.isNotEmpty(propertyDetail.getNewstitle())) {
			dynamicTitleTv.setText(propertyDetail.getNewstitle());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getNewscontent())) {
			dynamicContentTv.setText(propertyDetail.getNewscontent());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getNewsurl())) {
			dynamicPicIv.loadPic(propertyDetail.getNewsurl());
		}
	}
	
	/**
	 * 设置价格走势信息
	 */
	private void setPriceThrendsInfo() {
		
	}
	
	/**
	 * 设置周边信息
	 */
	private void setAroundInfo() {
		
	}
	
	/**
	 * 设置业主论坛信息
	 */
	private void setBbsInfo(List<Bbs> bbsList) {
		BbsItemAdapter bbsAdapter = new BbsItemAdapter(bbsList,context);
		bbsLv.setAdapter(bbsAdapter);
	}
	
	/**
	 * 设置主户力型信息
	 */
	private void setHouseApartmentInfo() {
		if(propertyDetail.getHuxingInfo() != null) {
			List<HouseApartment> apartmentList = propertyDetail.getHuxingInfo();
			int horizontalSpacing = (int)getResources().getDimension(R.dimen.dimen_7_dip);
			int width = (int)getResources().getDimension(R.dimen.dimen_123_dip);
			LayoutParams params = new LayoutParams(apartmentList.size() * (width+horizontalSpacing), 
					LayoutParams.WRAP_CONTENT);  
			apartmentPicGv.setLayoutParams(params);  
			apartmentPicGv.setColumnWidth(width);  
			apartmentPicGv.setHorizontalSpacing(horizontalSpacing);  
			apartmentPicGv.setStretchMode(GridView.NO_STRETCH);  
			apartmentPicGv.setNumColumns(apartmentList.size());  
			HouseApartmentItemAdapter houseApartmentAdapter = new HouseApartmentItemAdapter(apartmentList,context);
			apartmentPicGv.setAdapter(houseApartmentAdapter);
		}
	}
	
	
	/**
	 * 加载楼盘详情成功
	 * 
	 * @param data
	 */
	private void doSucessPropertyDetail(String data) {
		propertyDetail = JSON.parseObject(data, PropertyDetail.class);
		setPropertyBaseInfo();
		setDiscountInfo();
		setPropertyDynamicInfo();
		setHouseApartmentInfo();
		setPropertyParametersInfo();
		setPropertyDetailInfo();
		dissmissProgressDialog();
	}
	
	
	/**
	 * 设置优惠信息
	 */
	private void setDiscountInfo() {
		if(StringUtils.isNotEmpty(propertyDetail.getTitle())) {
			discountTitleTv.setText(propertyDetail.getTitle());
		}
		if(StringUtils.isNotEmpty(propertyDetail.getEnd_time())) {
			discountTimeTv.setText(propertyDetail.getEnd_time());
		}
	}
	
	/**
	 * 设置
	 */
	
	/**
	 * 加载楼盘详情
	 */
	private void loadPropertyDetail() {
		if(CommonUtil.checkNetwork(context)) {	
			showProgressDialog(null, getResources().getString(R.string.loading));
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("id", id);
			//org.json.JSONObject json = new org.json.JSONObject(map);
			RequestParams paramMap = new RequestParams();
			JSONObject json = new JSONObject();
			json.put("id", id);
			paramMap.put("data",json.toString());
			final APIAsyncTask api = new APIAsyncTask();			
			api.get(Protocol.PROPERTY_DETAIL,paramMap, new CallbackListener() {
				public void onSuccess(String data) {
					if (data != null) {		
						doSucessPropertyDetail(data);			
					}		
				}		

				public void onError(String error) {
					doFailedPropertyDetail(error);
				}
			});
			api.execute();
		}
	}

	@Override
	protected void initData() {
		context = this.getApplicationContext();
		id = this.getIntent().getStringExtra(Constant.PROPERTY_ID);
		loadPropertyDetail();
		List<HouseApartment> houseApartmentList = new ArrayList<HouseApartment>();
		HouseApartment houseApartment = new HouseApartment();
		houseApartmentList.add(houseApartment);
		houseApartmentList.add(houseApartment);
		houseApartmentList.add(houseApartment);
		houseApartmentList.add(houseApartment);
		setApartmentData(houseApartmentList);
		
		List<Bbs> bbsList = new ArrayList<Bbs>();
		Bbs bbs = new Bbs();
		bbsList.add(bbs);
		bbsList.add(bbs);
		bbsList.add(bbs);
		bbsList.add(bbs);
		setBbsData(bbsList);
		
		List<CarretCounselor> carrerList = new ArrayList<CarretCounselor>();
		CarretCounselor carrer = new CarretCounselor();
		carrerList.add(carrer);
		carrerList.add(carrer);
		carrerList.add(carrer);
		carrerList.add(carrer);
		setCarrerCounselorData(carrerList);
	}

	@Override
	protected void addListeners() {
		// TODO Auto-generated method stub
		super.addListeners();
	}
	

}
