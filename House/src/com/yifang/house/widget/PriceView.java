package com.yifang.house.widget;
import java.util.List;

import com.yifang.house.R;
import com.yifang.house.adapter.system.PriceAdapter;
import com.yifang.house.bean.Price;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;				
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;										
public class PriceView extends RelativeLayout{
	private ListView mListView;
	private List<Price> list;
	private OnSelectListener mOnSelectListener;
	private PriceAdapter adapter;
	private int selectPosition;
	private Context context;


	public PriceView(Context context) {
		super(context);
		this.context = context;
	}
	
	public void setPriceList(List<Price> list) {
		this.list = list;
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_sort, this, true);
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.area_pop_bg));
		mListView = (ListView) findViewById(R.id.listView);
		this.setBackgroundColor(getResources().getColor(R.color.white_70));
		adapter = new PriceAdapter(context, list, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
		adapter.setTextSize(17);
//		ViewGroup.MarginLayoutParams params = (MarginLayoutParams)mListView.getLayoutParams();
//		int height = (int)context.getResources().getDimension(R.dimen.dimen_260_dip);
//		params.height = height;
//		mListView.setLayoutParams(params);
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new PriceAdapter.OnItemClickListener() {
			public void onItemClick(View view, int position) {
				selectPosition = position;
				if (mOnSelectListener != null) {
					Price price = list.get(selectPosition);
					mOnSelectListener.getValue(price.getName(), price.getId());
				}
			}
		});
		setDefaultSelect();
	}
	
	public void setDefaultSelect() {
		mListView.setSelection(selectPosition);
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String sortName, String sortId);
	}

}
