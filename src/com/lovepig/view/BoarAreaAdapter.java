package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.model.BoarAreaModel;

public class BoarAreaAdapter extends BaseAdapter {
	private ArrayList<BoarAreaModel> list;
	private Context context;
	private LayoutInflater inflater = null;
	private static int currentPostion = -1;

	public BoarAreaAdapter(ArrayList<BoarAreaModel> list,
			Context context) {
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
			convertView = inflater.inflate(R.layout.common_list_item,
					null);
			holder.cateName = (TextView) convertView.findViewById(R.id.categorizationName);
			holder.cateImg = (ImageView) convertView.findViewById(R.id.cateImg);
			holder.pigName = (TextView) convertView.findViewById(R.id.pigName);
			holder.pigDesc = (TextView) convertView.findViewById(R.id.pigDesc);
			holder.gradebar = (RatingBar) convertView
					.findViewById(R.id.gradebar);
			holder.brandInfo = (TextView) convertView
					.findViewById(R.id.brandInfo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.cateName.setText(list.get(position).area);
		// holder.cateImg.s
		holder.pigName.setText(list.get(position).boarName);
		holder.pigDesc.setText(list.get(position).info);
		holder.gradebar.setRating(list.get(position).star);
		holder.brandInfo.setText(list.get(position).brandInfo);
		return convertView;
	}

	public void setPositionNum(int num) {
		currentPostion = num;
	}

	public final class ViewHolder {
		public TextView cateName;
		public ImageView cateImg;
		public TextView pigName;
		public TextView pigDesc;
		public RatingBar gradebar;
		public TextView brandInfo;
	}

}