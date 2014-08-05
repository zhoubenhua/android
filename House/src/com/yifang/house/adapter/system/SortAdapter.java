package com.yifang.house.adapter.system;
import java.util.List;
import com.yifang.house.R;
import com.yifang.house.bean.SortChild;
import com.yifang.house.bean.SortType;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter {

	private Context mContext;
	private List<SortType> groupList;
	private List<SortChild> childList;
	private int selectedPos = -1;
	private String selectId = "";
	private int normalDrawbleId;		
	private Drawable selectedDrawble;
	private float textSize;			
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;

	public SortAdapter(Context context, List<SortType> groupList, int sId, int nId) {
		//super(context, R.string.no_data, listData);
		mContext = context;
		this.groupList = groupList;			
		if(sId != 0) {
			selectedDrawble = mContext.getResources().getDrawable(sId);
		} else {
			selectedDrawble = null;
		}
		normalDrawbleId = nId;

		init();
	}

	public SortAdapter(Context context, List<SortChild> childList,int nId) {
		//super(context, R.string.no_data, listData);
		mContext = context;
		this.childList = childList;	
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
		if (groupList != null && pos < groupList.size()) {
			selectedPos = pos;
			selectId = groupList.get(pos).getId()+"";
			notifyDataSetChanged();		
		} else if (childList != null && pos < childList.size()) {
			selectedPos = pos;
			selectId = childList.get(pos).getId()+"";
			notifyDataSetChanged();
		}

	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (groupList != null && pos < groupList.size()) {
			selectId = groupList.get(pos).getId()+"";
		} else if (childList != null && pos < childList.size()) {
			selectId = childList.get(pos).getId()+"";
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		if (childList != null && selectedPos < childList.size()) {
			return selectedPos;			
		}
		if (groupList != null && selectedPos < groupList.size()) {
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
		if (groupList != null) {
			if (position < groupList.size()) {
				SortType bean = groupList.get(position);
				regionId = bean.getId();
				regionName= bean.getName();
			}
		} else if (childList != null) {
			if (position < childList.size()) {
				SortChild bean = childList.get(position);
				regionName = bean.getName();
				regionId = bean.getId();
			}
		}
		view.setText(regionName);
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
		if (selectId != null && selectId.equals(regionId) && selectedDrawble != null) {
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
		if(groupList != null)
			return groupList.size();
		else 
			return childList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

}
