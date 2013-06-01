package com.lovepig.engine;



import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.BaseManager;

/**
 * 鉴权和轮询
 * 
 * @author Administrator
 * 
 */
public class CheckUserEngine extends BaseEngine {

    public CheckUserEngine(BaseManager manager) {
        super(manager);
    }

    public static boolean isNetwork = false;

    /**
     * 鉴权,在当前线程
     */
    public void checkUser() {
     
    }

    
    public void timingPolling() {}
}
