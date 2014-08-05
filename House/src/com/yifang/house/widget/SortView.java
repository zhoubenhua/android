package com.yifang.house.widget;
import java.util.LinkedList;
import java.util.List;
import com.yifang.house.R;
import com.yifang.house.adapter.system.SortAdapter;
import com.yifang.house.bean.SortChild;
import com.yifang.house.bean.SortType;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
public class SortView extends LinearLayout{
	
	private ListView regionListView;
	private ListView plateListView;
	private List<SortType> groups;
	private LinkedList<SortChild> childrenItem = new LinkedList<SortChild>();
	private SparseArray<LinkedList<SortChild>> children = new SparseArray<LinkedList<SortChild>>();
	private SortAdapter groupAdapter;
	private SortAdapter childAdapter;			
	private OnSelectListener mOnSelectListener;
	private int groupPosition = 0;
	private int childPosition = 0;
	private String name = "";
	private String id = "";
	private Context context;

	public SortView(Context context) {
		super(context);
		this.context = context;
	}
	
	public void setGroupList(List<SortType> groups) {
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
				SortType bean = groups.get(i);
				if(bean.getMenu() != null && bean.getMenu().size() >0) {
					LinkedList<SortChild> items = new LinkedList<SortChild>();
					for(SortChild child : bean.getMenu()) {
						items.add(child);
					}
					children.put(i, items);
				}
			}
			ViewGroup.MarginLayoutParams params = (MarginLayoutParams)regionListView.getLayoutParams();
			int height = (int)context.getResources().getDimension(R.dimen.dimen_260_dip);
			params.height = height;
			regionListView.setLayoutParams(params);
			plateListView.setLayoutParams(params);
			groupAdapter = new SortAdapter(context, groups,
					R.drawable.choose_item_selected,
					R.drawable.choose_eara_item_selector);
			groupAdapter.setTextSize(17);
			groupAdapter.setSelectedPositionNoNotify(groupPosition);
			regionListView.setAdapter(groupAdapter);
			groupAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
						public void onItemClick(View view, int position) {
							groupPosition = position;
							if(children.indexOfKey(groupPosition) != -1) {
								LinkedList<SortChild> items = children.get(groupPosition);
								childrenItem.clear();
								if(items != null) {
									childrenItem.addAll(items);
								}
								childAdapter.notifyDataSetChanged();
							} else {
								childrenItem.clear();
								childAdapter.notifyDataSetChanged();
								SortType bean = groups.get(groupPosition);
								name = bean.getName();
								id = bean.getId()+"";
								if (mOnSelectListener != null) {
									mOnSelectListener.getValue(name,id,id);
								}
							}
							
						}
					});
			if(children.indexOfKey(groupPosition) != -1) {
				LinkedList<SortChild> items = children.get(groupPosition);
				if(items != null)
					childrenItem.addAll(items);
			}
				
			childAdapter = new SortAdapter(context, childrenItem,R.drawable.choose_plate_item_selector);
			childAdapter.setTextSize(15);
			childAdapter.setSelectedPositionNoNotify(childPosition);
			plateListView.setAdapter(childAdapter);
			childAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
						public void onItemClick(View view, final int position) {
							SortChild bean = childrenItem.get(position);
							name = bean.getName();
							id = bean.getId();
							if (mOnSelectListener != null) {
								mOnSelectListener.getValue(name,id,bean.getType());
							}
						}
					});
			if (childPosition < childrenItem.size()) {
				SortChild bean = childrenItem.get(childPosition);
				if(bean != null) {
					name = bean.getName();
					id = bean.getId();
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
//		return name;
//	}			

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String name,String id,String type);
	}
}
