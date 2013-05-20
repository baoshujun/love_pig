package com.lovepig.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.lovepig.main.R;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 19, 2013 6:08:42 PM
 * 
 */
public class AboutView extends BaseView {
	TextView mTitle;
	TextView mDetail;
	Button mBackBtn;

	public AboutView(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("关于");
		mBackBtn = (Button) findViewById(R.id.leftBtn);
		mDetail = (TextView) findViewById(R.id.abouthelp_txt);
		mBackBtn.setVisibility(View.VISIBLE);
		mBackBtn.setText(R.string.More);
		mBackBtn.setOnClickListener(this);
		init();
	}

	@Override
	public void onClicked(View v) {
		super.onClicked(v);
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void init() {
		StringBuffer sBuffer = new StringBuffer();
		BufferedReader bReader;
		try {
			bReader = new BufferedReader(new InputStreamReader(context
					.getAssets().open("aboult.txt")));
			String tline;
			while ((tline = bReader.readLine()) != null) {
				sBuffer.append(tline + "\n");
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mDetail.setText(sBuffer.toString());
		sBuffer = null;
	}

}
