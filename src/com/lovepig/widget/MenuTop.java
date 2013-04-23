package com.lovepig.widget;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.lovepig.main.R;
import com.lovepig.pivot.BaseManager;

/**
 * 用于处理书架和消息批量处理的菜单
 */
public class MenuTop implements OnClickListener {

    /**
     * 标识信息类型
     */
    public static final int MSG_WHAT = 999;
    /**
     * 全选
     */
    public static final int MSG_ARG1_ALL = 1;
    /**
     * 删除
     */
    public static final int MSG_ARG1_DEL = 2;
    /**
     * 刷新或写信
     */
    public static final int MSG_ARG1_DO = 3;
    /**
     * 取消
     */
    public static final int MSG_ARG1_CANCEL = 4;
    private Button allBtn;// 全选
    private Button delBtn;// 删除
    private Button doBtn;// 刷新或写信
    private Button cancelBtn;// 取消
    private BaseManager manager;
    private Animation goneAnimation, visibleAnimation;
    private View contentView;
    /**
     * 0为书架一级界面,1为书架二级界面,2为消息管理界面
     */
    private int type = 0;
    private long l = 0;

    /**
     * from 0为书架一级界面,1为书架二级界面,2为消息管理界面 view 必须是menu_top.xml,否则程序会崩毁
     */
    public MenuTop(BaseManager manager, int from, View view) {
        this.manager = manager;
        contentView = view;
        type = from;
        goneAnimation = AnimationUtils.loadAnimation(manager.context, R.anim.push_right_out);
        visibleAnimation = AnimationUtils.loadAnimation(manager.context, R.anim.push_right_in);
        allBtn = (Button) view.findViewById(R.id.top_btn1);
        allBtn.setText("全选");
        allBtn.setOnClickListener(this);
        delBtn = (Button) view.findViewById(R.id.top_btn2);
        delBtn.setText("删除");
        delBtn.setOnClickListener(this);
        doBtn = (Button) view.findViewById(R.id.top_btn3);
        if (type == 0 || type == 1) {
            doBtn.setText("刷新");
        } else if (type == 2) {
            doBtn.setText("写信");
        }
        doBtn.setOnClickListener(this);
        cancelBtn = (Button) view.findViewById(R.id.top_btn4);
        cancelBtn.setText("取消");
        cancelBtn.setOnClickListener(this);
    }

    /**
     * 显示菜单栏
     */
    public void show() {
        contentView.startAnimation(visibleAnimation);
        contentView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏菜单栏
     */
    public void hide() {
        contentView.startAnimation(goneAnimation);
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(Math.abs(System.currentTimeMillis() - l) > 500){
            l=System.currentTimeMillis();
            switch (v.getId()) {
            case R.id.top_btn1:
                manager.sendMessage(manager.obtainMessage(MSG_WHAT, MSG_ARG1_ALL, type));
                break;
            case R.id.top_btn2:
                manager.sendMessage(manager.obtainMessage(MSG_WHAT, MSG_ARG1_DEL, type));
                break;
            case R.id.top_btn3:
                manager.sendMessage(manager.obtainMessage(MSG_WHAT, MSG_ARG1_DO, type));
                break;
            case R.id.top_btn4:
                manager.sendMessage(manager.obtainMessage(MSG_WHAT, MSG_ARG1_CANCEL, type));
                break;
    
            default:
                break;
            }
        }
    }
}
