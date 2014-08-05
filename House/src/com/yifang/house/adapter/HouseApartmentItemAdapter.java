package com.yifang.house.adapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.yifang.house.R;
import com.yifang.house.bean.HouseApartment;
import com.yifang.house.bean.Property;
import com.yifang.house.widget.DownloadImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 主户力型
 * @author Administrator
 *
 */
public class HouseApartmentItemAdapter extends BaseAdapter {
	private List<HouseApartment> list;
	private LayoutInflater inflater;
	private Context mContext;
	private Map<Integer,View> viewMap;
	public HouseApartmentItemAdapter(List<HouseApartment> list, Context context) {
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
			convertView =  inflater.inflate(R.layout.house_apartment_item, null);  
			DownloadImageView apartmentPicIv = (DownloadImageView) convertView.findViewById(R.id.apartment_pic_gv);
			TextView apartmentNameTv = (TextView) convertView.findViewById(R.id.apartment_name_tv);
			TextView apartmentAreaTv = (TextView) convertView.findViewById(R.id.apartment_area_tv);
			TextView picTitleTv = (TextView) convertView.findViewById(R.id.picture_title_tv);
			final HouseApartment bean  = list.get(position);
			if(StringUtils.isNotEmpty(bean.getArea())) {
				apartmentAreaTv.setText(bean.getArea());
			}
			if(StringUtils.isNotEmpty(bean.getHuxing())) {
				apartmentNameTv.setText(bean.getHuxing());
			}
			if(StringUtils.isNotEmpty(bean.getTitle())) {
				picTitleTv.setText(bean.getTitle());
			}
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
