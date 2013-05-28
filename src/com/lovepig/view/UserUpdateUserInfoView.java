package com.lovepig.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.InputFilter;
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

public class UserUpdateUserInfoView extends BaseView {
    Button mBackBtn;
    Button mOkBtn;
    Button mCancelBtn;
    TextView mTitle;
    TextView mUserID;
    EditText mUserName;
    EditText mUserEmail;
    EditText mUserPhone;
    EditText mUserPWD;

    public UserUpdateUserInfoView(Context context, int layoutId, BaseManager manager) {
        super(context, layoutId, manager);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.MyAccountModifyInfo);
        mBackBtn = (Button) findViewById(R.id.leftBtn);
        mBackBtn.setText(R.string.MyAccountInfo);
        mOkBtn = (Button) findViewById(R.id.updateuserinfo_ok);
        mCancelBtn = (Button) findViewById(R.id.updateuserinfo_cancel);
        mUserID = (TextView) findViewById(R.id.updateuserinfo_userid);
        mUserName = (EditText) findViewById(R.id.updateuserinfo_name);
        mUserName.setFilters(new InputFilter[] { new InputFilter.LengthFilter(14) });
        mUserEmail = (EditText) findViewById(R.id.updateuserinfo_email);
        mUserPhone = (EditText) findViewById(R.id.updateuserinfo_phone);
        mUserPWD = (EditText) findViewById(R.id.updateuserinfo_pwd);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(this);
        mOkBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }

    /**
     * 设置用户信息
     */
    public void setUserInfo() {
        mUserID.setText(Configs.userid);
        mUserName.setText(Configs.mUser_Name);
        mUserEmail.setText(Configs.mUser_Email);
        mUserPhone.setText(Configs.mUser_PhoneNum);
        mUserPWD.setText("");
    }

    public Json getUserInfo() {
        Json j = null;
        if (mUserName.getText().toString().length() > 12) {
            showToast(context.getString(R.string.SureInputUserNameLength));
            j = null;
            return j;
        } else if(mUserName.getText().toString().length() >0){
            j = new Json(0);
            j.put("userName", mUserName.getText().toString());
        } else{
            showToast(context.getString(R.string.SureInputUserName));
            j = null;
            return j;  
        }
        if (mUserEmail.getText().toString().length() > 0) {
            Pattern p = Pattern.compile(Configs.EmailPattern);
            Matcher m = p.matcher(mUserEmail.getText().toString());
            if (m.matches()) {
                j.put("userEmail", mUserEmail.getText().toString());
            } else {
                showToast(context.getString(R.string.EmailError));
                j = null;
                return j;
            }
        } else {
            j.put("userEmail", "");
        }
        if (mUserPhone.getText().toString().length() > 0) {
            Pattern p1 = Pattern.compile(Configs.PhonePattern);
            Matcher m1 = p1.matcher(mUserPhone.getText().toString());
            if (m1.matches() && mUserPhone.getText().toString().length() == 11) {
                j.put("userPhoneNum", mUserPhone.getText().toString());
            } else {
                showToast(context.getString(R.string.PhoneError));
                j = null;
                return j;
            }
        } else {
            j.put("userPhoneNum", "");
        }
        if (mUserPWD.getText().toString().length() > 0) {
            j.put("pwd", MD5.md5Lower(mUserPWD.getText().toString()));
        } else {
            showToast(context.getString(R.string.SureInputPWD));
            j = null;
            return j;
        }
        return j;
    }

    @Override
    public void onClicked(View v) {
        super.onClicked(v);
    }
}
