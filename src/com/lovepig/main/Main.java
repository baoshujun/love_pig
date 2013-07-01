package com.lovepig.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Main extends Activity {
	private LinearLayout newsMsg;
	private LinearLayout priceMsg;
	private LinearLayout boarMsg;
	private LinearLayout medicialMsg;
	private LinearLayout fodderMsg;
	private LinearLayout deviceMsg;
	private LinearLayout expertMsg;
	private LinearLayout other;
	private ImageView signIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_main);
		initView();
		setOnclickEvent();
	}
	
	/**
	 * init first_main activity
	 */
	private void initView() {
		newsMsg = (LinearLayout) this.findViewById(R.id.newsMsg);
		priceMsg = (LinearLayout) this.findViewById(R.id.priceMsg);
		boarMsg = (LinearLayout) this.findViewById(R.id.boarMsg);
		medicialMsg = (LinearLayout) this.findViewById(R.id.medicinalMsg);
		fodderMsg = (LinearLayout) this.findViewById(R.id.fodderMsg);
		deviceMsg = (LinearLayout) this.findViewById(R.id.deviceMsg);
		expertMsg = (LinearLayout) this.findViewById(R.id.expertMsg);
		other = (LinearLayout) this.findViewById(R.id.other);
		signIn = (ImageView)this.findViewById(R.id.sigin);
	}

	/**
	 * newsMsg on Click event
	 */
	private void setOnclickEvent() {
		commonClick(newsMsg, R.id.newsMsg);
		commonClick(priceMsg, R.id.priceMsg);
		commonClick(boarMsg, R.id.boarMsg);
		commonClick(medicialMsg, R.id.medicinalMsg);
		commonClick(fodderMsg, R.id.fodderMsg);
		commonClick(deviceMsg, R.id.deviceMsg);
		commonClick(expertMsg, R.id.expertMsg);
		commonClick(other, R.id.other);
		
		signIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				commonIntent(SignIn.class,-1);
			}
		});
	}

	/**
	 * 
	 * @param id
	 */
	private void commonClick(LinearLayout lg, final int id) {
		lg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (id) {
				case R.id.newsMsg:
					commonIntent(Application.class,R.id.newsMsg);
					break;
				case R.id.priceMsg:
					commonIntent(Application.class,R.id.priceMsg);
					break;
				case R.id.boarMsg:
					commonIntent(Application.class,R.id.boarMsg);
					break;
				case R.id.medicinalMsg:
					commonIntent(Application.class,R.id.medicinalMsg);
					break;
				case R.id.fodderMsg:
					commonIntent(Application.class,R.id.fodderMsg);
					break;
				case R.id.deviceMsg:
					commonIntent(Application.class,R.id.deviceMsg);
					break;
				case R.id.expertMsg:
					commonIntent(Application.class,R.id.expertMsg);
					break;
				case R.id.other:
					commonIntent(Application.class,R.id.other);
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 封装一个调转
	 * 
	 * @param mClass
	 */
	private void commonIntent(Class mClass,int tabID) {
		Intent it = new Intent();
		it.setClass(Main.this, mClass);
		it.putExtra("tabID", tabID);
		startActivity(it);
	}

}
