package com.lovepig.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lovepig.main.Configs;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;
import com.lovepig.utils.Json;
import com.lovepig.utils.MD5;

public class UserModiyPWView extends BaseView {
    TextView mUserID, user_modifypwd_oldpwd_text;
    TextView mTitle;
    Button mBackBtn;
    Button mOkBtn;
    Button mCancelBtn;
    EditText mOldPwd;
    EditText mNewPwd;
    EditText mReNewPwd; 

    public UserModiyPWView(Context context, int layoutId, BaseManager manager) {
        super(context, layoutId, manager);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.MyAccountModifyPwd);
        mBackBtn = (Button) findViewById(R.id.leftBtn);
        mBackBtn.setText(R.string.MyAccountInfo);
        mUserID = (TextView) findViewById(R.id.user_modifypwd_userid);
        mOldPwd = (EditText) findViewById(R.id.user_modifypwd_oldpwd);
        mNewPwd = (EditText) findViewById(R.id.user_modifypwd_newpwd);
        mReNewPwd = (EditText) findViewById(R.id.user_modifypwd_renewpwd);
        mOkBtn = (Button) findViewById(R.id.user_modifypwd_ok);
        mCancelBtn = (Button) findViewById(R.id.user_modifypwd_cancel);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(this);
        mOkBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        user_modifypwd_oldpwd_text = (TextView) findViewById(R.id.user_modifypwd_oldpwd_text);
        setOldPasswdVisibility();

    }

    public void setOldPasswdVisibility() {
        if (Configs.isChangededPWD == 0) {
            user_modifypwd_oldpwd_text.setVisibility(View.GONE);
            mOldPwd.setVisibility(View.GONE);
        } else {
            user_modifypwd_oldpwd_text.setVisibility(View.VISIBLE);
            mOldPwd.setVisibility(View.VISIBLE);
        }
    }

    public void setUserID() {
        mUserID.setText(Configs.userid);
        mOldPwd.setText("");
        mNewPwd.setText("");
        mReNewPwd.setText("");
    }
    
    public Json getPwd() {
        Json j = null;
        if (mOldPwd.getText().toString().length() > 0) {
            j = new Json(0);
            j.put("oldPwd",  MD5.md5Lower(mOldPwd.getText().toString()));
        } else {
            if (Configs.isChangededPWD == 0) {
                j = new Json(0);
                j.put("oldPwd", "");
            } else {
                j = null;
                showToast("请输入旧密码");
                return j;
            }

          
        }
        if (mNewPwd.getText().toString().length() > 0) {
            j.put("newPwd", MD5.md5Lower(mNewPwd.getText().toString()));
        } else {
            j = null;
            showToast("请输入新密码");
            return j;
        }
        if (mReNewPwd.getText().toString().length() < 0) {
            j = null;
            showToast("请确认新密码");
            return j;
        } else if (!mReNewPwd.getText().toString().equals(mNewPwd.getText().toString())) {
            j = null;
            showToast("二次密码输入不一致");
            return j;
        }
        return j;
    }

    @Override
    public void onShow() {
        super.onShow();
        setOldPasswdVisibility();
    }
    
    
}
