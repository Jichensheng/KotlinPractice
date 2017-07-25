package com.heshun.firstkotlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heshun.firstkotlin.customer.behavior.BehaviorActivity;
import com.heshun.firstkotlin.customer.layoutManager.CardConfig;
import com.heshun.firstkotlin.customer.layoutManager.OverLayCardLayoutManager;
import com.heshun.firstkotlin.customer.layoutManager.RenRenCallback;
import com.heshun.firstkotlin.customer.xfermode.GirlView;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/7/19 15:51
 */
public class LMActivity extends AppCompatActivity {
	RecyclerView recyclerView;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lm);
		GirlView girlView= (GirlView) findViewById(R.id.girl);
		girlView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LMActivity.this, BehaviorActivity.class));
			}
		});
		RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
			@Override
			public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				return new TempHolder(LayoutInflater.from(LMActivity.this).inflate(R.layout.rv_lm_item, parent, false));
			}

			@Override
			public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

			}

			@Override
			public int getItemCount() {
				return 100;
			}

			class TempHolder extends RecyclerView.ViewHolder {

				public TempHolder(View itemView) {
					super(itemView);
				}
			}
		};

		recyclerView= (RecyclerView) findViewById(R.id.rv_lm);
		CardConfig.initConfig(this);
		ItemTouchHelper.Callback callback = new RenRenCallback(recyclerView, adapter, init());
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
		itemTouchHelper.attachToRecyclerView(recyclerView);



		recyclerView.setLayoutManager(new OverLayCardLayoutManager());

		recyclerView.setAdapter(adapter);
	}
	public static List<String> init(){
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add(""+i);
		}
		return list;
	}
}
