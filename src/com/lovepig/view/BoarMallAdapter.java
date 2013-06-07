package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lovepig.engine.ImageEngine;
import com.lovepig.main.R;
import com.lovepig.manager.BoarMallManager;
import com.lovepig.model.BoarMallModel;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：Jun 4, 2013 11:21:07 PM
 * 
 */
public class BoarMallAdapter extends BaseAdapter {
	private ArrayList<BoarMallModel> list;
	private Context context;
	private LayoutInflater inflater = null;
	private static int currentPostion = -1;
	private BoarMallManager manager;

	public BoarMallAdapter(ArrayList<BoarMallModel> list, Context context,BoarMallManager manager) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		this.manager = manager;
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
		final BoarMallModel bmm = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.common_img_item, null);
			holder.bigImage = (ImageView) convertView.findViewById(R.id.bigImg);
			holder.middleImage = (ImageView) convertView
					.findViewById(R.id.middleImg);
			holder.smallImg = (LinearLayout) convertView
					.findViewById(R.id.smallImg);
			holder.smallImage01 = (ImageView) convertView
					.findViewById(R.id.smallImg01);
			holder.smallImage02 = (ImageView) convertView
					.findViewById(R.id.smallImg02);
			holder.smallImage03 = (ImageView) convertView
					.findViewById(R.id.smallImg03);
			holder.smallImage04 = (ImageView) convertView
					.findViewById(R.id.smallImg04);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (bmm.type.equals("1")) {
			if (TextUtils.isEmpty(bmm.bigImg)) {
				holder.bigImage.setVisibility(View.GONE);
			} else {
				holder.bigImage.setVisibility(View.VISIBLE);
				ImageEngine.setImageBitmap(bmm.bigImg, holder.bigImage,
						R.drawable.ic_launcher, -1);
				setOnClick(holder.bigImage, bmm.bigImgId);
			}
		}
		if (bmm.type.equals("2")) {
			if (TextUtils.isEmpty(bmm.middleImg)) {
				holder.middleImage.setVisibility(View.GONE);
			} else {
				holder.middleImage.setVisibility(View.VISIBLE);
				ImageEngine.setImageBitmap(bmm.middleImg, holder.middleImage,
						R.drawable.ic_launcher, -1);
				setOnClick(holder.middleImage, bmm.middleImgId);
			}
		}
		if (bmm.type.equals("3")) {
			
			if (TextUtils.isEmpty(bmm.smallImg01)) {
				holder.smallImage01.setVisibility(View.GONE);
			} else {
				holder.smallImage01.setVisibility(View.VISIBLE);
				ImageEngine.setImageBitmap(bmm.smallImg01, holder.smallImage01,
						R.drawable.ic_launcher, -1);
				setOnClick(holder.smallImage01, bmm.smallImgId01);
			}

			if (TextUtils.isEmpty(bmm.smallImg02)) {
				holder.smallImage02.setVisibility(View.GONE);
			} else {
				holder.smallImage02.setVisibility(View.VISIBLE);
				ImageEngine.setImageBitmap(bmm.smallImg02, holder.smallImage02,
						R.drawable.ic_launcher, -1);
				setOnClick(holder.smallImage02, bmm.smallImgId02);
			}

			if (TextUtils.isEmpty(bmm.smallImg03)) {
				holder.smallImage03.setVisibility(View.GONE);
			} else {
				holder.smallImage03.setVisibility(View.VISIBLE);
				ImageEngine.setImageBitmap(bmm.smallImg03, holder.smallImage03,
						R.drawable.ic_launcher, -1);
				setOnClick(holder.smallImage03, bmm.smallImgId03);
			}

			if (TextUtils.isEmpty(bmm.smallImg04)) {
				holder.smallImage04.setVisibility(View.GONE);
			} else {
				holder.smallImage04.setVisibility(View.VISIBLE);
				ImageEngine.setImageBitmap(bmm.smallImg04, holder.smallImage04,
						R.drawable.ic_launcher, -1);
				setOnClick(holder.smallImage04, bmm.smallImgId04);
			}
		}

		return convertView;
	}

	public void setPositionNum(int num) {
		currentPostion = num;
	}

	public final class ViewHolder {
		public ImageView bigImage;
		public ImageView smallImage01;
		public ImageView smallImage02;
		public ImageView smallImage03;
		public ImageView smallImage04;
		public ImageView middleImage;
		public LinearLayout smallImg;
	}
	/**
	 * 设置imageView点击事件
	 * @param iv
	 * @param id
	 */
	public void setOnClick(ImageView iv,final String id){
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				manager.sendMessage(manager.obtainMessage(3,id));
			}
		});
	}

}
