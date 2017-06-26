package com.heshun.firstkotlin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.heshun.firstkotlin.R;
import com.heshun.firstkotlin.customer.PPRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/6/24 14:40
 */
public class OtherFragment extends Fragment implements PPRefreshView.PPRefreshViewListener {
	private PPRefreshView ppRefreshView;
	private RecyclerView.Adapter adapter;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_other, container, false);
		ppRefreshView = (PPRefreshView) view.findViewById(R.id.swipeRefreshLayout);
		ppRefreshView.setPPRefreshViewListener(this);
		RecyclerView listView = (RecyclerView) view.findViewById(R.id.list);
		listView.setLayoutManager(new LinearLayoutManager(getContext()));
		adapter = makeAdapter();
		listView.setAdapter(adapter);
		return view;
	}

	private RecyclerView.Adapter makeAdapter() {
		return new RecyclerView.Adapter() {
			List<String> lists = getData();

			@Override
			public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				return new TempViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item, parent, false));
			}

			@Override
			public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
				if (holder instanceof TempViewHolder) {
					((TempViewHolder) holder).textView.setText(lists.get(position));
				}
			}

			@Override
			public int getItemCount() {
				return lists.size();
			}

			class TempViewHolder extends RecyclerView.ViewHolder {
				TextView textView;

				public TempViewHolder(View itemView) {
					super(itemView);
					textView = (TextView) itemView.findViewById(R.id.tv_simple);
				}
			}
		};
	}

	private List<String> getData() {
		List<String> data = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			data.add("测试数据" + i);
		}
		return data;
	}

	@Override
	public void onRefresh() {
		Toast.makeText(getContext(), "正在刷新", Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					ppRefreshView.setRefreshing(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void LoadMore() {
		Toast.makeText(getContext(), "加载更多", Toast.LENGTH_SHORT).show();
	}
}
