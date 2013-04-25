package com.lovepig.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
	}

	private void setOnclickEvent() {
		newsMsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(Main.this, Application.class);
				startActivity(it);
			}
		});
	}
}
