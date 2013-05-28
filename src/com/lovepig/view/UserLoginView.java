package com.lovepig.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;
import com.lovepig.utils.Json;
import com.lovepig.utils.MD5;
import com.lovepig.utils.Utils;

public class UserLoginView extends BaseView {
    EditText mUseridET;// 用户名
    EditText mUserpwdET;//
    Button mUserRegisterBtn;
    Button mUserLoginBtn;
    Button mBackBtn;
    TextView mTitlem;


    public UserLoginView(Context context, int layoutId, BaseManager manager) {
        super(context, layoutId, manager);

        mBackBtn = (Button) findViewById(R.id.leftBtn);
        mBackBtn.setText("返回");
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText(context.getString(R.string.RegisterInfo));

        mUseridET = (EditText) findViewById(R.id.et_login_user_name);
        mUserpwdET = (EditText) findViewById(R.id.et_login_input_pwd);
        mUserRegisterBtn = (Button) findViewById(R.id.btn_register);
        mUserLoginBtn = (Button) findViewById(R.id.btn_login);
        mUserRegisterBtn.setOnClickListener(this);
        mUserLoginBtn.setOnClickListener(this);
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
    
    public boolean checkUserNameOrPwd(){
    	String name = mUseridET.getText().toString();
    	String pwd = mUserpwdET.getText().toString();
    	if("".equals(name.trim()) ){
    		showToast("用户名不可为空");
    		return false;
    	}
    	
    	if("".equals(pwd.trim())){
    		showToast("密码不可为空");
    		return false;
    	}
    	return true;
    }


    public Json getLoginInfo() {
        Json j = new Json(0);
        j.put("userName", mUseridET.getText().toString());
        j.put("pwd", MD5.md5Lower(mUserpwdET.getText().toString()));
        return j;
    }


    @Override
    public void onShow() {
        super.onShow();
//        setVeriCode();
    }

//    public void setVeriCode() {
//        registerCheckcode = Utils.generateRandomVeriCode();
//        // registerCheckcodeImage.setText(registerCheckcode);
//    }

    public String getUserAccount() {
        return mUseridET.getText().toString();
    }
}
