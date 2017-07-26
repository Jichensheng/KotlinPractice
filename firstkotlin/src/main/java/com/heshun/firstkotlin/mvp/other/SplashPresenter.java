package com.heshun.firstkotlin.mvp.other;

import android.content.Context;


public class SplashPresenter {
    private INetConnect connect;
     private ISplashView iView;  
       
     public SplashPresenter(ISplashView iView){  
            this. iView = iView;  
            connect = new NetConnect();  
     }  
       
     public void didFinishLoading(Context context){
            iView.showProcessBar();  
            if( connect.isNetConnect(context)){  
                 iView.startNextActivity();  
           } else{  
                 iView.showNetError();  
           }  
            iView.hideProcessBar();  
     }  
}