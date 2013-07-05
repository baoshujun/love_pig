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

public class UserLoginView extends BaseView {
    EditText mUserPhoneNum;// 用户电话号码
    EditText mUserpwdET;// 密码
    EditText mUserpwdAgainET;// 再次确认密码
    EditText mUserVerificationCode;// 用户验证码
    Button mUserRegisterBtn;
    Button mUserLoginBtn;
    Button mBackBtn;
    Button vericationCode;
    TextView mTitlem;
    TextView verificationText;
    BaseManager m;

    public UserLoginView(Context context, int layoutId, BaseManager manager) {
        super(context, layoutId, manager);
        m = manager;
        mBackBtn = (Button) findViewById(R.id.leftBtn);
        mBackBtn.setText("返回");
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText(context.getString(R.string.RegisterInfo));

        mUserPhoneNum = (EditText) findViewById(R.id.et_login_user_name);
        mUserpwdET = (EditText) findViewById(R.id.et_login_input_pwd);
        mUserpwdAgainET = (EditText) findViewById(R.id.et_login_input_pwd_again);

        vericationCode = (Button) findViewById(R.id.verificationCode);
        verificationText = (TextView) findViewById(R.id.verificationText);

        verificationText.setVisibility(View.GONE);

        vericationCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                m.onClicked(v.getId());
            }
        });

        mUserRegisterBtn = (Button) findViewById(R.id.btn_register);
        mUserLoginBtn = (Button) findViewById(R.id.btn_login);
        mUserRegisterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                m.onClicked(v.getId());
            }
        });
        mUserLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                m.onClicked(v.getId());
            }
        });
        mUserVerificationCode = (EditText) findViewById(R.id.et_login_input_code);
    }

    /**
     * check phonenum
     * 
     * @param v
     * @return
     */
    public String checkPhoneNum() {
        String phoneNum = mUserPhoneNum.getEditableText().toString();
        if (phoneNum.equals("")) {
            showToast("请填写电话号码");
            phoneNum = null;
        } else if (mUserPhoneNum.getText().toString().length() > 0) {
            Pattern p1 = Pattern.compile(Configs.PhonePattern);
            Matcher m1 = p1.matcher(mUserPhoneNum.getText().toString());
            if (!m1.matches() || mUserPhoneNum.getText().toString().length() != 11) {
                showToast(context.getString(R.string.PhoneError));
                phoneNum = null;
            }
        }
        return phoneNum;
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

    public Json getLoginInfo() {
        Json j = new Json(0);
        j.put("userName", mUserPhoneNum.getText().toString());
        j.put("pwd", MD5.md5Lower(mUserpwdET.getText().toString()));
        return j;
    }

    @Override
    public void onShow() {
        super.onShow();
        // setVeriCode();
    }

    // public void setVeriCode() {
    // registerCheckcode = Utils.generateRandomVeriCode();
    // // registerCheckcodeImage.setText(registerCheckcode);
    // }

    public String getUserAccount() {
        return mUserPhoneNum.getText().toString();
    }

    public boolean checkDataintegrity() {
        LogInfo.LogOut(mUserPhoneNum.getText().toString());
        if (checkPhoneNum() == null) {
            return false;
        }
        if (mUserVerificationCode.getText().toString().equals("")) {
            showToast("验证码不可为空");
            return false;
        } else if (mUserVerificationCode.getText().toString().length() != 6) {
            showToast("验证码不符合要求，请输入六位数字验证码");
            return false;
        }
        if (mUserpwdET.getText().toString().length() < 4) {
            if (mUserpwdET.getText().toString().length() == 0) {
                showToast(context.getString(R.string.SureInputPWD));
            } else {
                showToast("密码应为4-12位");
            }
            return false;
        }
        if (mUserpwdAgainET.getText().toString().length() == 0) {
            showToast("请输入确认密码");
            return false;
        }
        if (!mUserpwdET.getText().toString().equals(mUserpwdAgainET.getText().toString())) {
            showToast("二次密码输入不一致");
            return false;
        }
        return true;
    }

    public Json getRegisterInfo() {
        Json j = new Json(0);
        Configs.mUser_PhoneNum = mUserPhoneNum.getText().toString();
        j.put("pwd", MD5.md5Lower(mUserpwdET.getText().toString()));
        j.put("phoneNum", Configs.mUser_PhoneNum);
        j.put("code", mUserVerificationCode.getText().toString());
        return j;
    }

    public void setverificationTextVisible(boolean isVisible) {
        if (isVisible) {
            verificationText.setVisibility(View.VISIBLE);
        } else {
            verificationText.setVisibility(View.GONE);
        }
    }

}
