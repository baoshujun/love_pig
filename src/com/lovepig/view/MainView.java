package com.lovepig.view;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseView;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.LogInfo;

public class MainView extends BaseView {
	/**
	 * 新闻，价格，兽药，食疗，设备，答疑
	 * 
	 */
	private Button toNews, toPrice, toVeterinaryDrugs, toFoodStuff, toEquipment,toAnswerQuestions;
	Animation goneAnimation, visibleAnimation;
	LinearLayout menuLayout;
	private TextView menuMoreTip;
	private Context context;

	public MainView(Context context, int layoutId, BaseManager manger) {
		super(context, layoutId, manger);
		this.context=context;
		toNews = (Button) findViewById(R.id.menu_news);
		toVeterinaryDrugs = (Button) findViewById(R.id.menu_veterinary_drugs);
		toPrice = (Button) findViewById(R.id.menu_price);
		toAnswerQuestions = (Button) findViewById(R.id.menu_answer_questions);
		toFoodStuff= (Button) findViewById(R.id.menu_foodstuff);
		toEquipment = (Button) findViewById(R.id.menu_equipment);
		menuMoreTip = (TextView) findViewById(R.id.menu_more_tip);

		toNews.setOnClickListener(this);
		toVeterinaryDrugs.setOnClickListener(this);
		toPrice.setOnClickListener(this);
		toFoodStuff.setOnClickListener(this);
		toEquipment.setOnClickListener(this);
		toAnswerQuestions.setOnClickListener(this);
		goneAnimation = AnimationUtils.loadAnimation(context,
				R.anim.push_right_out);
		visibleAnimation = AnimationUtils.loadAnimation(context,
				R.anim.push_right_in);

		menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
	}

	@Override
	public void onClicked(View v) {
		viewReset(v.getId());
		super.onClicked(v);
	}

	public void viewReset(int id) {
		toNews.setTextColor(context.getResources().getColor(R.color.bottom_btn_no_selected));
		toVeterinaryDrugs.setTextColor(context.getResources().getColor(R.color.bottom_btn_no_selected));
		toPrice.setTextColor(context.getResources().getColor(R.color.bottom_btn_no_selected));
		toFoodStuff.setTextColor(context.getResources().getColor(R.color.bottom_btn_no_selected));
		toEquipment.setTextColor(context.getResources().getColor(R.color.bottom_btn_no_selected));
		toAnswerQuestions.setTextColor(context.getResources().getColor(R.color.bottom_btn_no_selected));
		
		switch (id) {
		case R.id.menu_news:
			toNews.setTextColor(context.getResources().getColor(R.color.bottom_btn_selected));
			break;
		case R.id.menu_answer_questions:
			toAnswerQuestions.setTextColor(context.getResources().getColor(R.color.bottom_btn_selected));
			break;
		case R.id.menu_equipment:
			toEquipment.setTextColor(context.getResources().getColor(R.color.bottom_btn_selected));
			break;
		case R.id.menu_foodstuff:
			toFoodStuff.setTextColor(context.getResources().getColor(R.color.bottom_btn_selected));
			break;
		case R.id.menu_price:
			toPrice.setTextColor(context.getResources().getColor(R.color.bottom_btn_selected));
			break;
		case R.id.menu_veterinary_drugs:
			toVeterinaryDrugs.setTextColor(context.getResources().getColor(R.color.bottom_btn_selected));
			
			break;
		default:
			break;
		}
	}

	public void setMenuLayoutVisibility(int visible) {
		menuLayout.setVisibility(visible);
	}

	public boolean getMenuVisiblity() {
		return menuLayout.getVisibility() == VISIBLE;
	}

	
	public void setTipVisibility(int tipNum) {

	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		/**
		 * 在横竖屏切换时,每个DC都会被回调此方法 有两种方式更新界面
		 */

		/**
		 * 方式一,重新排版,适合更改范围较小的界面 优点:可无缝切换,如列表滑动位置等都不会变 缺点:代码量大
		 */
		((RelativeLayout.LayoutParams) menuMoreTip.getLayoutParams()).rightMargin = (int) context
				.getResources().getDimension(R.dimen.tip_right_textview_margin);
		// menuMoreTip.requestLayout();
		menuMoreTip.invalidate();// 建议使用该方法，不会重组layout但能刷新界面数据
		LogInfo.LogOut(
				"id",
				"title_right_btn_margin ="
						+ (int) context.getResources().getDimension(
								R.dimen.title_right_btn_margin));
		/**
		 * 方式二,重新生成界面,适合更改范围很大的界面 优点:代码量小 缺点:界面是重新生成,原来的用户操作比较难以保留
		 */
		// this.removeAllViews();
		// inflate(context, R.layout.more, this);
		// init();
		//
		super.onConfigurationChanged(newConfig);
	}
}
