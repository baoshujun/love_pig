package com.lovepig.dc;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseDC;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.LogInfo;

public class MainDC extends BaseDC {
    private ImageView toNews, toShop, toBookShelf, toMedia, toMore;
    Animation goneAnimation, visibleAnimation;
    LinearLayout menuLayout;
    private TextView menuMoreTip;

    public MainDC(Context context, int layoutId, BaseManager manger) {
        super(context, layoutId, manger);
        toNews = (ImageView) findViewById(R.id.menu_news);
        toShop = (ImageView) findViewById(R.id.menu_shop);
        toBookShelf = (ImageView) findViewById(R.id.menu_book_shelf);
        toMedia = (ImageView) findViewById(R.id.menu_multi_media);
        toMore = (ImageView) findViewById(R.id.menu_more);

        menuMoreTip = (TextView) findViewById(R.id.menu_more_tip);

        toNews.setOnClickListener(this);
        toShop.setOnClickListener(this);
        toBookShelf.setOnClickListener(this);
        toMedia.setOnClickListener(this);
        toMore.setOnClickListener(this);
        goneAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_out);
        visibleAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_in);

        menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
    }

    @Override
    public void onClicked(View v) {
        viewReset(v.getId());
        super.onClicked(v);
    }

    public void viewReset(int id) {
        switch (id) {
        case R.id.menu_news:
            toNews.setImageResource(R.drawable.tab_icon_news_3);
            toBookShelf.setImageResource(R.drawable.tab_icon_book);
            toShop.setImageResource(R.drawable.tab_icon_shop);
            toMedia.setImageResource(R.drawable.tab_icon_media);
            toMore.setImageResource(R.drawable.tab_icon_more);
            break;
        case R.id.menu_shop:
            toNews.setImageResource(R.drawable.tab_icon_news);
            toBookShelf.setImageResource(R.drawable.tab_icon_book);
            toShop.setImageResource(R.drawable.tab_icon_shop_3);
            toMedia.setImageResource(R.drawable.tab_icon_media);
            toMore.setImageResource(R.drawable.tab_icon_more);
            break;
        case R.id.menu_book_shelf:
            toNews.setImageResource(R.drawable.tab_icon_news);
            toBookShelf.setImageResource(R.drawable.tab_icon_book_3);
            toShop.setImageResource(R.drawable.tab_icon_shop);
            toMedia.setImageResource(R.drawable.tab_icon_media);
            toMore.setImageResource(R.drawable.tab_icon_more);
            break;
        case R.id.menu_multi_media:
            toNews.setImageResource(R.drawable.tab_icon_news);
            toBookShelf.setImageResource(R.drawable.tab_icon_book);
            toShop.setImageResource(R.drawable.tab_icon_shop);
            toMedia.setImageResource(R.drawable.tab_icon_media_3);
            toMore.setImageResource(R.drawable.tab_icon_more);
            break;
        case R.id.menu_more:
            toNews.setImageResource(R.drawable.tab_icon_news);
            toBookShelf.setImageResource(R.drawable.tab_icon_book);
            toShop.setImageResource(R.drawable.tab_icon_shop);
            toMedia.setImageResource(R.drawable.tab_icon_media);
            toMore.setImageResource(R.drawable.tab_icon_more_3);
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

    // private void initMusicHeadD(){
    //
    // }
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
        ((RelativeLayout.LayoutParams) menuMoreTip.getLayoutParams()).rightMargin = (int) context.getResources().getDimension(R.dimen.tip_right_textview_margin);
        //menuMoreTip.requestLayout();
        menuMoreTip.invalidate();//建议使用该方法，不会重组layout但能刷新界面数据
        LogInfo.LogOut("id", "title_right_btn_margin =" + (int) context.getResources().getDimension(R.dimen.title_right_btn_margin));
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
