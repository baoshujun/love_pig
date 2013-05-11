package com.lovepig.view;
/**
 * 种猪根据品牌分类的adapter
 */

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.model.BoarBrandModel;

public class BoarBrandAdapter extends BaseAdapter {
	private ArrayList<BoarBrandModel> list;
	private Context context;
	private LayoutInflater inflater = null;
	private static int currentPostion = -1;

	public BoarBrandAdapter(ArrayList<BoarBrandModel> list, Context context) {
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
			convertView = inflater.inflate(R.layout.common_two_button_item, null);
			holder.text = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setText(list.get(position).brandName);
		return convertView;
	}

	public void setPositionNum(int num) {
		currentPostion = num;
	}

	public final class ViewHolder {
		public TextView text;
	}

}