package com.lovepig.dc;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.lovepig.main.R;
import com.lovepig.manager.PigManager;
import com.lovepig.pivot.BaseDC;

public class PigDC extends BaseDC  {
     
    PigManager manager;
    private TextView title;
    
    
    public PigDC(Context context, int layoutId, PigManager manager) {
        super(context, layoutId, manager);
        this.manager = manager;
        title = (TextView) findViewById(R.id.title);
        title.setText("地址");
       
    }

    

    @Override
    public void onClicked(View v) {
         
    }

   
    /**
     * resume
     */
    @Override
    public void onShow() {
        super.onShow();
//        adapter.notifyDataSetChanged();
    }
    
}
