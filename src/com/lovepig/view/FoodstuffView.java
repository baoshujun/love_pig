package com.lovepig.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.lovepig.main.R;
import com.lovepig.manager.FoodstuffManager;
import com.lovepig.pivot.BaseView;
/**
 *
 * @author Administrator
 *
 */
public class FoodstuffView extends BaseView  {
     
    FoodstuffManager manager;
    private TextView title;
    
    
    public FoodstuffView(Context context, int layoutId, FoodstuffManager manager) {
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
