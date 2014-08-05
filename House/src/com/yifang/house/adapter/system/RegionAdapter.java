package com.yifang.house.adapter.system;
import java.util.List;

import com.yifang.house.R;
import com.yifang.house.bean.CityAddress;
import com.yifang.house.bean.CityArea;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RegionAdapter extends BaseAdapter {

	private Context mContext;
	private List<CityArea> regionTypeList;
	private List<CityAddress> regionList;
	private int selectedPos = -1;
	private String selectRegionId = "";
	private int normalDrawbleId;		
	private Drawable selectedDrawble;
	private float textSize;			
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;

	public RegionAdapter(Context context, List<CityArea> regionTypeList, int sId, int nId) {
		//super(context, R.string.no_data, listData);
		mContext = context;
		this.regionTypeList = regionTypeList;			
		if(sId != 0) {
			selectedDrawble = mContext.getResources().getDrawable(sId);
		} else {
			selectedDrawble = null;
		}
		normalDrawbleId = nId;

		init();
	}

	public RegionAdapter(Context context, List<CityAddress> regionList,int nId) {
		//super(context, R.string.no_data, listData);
		mContext = context;
		this.regionList = regionList;	
		selectedDrawble = null;
		normalDrawbleId = nId;
		init();
	}

	
	private void init() {
		onClickListener = new OnClickListener() {
			public void onClick(View view) {
				selectedPos = (Integer) view.getTag();
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};						
	}

//	public RegionAdapter(Context context, Region[] regions, int sId, int nId) {
//		//super(context, R.string.no_data, arrayData);
//		mContext = context;
//		this.regions = regions;			
//		if(sId != 0) {
//			selectedDrawble = mContext.getResources().getDrawable(sId);
//		} else {
//			selectedDrawble = null;						
//		}
//		normalDrawbleId = nId;			
//		init();
//	}

	/**
	 * 设置选中的position,并通知列表刷新
	 */
	public void setSelectedPosition(int pos) {
		if (regionTypeList != null && pos < regionTypeList.size()) {
			selectedPos = pos;
			selectRegionId = regionTypeList.get(pos).getId()+"";
			notifyDataSetChanged();		
		} else if (regionList != null && pos < regionList.size()) {
			selectedPos = pos;
			selectRegionId = regionList.get(pos).getId()+"";
			notifyDataSetChanged();
		}

	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (regionTypeList != null && pos < regionTypeList.size()) {
			selectRegionId = regionTypeList.get(pos).getId()+"";
		} else if (regionList != null && pos < regionList.size()) {
			selectRegionId = regionList.get(pos).getId()+"";
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		if (regionList != null && selectedPos < regionList.size()) {
			return selectedPos;			
		}
		if (regionTypeList != null && selectedPos < regionTypeList.size()) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * 设置列表字体大小
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if (convertView == null) {
			view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.choose_item, parent, false);
		} else {
			view = (TextView) convertView;
		}
		view.setTag(position);
		String regionId = "";
		String regionName = "";
		if (regionTypeList != null) {
			if (position < regionTypeList.size()) {
				CityArea regionType = regionTypeList.get(position);
				regionId = regionType.getId();
				regionName= regionType.getName();
			}
		} else if (regionList != null) {
			if (position < regionList.size()) {
				CityAddress region = regionList.get(position);
				regionName = region.getName();
				regionId = region.getId();
			}
		}
		view.setText(regionName);
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
		if (selectRegionId != null && selectRegionId.equals(regionId) && selectedDrawble != null) {
			view.setBackgroundDrawable(selectedDrawble);//设置选中的背景图片
		} else {
			view.setBackgroundDrawable(mContext.getResources().getDrawable(normalDrawbleId));//设置未选中状态背景图片
		}
		view.setPadding(20, 0, 0, 0);
		view.setOnClickListener(onClickListener);
		return view;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

	public int getCount() {
		if(regionTypeList != null)
			return regionTypeList.size();
		else 
			return regionList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

}
