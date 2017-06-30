package com.heshun.firstkotlin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heshun.firstkotlin.tools.TextEvalueator;

public class AnimActivity extends AppCompatActivity {
	private LinearLayout firstView;
	private LinearLayout secondView;
	private int fHeight;
	private int sHeight;
	private Button mShowButton;
	private Button mHiddenButton;

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anim);
		firstView = (LinearLayout)findViewById(R.id.first_view);
		secondView = (LinearLayout)findViewById(R.id.second_view);
		mShowButton = (Button)findViewById(R.id.btn_anim3_show);
		mHiddenButton = (Button)findViewById(R.id.btn_anim3_hidden);
		//获取view宽高 法一
		firstView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
	            public void onGlobalLayout() {

	            	fHeight = firstView.getHeight();
	            	firstView.getViewTreeObserver()
	                        .removeOnGlobalLayoutListener(this);
	            }
		});

		// 获取view宽高 法二
		secondView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	            @Override
	            public void onGlobalLayout() {
	                                                                                                                                                                                                                                      
	            	sHeight = secondView.getHeight();
	            	secondView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	            }
		});	

		/*firstView.post(new Runnable() {
			@Override
			public void run() {
				fHeight=firstView.getHeight();
			}
		});
		secondView.post(new Runnable() {
			@Override
			public void run() {
				sHeight=secondView.getHeight();
			}
		});*/
		mShowButton.setOnClickListener(new View.OnClickListener() {
			@Override			
			public void onClick(View v){
				initShowAnim();
			}
		});
		mHiddenButton.setOnClickListener(new View.OnClickListener() {
			@Override			
			public void onClick(View v){
				initHiddenAnim();
			}
		});


		final TextView textView = (TextView) findViewById(R.id.tv_text_anim);

		ValueAnimator textAnimator =  ValueAnimator.ofObject(new TextEvalueator(), 'A', 'z');
		textAnimator.setDuration(2000);
		textAnimator.setTarget(textView);
		textAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				final Character c= (char) animation.getAnimatedValue();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						textView.setText(c);
					}
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
 
	private void initShowAnim(){
		//x动画
        ObjectAnimator fViewScaleXAnim=ObjectAnimator.ofFloat(firstView,"scaleX",1.0f,0.8f);
        fViewScaleXAnim.setDuration(350);
        //y动画
		ObjectAnimator fViewScaleYAnim=ObjectAnimator.ofFloat(firstView,"scaleY",1.0f,0.8f);
        fViewScaleYAnim.setDuration(350);
        //view透明动画
		ObjectAnimator fViewAlphaAnim=ObjectAnimator.ofFloat(firstView,"alpha",1.0f,0.5f);
        fViewAlphaAnim.setDuration(350);
        //xy旋转动画
		ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
		//复原+延时
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
		//y位移
        ObjectAnimator fViewTransYAnim=ObjectAnimator.ofFloat(firstView,"translationY",0,-0.1f* fHeight);
        fViewTransYAnim.setDuration(350);
		//第二个视图位移
        ObjectAnimator sViewTransYAnim=ObjectAnimator.ofFloat(secondView,"translationY",sHeight,0);
        sViewTransYAnim.setDuration(350);
		//第二个视图动画开始时才显示
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                secondView.setVisibility(View.VISIBLE);
            }
        });
		//同时动画
        AnimatorSet showAnim=new AnimatorSet();
//        showAnim.playTogether(fViewScaleXAnim,fViewRotationXAnim,fViewResumeAnim,fViewTransYAnim,fViewAlphaAnim,fViewScaleYAnim,sViewTransYAnim);
        showAnim.playTogether(fViewScaleXAnim,fViewRotationXAnim,fViewResumeAnim,fViewTransYAnim,fViewAlphaAnim,fViewScaleYAnim,sViewTransYAnim);
        showAnim.start();



    }	
	 
	private void initHiddenAnim(){
        ObjectAnimator fViewScaleXAnim=ObjectAnimator.ofFloat(firstView,"scaleX",0.8f,1.0f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim=ObjectAnimator.ofFloat(firstView,"scaleY",0.8f,1.0f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim=ObjectAnimator.ofFloat(firstView,"alpha",0.5f,1.0f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
        ObjectAnimator fViewTransYAnim=ObjectAnimator.ofFloat(firstView,"translationY",-0.1f* fHeight,0);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim=ObjectAnimator.ofFloat(secondView,"translationY",0,sHeight);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {  
            @Override  
            public void onAnimationEnd(Animator animation) {  
            	 super.onAnimationEnd(animation);
            	 secondView.setVisibility(View.INVISIBLE);
            }              
        });
        AnimatorSet showAnim=new AnimatorSet();
        showAnim.playTogether(fViewScaleXAnim,fViewRotationXAnim,fViewResumeAnim,fViewTransYAnim,fViewAlphaAnim,fViewScaleYAnim,sViewTransYAnim);
        showAnim.start();
    }
	/*// 获取view宽高 法三
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			fHeight=firstView.getHeight();
			sHeight=secondView.getHeight();
		}
	}*/
}