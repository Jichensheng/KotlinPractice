package com.heshun.firstkotlin.mvp.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetConnect implements INetConnect {
  
     @Override
     public boolean isNetConnect(Context context) {
            if (context != null) {  
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                          .getSystemService(Context. CONNECTIVITY_SERVICE);  
                NetworkInfo mNetworkInfo = mConnectivityManager
                           .getActiveNetworkInfo();  
                 if (mNetworkInfo != null) {  
                      return mNetworkInfo.isAvailable();  
                }  
           }  
            return false;  
     }  
  
}  