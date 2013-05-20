package com.lovepig.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lovepig.main.Configs;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

public class UserAccountView extends BaseView {
    TextView mTitle;
    Button mBackBtn;
    Button mModifyInfoBtn;
    Button mModifyPwdBtn;
//    Button mUnBindBtn;
    TextView mUser_ID;
    TextView mUser_Name;
    TextView mUser_Email;
    TextView mUser_PhoneNum;

    public UserAccountView(Context context, int layoutId, BaseManager manager) {
        super(context, layoutId, manager);
        init();
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.title);
        mBackBtn = (Button) findViewById(R.id.leftBtn);
        mBackBtn.setText(R.string.More);
        mModifyInfoBtn = (Button) findViewById(R.id.account_modifyinfo);
        mModifyPwdBtn = (Button) findViewById(R.id.account_modifypwd);
//        mUnBindBtn = (Button) findViewById(R.id.account_unbind);

        mBackBtn.setVisibility(View.VISIBLE);

        mUser_ID = (TextView) findViewById(R.id.account_id);
        mUser_Name = (TextView) findViewById(R.id.account_name);
        mUser_Email = (TextView) findViewById(R.id.account_email);
        mUser_PhoneNum = (TextView) findViewById(R.id.account_phone_num);
        mTitle.setText(R.string.MyAccountInfo);
        mBackBtn.setOnClickListener(this);
        mModifyInfoBtn.setOnClickListener(this);
        mModifyPwdBtn.setOnClickListener(this);
//        mUnBindBtn.setOnClickListener(this);
    }

    public void setUserInfo() {
        mUser_ID.setText(Configs.userid);
        mUser_Name.setText(Configs.mUser_Name);
        mUser_Email.setText(Configs.mUser_Email);
        mUser_PhoneNum.setText(Configs.mUser_PhoneNum);
    }

    @Override
    public void onClicked(View v) {
        super.onClicked(v);
    }

    @Override
    public void onShow() {
        super.onShow();
        setUserInfo();
    }
    
}
