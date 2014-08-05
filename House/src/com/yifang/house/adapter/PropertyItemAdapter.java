package com.yifang.house.adapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.yifang.house.R;
import com.yifang.house.bean.Property;
import com.yifang.house.widget.DownloadImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PropertyItemAdapter extends BaseAdapter {
	private List<Property> list;
	private LayoutInflater inflater;
	private Context mContext;
	private Map<Integer,View> viewMap;
	public PropertyItemAdapter(List<Property> list, Context context) {
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.list = list;		
		viewMap = new HashMap<Integer,View>();
	}
	
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();  
	}

	public void notifyDataSetInvalidated() {
		super.notifyDataSetInvalidated();
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = viewMap.get(position);
		if(convertView == null) {
			convertView =  inflater.inflate(R.layout.property_item, null);  
			TextView propertyNameTv = (TextView) convertView.findViewById(R.id.price_name_tv);
			TextView areaNameTv = (TextView) convertView.findViewById(R.id.area_name_tv);
			TextView addressNameTv = (TextView) convertView.findViewById(R.id.address_name_tv);
			TextView priceNameTv = (TextView) convertView.findViewById(R.id.price_name_tv);
			TextView discountNameTv = (TextView) convertView.findViewById(R.id.discount_name_tv);
			DownloadImageView propertyPicIv = (DownloadImageView)convertView.findViewById(R.id.property_pic_iv);
			final Property bean  = list.get(position);
			if(StringUtils.isNotEmpty(bean.getName())) {
				propertyNameTv.setText(bean.getName());
			}
			if(StringUtils.isNotEmpty(bean.getPrice())) {
				priceNameTv.setText(bean.getPrice());
			}
			if(StringUtils.isNotEmpty(bean.getAddress())) {
				addressNameTv.setText(bean.getAddress());
			}
			if(StringUtils.isNotEmpty(bean.getRegionName())) {
				areaNameTv.setText(bean.getRegionName());
			}
			if(StringUtils.isNotEmpty(bean.getFeaturesTxt())) {
				discountNameTv.setText(bean.getFeaturesTxt());
			}
//			if(StringUtils.isNotEmpty(bean.getLogo())) {
//				propertyPicIv.loadPic(bean.getLogo());
//			}
			viewMap.put(position, convertView);
		}
		return convertView;
	}
	
	public int getCount() {
		return list.size();
	}

	public Object getItem(int arg0) {
		return arg0;
	}

	public long getItemId(int arg0) {
		return arg0;
	}
	

}
