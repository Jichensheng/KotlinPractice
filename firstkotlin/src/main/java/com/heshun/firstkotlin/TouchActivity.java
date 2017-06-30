package com.heshun.firstkotlin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

/**
 * author：Jics
 * 2017/6/28 10:11
 */
public class TouchActivity extends AppCompatActivity {
	private static final String TAG = "TouchActivity~~";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);
		/*findViewById(R.id.chiaroTextView).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(TouchActivity.this, "点到tv了", Toast.LENGTH_SHORT).show();
			}
		});*/
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean b=super.dispatchTouchEvent(ev);
		Log.e(TAG, "dispatchTouchEvent: "+b);
		return b;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean b=super.onTouchEvent(event);
		Log.e(TAG, "onTouchEvent: " +b);
		return b;
	}
}
