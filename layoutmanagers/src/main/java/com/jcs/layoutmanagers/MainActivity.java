package com.jcs.layoutmanagers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private RecyclerView mRv;
	private RecyclerView.Adapter adapter;
	private List<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list=initData();
		mRv= (RecyclerView) findViewById(R.id.rv_lm);
//		mRv.setLayoutManager(new RvLm());
		mRv.setLayoutManager(new LinearLayoutManager(this));
		adapter =new RvAdapter(list,this);
		mRv.setAdapter(adapter);

		RvCallback callback=new RvCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP ,
				ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT,mRv,adapter,list);
		ItemTouchHelper touchHelper=new ItemTouchHelper(callback);
		touchHelper.attachToRecyclerView(mRv);

	}

	private static List<String> initData() {
		List<String> list=new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add("可以"+i);
		}
		return list;
	}
}
