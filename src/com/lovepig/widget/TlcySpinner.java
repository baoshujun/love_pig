package com.lovepig.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lovepig.main.R;
import com.lovepig.utils.LogInfo;

public class TlcySpinner extends LinearLayout implements OnClickListener, android.content.DialogInterface.OnClickListener {
    Button textButton;
    String selectedValue;
    String itemValue[];
    String itemString[];
    AlertDialog dialog;

    public TlcySpinner(Context context, AttributeSet set) {
        super(context, set);
        textButton = new Button(getContext());
        
        //textButton.setText("TlcySpinner");
        //textButton.setBackgroundResource(android.R.drawable.btn_dropdown);
        //textButton.setBackgroundResource(R.drawable.input_edit);
        textButton.setBackgroundResource(R.drawable.translucent_btn_background);
        addView(textButton, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    }

    public TlcySpinner(Context context) {
        super(context);
        textButton = new Button(getContext());
        //textButton.setText("TlcySpinner");
        textButton.setBackgroundResource(android.R.drawable.btn_dropdown);
        addView(textButton, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    }

    public void init(String[] itemStrings, String[] itemValues, int defaultItem) {
        itemString = itemStrings;
        itemValue = itemValues;
        //textButton.setTextColor(Color.WHITE);
        textButton.setTextColor(getResources().getColor(R.color.btn_color_title));
        textButton.setHint(itemStrings[defaultItem]);
        if(textButton.getText().toString().trim().length()>0){
            textButton.setText(itemStrings[defaultItem]);
        }
        textButton.setGravity(Gravity.LEFT);
       // int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getContext().getResources().getDisplayMetrics());
        textButton.setTextSize(18f);
        textButton.setOnClickListener(this);
        dialog = new AlertDialog.Builder(getContext())
        .setSingleChoiceItems(itemStrings, defaultItem, this).create();
        selectedValue = itemValues[defaultItem];
    }

    public void setButton(String button) {
        dialog.setButton(button, this);
    }

    public void SetButton(String button, android.content.DialogInterface.OnClickListener listener) {
        dialog.setButton(button, listener);
    }

    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which >= 0 && which < itemString.length) {
            textButton.setText(itemString[which]);
            selectedValue = itemValue[which];
            textButton.postInvalidate();
        }
        dialog.dismiss();
        LogInfo.LogOut("TlcySpinner.DialogInterface.onClick");

    }

    @Override
    public void onClick(View v) {
        dialog.show();
        LogInfo.LogOut("TlcySpinner.button.onClick");
    }

    public String getSelectedValue() {
        return selectedValue;
    }
}
