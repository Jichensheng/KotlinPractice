package com.heshun.firstkotlin;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * author：Jics
 * 2017/6/24 11:33
 */
public class Acti extends Activity implements View.OnClickListener{
	private TextView textView;
	public void ma(){
		textView= (TextView) findViewById(R.id.tv_love);
		textView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_love:
				break;
		}
	}
}
