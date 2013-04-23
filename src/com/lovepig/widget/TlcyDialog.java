package com.lovepig.widget;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovepig.main.R;


public class TlcyDialog extends Dialog implements OnClickListener {
	public interface TlcyDialogListener {
		public void onClick();
	}
	private TextView titleText;
	private TextView messageText;
	private EditText editMessage;
	private Button cancelBtn;
	private Button okBtn;
	private Button onlyOkBtn;
	private Button cancelIcon;
	private RelativeLayout bottomLayout01;
	private RelativeLayout bottomLayout02;
	private TlcyDialogListener okListener;
	private TlcyDialogListener cancelLisenter;
	private ListView listView;
	private MyAdpter myAdapter;
	private String[] datas = new String[] {};
	private long l = 0;
	public TlcyDialog(Context context) {
		super(context, R.style.dialog);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.tlcydialog);
		titleText = (TextView) findViewById(R.id.title);
		titleText.setText(context.getString(R.string.tip));
		messageText = (TextView) findViewById(R.id.message);
		editMessage = (EditText) findViewById(R.id.editMessage);
		editMessage.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16) });
		cancelBtn = (Button) findViewById(R.id.BtnCancel);
		cancelIcon = (Button) findViewById(R.id.cancelIcon);
		okBtn = (Button) findViewById(R.id.BtnOK);
		listView = (ListView) findViewById(R.id.listView0);
		myAdapter = new MyAdpter(context);
		listView.setFocusable(false);
		listView.setAdapter(myAdapter);
		bottomLayout01 = (RelativeLayout) findViewById(R.id.layoutBottom01);
		bottomLayout02 = (RelativeLayout) findViewById(R.id.layoutBottom02);
		onlyOkBtn = (Button) findViewById(R.id.btnOnlyOK);
		cancelBtn.setOnClickListener(this);
		cancelIcon.setOnClickListener(this);
		onlyOkBtn.setOnClickListener(this);
		okBtn.setOnClickListener(this);
		okBtn.setText(context.getString(R.string.OK));
		cancelBtn.setText(context.getString(R.string.CANCEL));
		onlyOkBtn.setText(context.getString(R.string.OK));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(editMessage.getWindowToken(), 0);
		return super.onTouchEvent(event);
	}
	@Override
	public void onClick(View v) {
	    if(Math.abs(System.currentTimeMillis() - l) > 500){
            l=System.currentTimeMillis();
    		switch (v.getId()) {
    		case R.id.BtnOK:
    			dismiss();
    			if (okListener != null) {
    				okListener.onClick();
    			}
    			break;
    		case R.id.BtnCancel:
    			dismiss();
    			if (cancelLisenter != null) {
    				cancelLisenter.onClick();
    			}
    			break;
    		case R.id.btnOnlyOK:
    			dismiss();
    			if (okListener != null) {
    				okListener.onClick();
    			}
    			break;
    		case R.id.cancelIcon:
    			dismiss();
    			break;
    		}
	    }
	}

	/**
	 * 
	 * 设置对话框的标题
	 */
	public TlcyDialog setTitle(String title) {
		this.titleText.setText(title);
		return this;
	}
	@Override
	@Deprecated
	public void setTitle(CharSequence title) {
		this.titleText.setText(title);
	}
	@Override
	@Deprecated
	public void setTitle(int titleId) {
		this.titleText.setText(getContext().getString(titleId));
	}
	/**
	 * 设置对话框提示消息
	 */
	public TlcyDialog setMessage(String message) {
		this.messageText.setText(message);
		return this;
	}

	/**
	 * 设置对话框为输入格式，覆盖提示消息
	 */
	public TlcyDialog setEditMessage(String hint) {
		editMessage.setVisibility(View.VISIBLE);
		editMessage.setHint(hint);
		messageText.setVisibility(View.GONE);
		return this;
	}
	/*
	 * 
	 */
	
	public TlcyDialog setEditMessageInputType() {
	    editMessage.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
	    return this;
	}
	public String getEditMessage(){
	    return editMessage.getText().toString();
	}
	/**
	 * 设置输入框长度限制，默认16个字符
	 */
	public TlcyDialog setEditLength(int length){
		editMessage.setFilters(new InputFilter[] { new InputFilter.LengthFilter(length) });
		return this;
	}
	/**
	 * 重新设置确定和取消按钮监听器 
	 */
	public TlcyDialog setButton(TlcyDialogListener okListener, TlcyDialogListener cancelListener){
		this.okListener = okListener;
		this.cancelLisenter = cancelListener;
		return this;
	}
	/**
	 * 设置确定和取消按钮的文本和监听器
	 */
	public TlcyDialog setButton(String oktext, String cancelText, TlcyDialogListener okListener, TlcyDialogListener cancelListener) {
		bottomLayout02.setVisibility(View.GONE);
		bottomLayout01.setVisibility(View.VISIBLE);
		if (oktext != null && !oktext.trim().equals("")) {
			 okBtn.setText(oktext);
		}
		if (cancelText != null && !cancelText.trim().equals("")) {
			 cancelBtn.setText(cancelText);
		}
		this.okListener = okListener;
		this.cancelLisenter = cancelListener;
		return this;
	}

	/**
	 * 设置弹出框为只有一个确定按钮
	 */
	public TlcyDialog setOnlyOkPositiveMethod(String oktext) {
		bottomLayout01.setVisibility(View.GONE);
		bottomLayout02.setVisibility(View.VISIBLE);
		if (oktext != null && !oktext.trim().equals("")) {
			onlyOkBtn.setText(oktext);
		}
		return this;
	}
	 public void setPositiveMethod(String oktext, String cancelText, TlcyDialogListener okListener,TlcyDialogListener cancelListener ){
	      bottomLayout02.setVisibility(View.GONE);
	      bottomLayout01.setVisibility(View.VISIBLE);
		  if(oktext!=null&&!oktext.trim().equals("")){
		      //okBtn.setText(oktext);
		   }
		  if(cancelText!=null&&!cancelText.trim().equals("")){
			  //cancelBtn.setText(cancelText);
		   }
		   this.okListener=okListener;
		   this.cancelLisenter=cancelListener;	
  }
	/**
	 * 设置弹出框为只有一个确定按钮的文本和监听器
	 */
	public TlcyDialog setOnlyOkPositiveMethod(String oktext, TlcyDialogListener okListener) {
		bottomLayout01.setVisibility(View.GONE);
		bottomLayout02.setVisibility(View.VISIBLE);
		if (oktext != null && !oktext.trim().equals("")) {
			if (oktext.equals("1")) {
				onlyOkBtn.setText(oktext);
			}
		}
		this.okListener = okListener;
		return this;
	}
	public TlcyDialog setItems(String[] datas, OnItemClickListener listener) {
		listView.setVisibility(View.VISIBLE);
		this.datas = datas;
		if (listener != null) {
			listView.setOnItemClickListener(listener);
		}
		myAdapter.notifyDataSetChanged();
		return this;
	}
	
	/**
	 * 设置为无按钮
	 */
	public TlcyDialog setNoButton() {
		bottomLayout01.setVisibility(View.GONE);
		bottomLayout02.setVisibility(View.GONE);
		return this;
	}
	private class MyAdpter extends BaseAdapter {
		private LayoutInflater layoutInflater = null;

		public MyAdpter(Context context) {
			layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return datas.length;
		}

		@Override
		public Object getItem(int position) {
			return datas[position];
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = null;
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				
				v.setTag(viewHolder);
			} else {
				v = convertView;
				viewHolder = (ViewHolder) v.getTag();
			}
			String st = datas[position];
			viewHolder.title.setText(st);
			return v;
		}

		public final class ViewHolder {
			public TextView title;
		}
	}
}
