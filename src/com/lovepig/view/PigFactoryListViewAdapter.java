package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.model.PigFactoryModel;

public class PigFactoryListViewAdapter extends BaseAdapter {
	private ArrayList<PigFactoryModel> list;
	private Context context;
	private LayoutInflater inflater = null;
	private static int currentPostion = -1;

	public PigFactoryListViewAdapter(ArrayList<PigFactoryModel> list, Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.pigfactory_list_item, null);
			holder.pigFactoryName = (TextView) convertView.findViewById(R.id.pigFactoryName);
			holder.pigFactoryDesc = (TextView) convertView.findViewById(R.id.pigFactoryDesc);
			holder.pigFactoryBrandInfo = (TextView)convertView.findViewById(R.id.pigFactoryBrandInfo);
			holder.pigFactoryGradebar = (RatingBar)convertView.findViewById(R.id.pigFactoryGradebar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.pigFactoryName.setText(list.get(position).pigFactoryName);
		holder.pigFactoryDesc.setText(list.get(position).pigFactoryDesc);
		holder.pigFactoryBrandInfo.setText(list.get(position).pigFactoryBrandInfo);
		holder.pigFactoryGradebar.setRating(list.get(position).pigFactoryGradebarNum);
		return convertView;
	}

	public void setPositionNum(int num) {
		currentPostion = num;
	}

	public final class ViewHolder {
		public TextView pigFactoryName;
		public TextView pigFactoryDesc;
		public RatingBar pigFactoryGradebar;
		public TextView pigFactoryBrandInfo;
	}

}