package com.lovepig.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.model.ProvinceModel;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 7, 2013 11:47:39 PM
 * 
 */
public class BoarMallView extends BaseView {

	private EditText et;
	private List<ProvinceModel> data = new ArrayList<ProvinceModel>();
	private PopupWindow popupWindow;
	private String choiceId ; 

	public BoarMallView(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		et = (EditText) findViewById(R.id.provineceET);
		initData();
		et.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initPopupWindow();
				openPopWindow(et);
			}
		});
	}
	
	private void initData(){
		try {
			InputStream in = getResources().getAssets().open(
					"province.txt");
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			in.close();
			String res = EncodingUtils.getString(buffer, "UTF-8");
			Log.d("LKP", res);
			JSONArray ja = new JSONArray(res);
			for (int i = 0; i < ja.length(); i++) {
				ProvinceModel pm = new ProvinceModel();
				pm.id = ja.getJSONObject(i).getString("id");
				pm.name = ja.getJSONObject(i).getString("name");
				data.add(pm);
			}
			
		} catch (Exception e) {
			Log.e("LKP", e.getMessage());

		}

	}

	private void initPopupWindow() {
		LayoutInflater mLayoutInflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View contentView = mLayoutInflater.inflate(
				R.layout.boar_mall_popupwindow, null);
		GridView gridView = (GridView) contentView.findViewById(R.id.gridView);
		gridView.setOnItemClickListener(new ItemClickListener());
		GridViewAdapter adapter = new GridViewAdapter(context, data);
		gridView.setAdapter(adapter);
		popupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		popupWindow.setFocusable(true);// 取得焦点
		popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 点击空白的地方关闭PopupWindow

	}

	private final class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (popupWindow.isShowing())
				popupWindow.dismiss();// 关闭
			et.setText(data.get(position).name);
			choiceId = data.get(position).id;
			//发起联网请求
			Log.d("LKP", "choiceId--->" + choiceId);
			
		}
	}


	public void openPopWindow(View v) {
		popupWindow.showAsDropDown(et, 0, 0);
	}

	class GridViewAdapter extends BaseAdapter {
		private Context context;
		private List<ProvinceModel> data;

		public GridViewAdapter(Context context, List<ProvinceModel> data) {
			this.context = context;
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
				holder.tv = (TextView) convertView.findViewById(R.id.textView);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.tv.setText(data.get(position).name);
			return convertView;
		}

		class Holder {
			TextView tv;
		}

	}
}
