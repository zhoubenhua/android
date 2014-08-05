package com.yifang.house.adapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.yifang.house.R;
import com.yifang.house.bean.Bbs;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 业主论坛
 * @author Administrator
 *
 */
public class BbsItemAdapter extends BaseAdapter {
	private List<Bbs> list;			
	private LayoutInflater inflater;			
	private Context mContext;				
	private Map<Integer,View> viewMap;
	public BbsItemAdapter(List<Bbs> list, Context context) {
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
			convertView =  inflater.inflate(R.layout.bbs_item, null);  
			TextView authorNameTv = (TextView)convertView.findViewById(R.id.author_name_tv);
			TextView bbsSubjectTv = (TextView)convertView.findViewById(R.id.bbs_subject_tv);
			final Bbs bean  = list.get(position);
			if(StringUtils.isNotEmpty(bean.getAuthor())) {
				authorNameTv.setText(bean.getAuthor());
			}
			if(StringUtils.isNotEmpty(bean.getSubject())) {
				bbsSubjectTv.setText(bean.getSubject());
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
