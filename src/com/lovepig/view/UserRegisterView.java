package com.lovepig.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.lovepig.utils.LogInfo;
import com.lovepig.utils.MD5;
import com.lovepig.utils.Utils;

public class UserRegisterView extends BaseView {
    EditText mUseridET;// 用户名
    EditText mUserpwdET;//
    EditText mUserRepwdET;
    EditText mUserEmailET;
    EditText mUserPhoneNumET;
    Button mUserRegisterBtn;
    Button mUserResetBtn;
    Button mBackBtn;
    TextView mTitlem;

    private String registerCheckcode;

    public UserRegisterView(Context context, int layoutId, BaseManager manager) {
        super(context, layoutId, manager);

        mBackBtn = (Button) findViewById(R.id.leftBtn);
        mBackBtn.setText("返回");
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText(context.getString(R.string.RegisterInfo));

        mUseridET = (EditText) findViewById(R.id.register_userid_edit);
        mUserpwdET = (EditText) findViewById(R.id.register_pwd_edit);
        mUserRepwdET = (EditText) findViewById(R.id.register_repwd_edit);
        mUserEmailET = (EditText) findViewById(R.id.register_email_edit);
        mUserPhoneNumET = (EditText) findViewById(R.id.register_phone_edit);
        mUserRegisterBtn = (Button) findViewById(R.id.register_register_btn);
        mUserResetBtn = (Button) findViewById(R.id.register_register_reset_btn);
        mUserRegisterBtn.setOnClickListener(this);
        mUserResetBtn.setOnClickListener(this);
        
    }

    /**
     * 设置返回按钮文字
     * 
     * @param text
     */
    public void setBackText(int backtxtid) {
        mBackBtn.setText(backtxtid);
    }

   
    @Override
    public void onClicked(View v) {
        super.onClicked(v);
    }

    public boolean checkDataintegrity() {
        LogInfo.LogOut(mUseridET.getText().toString());
        if (mUseridET.getText().toString().length() < 4) {
            showToast("用户名号应为4-20位");
            return false;
        } else {
            try {
                Integer.parseInt(mUseridET.getText().toString());
                showToast("用户名不能为纯数字");
                return false;
            } catch (Exception e) {

            }
        }
        if (mUserpwdET.getText().toString().length() < 4) {
            if (mUserpwdET.getText().toString().length() == 0) {
                showToast(context.getString(R.string.SureInputPWD));
            } else {
                showToast("密码应为4-12位");
            }
            return false;
        }
        if (mUserRepwdET.getText().toString().length() == 0) {
            showToast("请输入确认密码");
            return false;
        }
        if (!mUserpwdET.getText().toString().equals(mUserRepwdET.getText().toString())) {
            showToast("二次密码输入不一致");
            return false;
        }
        if (mUserEmailET.getText().toString().length() > 0) {
            Pattern p = Pattern.compile(Configs.EmailPattern);
            Matcher m = p.matcher(mUserEmailET.getText().toString());
            if (!m.matches()) {
                showToast(context.getString(R.string.EmailError));
                return false;
            }
        }
        if (mUserPhoneNumET.getText().toString().length() > 0) {
            Pattern p1 = Pattern.compile(Configs.PhonePattern);
            Matcher m1 = p1.matcher(mUserPhoneNumET.getText().toString());
            if (!m1.matches() || mUserPhoneNumET.getText().toString().length() != 11) {

                showToast(context.getString(R.string.PhoneError));
                return false;
            }
        }
        return true;
    }

    public Json getRegisterInfo() {
        Json j = new Json(0);
        Configs.mUser_Email = mUserEmailET.getText().toString();
        Configs.mUser_PhoneNum = mUserPhoneNumET.getText().toString();
        j.put("userName", mUseridET.getText().toString());
        j.put("pwd", MD5.md5Lower(mUserpwdET.getText().toString()));
        j.put("userEmail", Configs.mUser_Email);
        j.put("userPhoneNum", Configs.mUser_PhoneNum);
        return j;
    }
    

    public void Reset() {
        mUseridET.setText("");
        mUserpwdET.setText("");
        mUserRepwdET.setText("");
        mUserEmailET.setText("");
        mUserPhoneNumET.setText("");
       
    }

    @Override
    public void onShow() {
        super.onShow();
        setVeriCode();
        Reset();
        mUserPhoneNumET.requestFocus();
    }

    public void setVeriCode() {
        registerCheckcode = Utils.generateRandomVeriCode();
        // registerCheckcodeImage.setText(registerCheckcode);
    }

    public String getUserAccount() {
        return mUseridET.getText().toString();
    }
}
