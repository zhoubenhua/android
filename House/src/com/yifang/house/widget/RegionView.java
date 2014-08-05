package com.yifang.house.widget;
import java.util.LinkedList;
import java.util.List;
import com.yifang.house.R;
import com.yifang.house.adapter.system.RegionAdapter;
import com.yifang.house.bean.CityAddress;
import com.yifang.house.bean.CityArea;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class RegionView extends LinearLayout{
	
	private ListView regionListView;
	private ListView plateListView;
	private List<CityArea> groups;
	private LinkedList<CityAddress> childrenItem = new LinkedList<CityAddress>();
	private SparseArray<LinkedList<CityAddress>> children = new SparseArray<LinkedList<CityAddress>>();
	private RegionAdapter regionGroupAdapter;
	private RegionAdapter regionChildAdapter;			
	private OnSelectListener mOnSelectListener;
	private int groupPosition = 0;
	private int childPosition = 0;
	private String areaName = "";
	private String areaId = "";
	private Context context;

	public RegionView(Context context) {
		super(context);
		this.context = context;
	}
	
	public void setRegiontTypeList(List<CityArea> groups) {
		this.groups = groups;
		init();																	
	}						
				
//	
//
//	public RegionView(Context context, AttributeSet attrs,ArrayList<RegionType> groups) {
//		super(context, attrs);
//		this.groups = groups;
//		init(context);		
//	}

	private void init() {
		try {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.view_region, this, true);
			regionListView = (ListView) findViewById(R.id.listView);
			plateListView = (ListView) findViewById(R.id.listView2);
			this.setBackgroundColor(getResources().getColor(R.color.white_70));
			for(int i=0;i<groups.size();i++) {
				CityArea regionType = groups.get(i);
				if(regionType.getMenu() != null && regionType.getMenu().size() >0) {
					LinkedList<CityAddress> items = new LinkedList<CityAddress>();
					for(CityAddress region : regionType.getMenu()) {
						items.add(region);
					}
					children.put(i, items);
				}
			}
			ViewGroup.MarginLayoutParams params = (MarginLayoutParams)regionListView.getLayoutParams();
			int height = (int)context.getResources().getDimension(R.dimen.dimen_260_dip);
			params.height = height;
			regionListView.setLayoutParams(params);
			plateListView.setLayoutParams(params);
			regionGroupAdapter = new RegionAdapter(context, groups,
					R.drawable.choose_item_selected,
					R.drawable.choose_eara_item_selector);
			regionGroupAdapter.setTextSize(17);
			regionGroupAdapter.setSelectedPositionNoNotify(groupPosition);
			regionListView.setAdapter(regionGroupAdapter);
			regionGroupAdapter.setOnItemClickListener(new RegionAdapter.OnItemClickListener() {
						public void onItemClick(View view, int position) {
							groupPosition = position;
							if(children.indexOfKey(groupPosition) != -1) {
								LinkedList<CityAddress> items = children.get(groupPosition);
								childrenItem.clear();
								if(items != null) {
									childrenItem.addAll(items);
								}
								regionChildAdapter.notifyDataSetChanged();
							} else {
								childrenItem.clear();
								regionChildAdapter.notifyDataSetChanged();
								CityArea regionType = groups.get(groupPosition);
								areaName = regionType.getName();
								areaId = regionType.getId()+"";
								if (mOnSelectListener != null) {
									mOnSelectListener.getValue(areaName,areaId);
								}
							}
							
						}
					});
			if(children.indexOfKey(groupPosition) != -1) {
				LinkedList<CityAddress> items = children.get(groupPosition);
				if(items != null)
					childrenItem.addAll(items);
			}
				
			regionChildAdapter = new RegionAdapter(context, childrenItem,R.drawable.choose_plate_item_selector);
			regionChildAdapter.setTextSize(15);
			regionChildAdapter.setSelectedPositionNoNotify(childPosition);
			plateListView.setAdapter(regionChildAdapter);
			regionChildAdapter.setOnItemClickListener(new RegionAdapter.OnItemClickListener() {
						public void onItemClick(View view, final int position) {
							CityAddress region = childrenItem.get(position);
							areaName = region.getName();
							areaId = region.getId();
							if (mOnSelectListener != null) {
								mOnSelectListener.getValue(areaName,areaId);
							}
						}
					});
			if (childPosition < childrenItem.size()) {
				CityAddress region = childrenItem.get(childPosition);
				if(region != null) {
					areaName = region.getName();
					areaId = region.getId();
				}			
			}
			setDefaultSelect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setDefaultSelect() {
		regionListView.setSelection(groupPosition);
		plateListView.setSelection(childPosition);
	}

//	public String getShowText() {
//		return areaName;
//	}			

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String areaName,String areaId);
	}
}
