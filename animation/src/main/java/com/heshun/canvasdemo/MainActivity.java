package com.heshun.canvasdemo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heshun.canvasdemo.customerView.boiler.BoilerView;
import com.heshun.canvasdemo.customerView.fish.FishDrawable;
import com.heshun.canvasdemo.customerView.fish.TestBizer;
import com.heshun.canvasdemo.customerView.volume.VolumeSliderView;
import com.heshun.canvasdemo.customerView.wave.WaveDrawable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
	private ViewPager viewPager;
	private View v1, v2, v3, v4,v5,v6,v7;
	private List<View> viewList;
	boolean ani=true;
	//private IndicatorView indicatorView;
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// 设置Page间间距
		viewPager.setPageMargin(100);
		// 设置缓存的页面数量
		viewPager.setOffscreenPageLimit(3);
		LayoutInflater inflater = getLayoutInflater();
		v1 = inflater.inflate(R.layout.layout_clock_view, null);
		v2 = inflater.inflate(R.layout.layout_static_fragment_use, null);
		v3 = inflater.inflate(R.layout.layout_fish, null);
		v4 = inflater.inflate(R.layout.layout_fruits_father, null);
		v5 = inflater.inflate(R.layout.layout_boiler, null);
		v6 = inflater.inflate(R.layout.layout_laba,null);
		v7 = inflater.inflate(R.layout.layout_fish_drawable,null);

		ImageView iv_fish= (ImageView) v2.findViewById(R.id.iv_fish);
        FishDrawable fishDrawable=new FishDrawable(this);
        iv_fish.setImageDrawable(fishDrawable);


		ImageView ivdongt= (ImageView) v2.findViewById(R.id.iv_dont);

		final WaveDrawable drawable1=new WaveDrawable(this);
		ivdongt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.iv_dont:
						if (!ani) {
							drawable1.start();
							ani=true;
						}else {
							drawable1.stop();
							ani=false;
						}
						break;
				}
			}
		});
		ivdongt.setImageDrawable(drawable1);


		final BoilerView boilerView= (BoilerView) v5.findViewById(R.id.bv_boiler_view);
		Button btn= (Button) v5.findViewById(R.id.btn_anim);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v instanceof Button) {
					if (((Button) v).getText()=="STOP") {
						((Button) v).setText("START");
					}else {
						((Button) v).setText("STOP");
					}
				}
			}
		});

		VolumeSliderView mSliderView;
		final TextView mTextView;
		mSliderView = (VolumeSliderView) v6.findViewById(R.id.sliderView);
		mTextView = (TextView) v6.findViewById(R.id.text);
		mSliderView.setOnVolumeSlideListener(new VolumeSliderView.OnVolumeSlideListener() {
			@Override
			public void result(int volume) {
				mTextView.setText("音量: " + volume);
			}
		});


		viewList = new ArrayList<>();
		viewList.add(new TestBizer(this));
		viewList.add(v7);
		viewList.add(v5);
		viewList.add(v6);
		viewList.add(v1);
		viewList.add(v2);
		viewList.add(v3);
		viewList.add(v4);

		viewPager.setAdapter(new PgAdapter(viewList));

//		indicatorView= (IndicatorView) findViewById(R.id.id_indicator);
//		indicatorView.setViewPager(viewPager);
	}

}
