package com.yifang.house.adapter.system;
import java.util.List;

import com.yifang.house.R;
import com.yifang.house.bean.Price;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PriceAdapter extends BaseAdapter {

	private Context mContext;
	private List<Price> list;
	private int selectedPos = -1;
	private int normalDrawbleId;		
	private Drawable selectedDrawble;
	private float textSize;			
	private String selectSortId;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;

	public PriceAdapter(Context context, List<Price> list, int sId, int nId) {
		//super(context, R.string.no_data, listData);
		mContext = context;
		this.list = list;			
		if(sId != 0) {
			selectedDrawble = mContext.getResources().getDrawable(sId);
		} else {
			selectedDrawble = null;
		}
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

	/**
	 * 设置选中的position,并通知列表刷新
	 */
	public void setSelectedPosition(int pos) {
		selectedPos = pos;
		selectSortId = list.get(pos).getId();
		notifyDataSetChanged();		
	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		selectSortId = list.get(pos).getId();
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		return selectedPos;	
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
		String id = "";
		String name = "";
		Price price = list.get(position);
		if(price == null)
			return null;
		id = price.getId();
		name = price.getName();
		view.setText(name);
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
		if (selectSortId != null && selectSortId.equals(id) && selectedDrawble != null) {
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
		return list.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

}
