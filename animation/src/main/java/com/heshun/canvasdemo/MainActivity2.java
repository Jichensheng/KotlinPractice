package com.heshun.canvasdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jcs on 2017/7/22.
 */

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        TextView textView1= (TextView) findViewById(R.id.first);
        TextView textView2= (TextView) findViewById(R.id.two);
        TextView textView3= (TextView) findViewById(R.id.three);
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.container_tv);

//        textView1.setX(100);
//        textView2.setX(200);
        textView3.setX(500);

        ObjectAnimator translationX=ObjectAnimator.ofFloat(textView1,"translationX",500);
        translationX.setDuration(5*1000);
        translationX.setRepeatCount(ValueAnimator.INFINITE);
        translationX.setRepeatMode(ValueAnimator.RESTART);


        ObjectAnimator x=ObjectAnimator.ofFloat(textView2,"x",500);
        x.setDuration(5*1000);
        x.setRepeatCount(ValueAnimator.INFINITE);
        x.setRepeatMode(ValueAnimator.RESTART);

        AnimatorSet set=new AnimatorSet();
        set.playTogether(translationX,x);
        set.start();

    }
}
