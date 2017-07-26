package com.heshun.firstkotlin.mvp.other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.heshun.firstkotlin.R;
/**
 * 在splash页面中，判断是否有网络连接，有则跳到下个页面，
 * 无则弹出一条消息通知用户，同时在检查网络是否正常的期间显示一个进度条
 */
public class MainActivity extends Activity implements ISplashView{
  
     SplashPresenter presenter;
    private ProgressDialog progressBar;
     @Override  
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);  
           setContentView(R.layout. activity_main);
             
            presenter = new SplashPresenter( this);  
     }  
  
     @Override  
     protected void onResume() {  
            super.onResume();  
            presenter.didFinishLoading( this);  
     }  
       
     @Override  
     public void showProcessBar() {  
            if ( progressBar == null) {  
                 progressBar = new ProgressDialog( this);  
                 progressBar.setCancelable( true);  
                 progressBar.setCanceledOnTouchOutside( true);  
                 progressBar.setMessage( "更新数据中，请稍后" );  
           }  
            progressBar.show();  
     }  
  
     @Override  
     public void hideProcessBar() {  
            progressBar.hide();  
     }  
  
     @Override  
     public void showNetError() {  
           Toast. makeText(this, "暂无网络", Toast.LENGTH_SHORT).show();
     }  
  
     @Override  
     public void startNextActivity() {  
           Toast. makeText(this, "跳到下个activity", Toast.LENGTH_SHORT).show();  
     }  
  
} 