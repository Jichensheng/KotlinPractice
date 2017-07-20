package com.jcs.layoutmanagers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * author：Jics
 * 2017/7/20 16:36
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvHolder> {
	private List<String> list;
	private Context context;

	public RvAdapter(List<String> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new RvHolder(LayoutInflater.from(context).inflate(R.layout.rv_lm_item, parent, false));
	}

	@Override
	public void onBindViewHolder(RvHolder holder, int position) {
		holder.textView.setText(list.get(position));
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	class RvHolder extends RecyclerView.ViewHolder {
		TextView textView;

		public RvHolder(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.tv_lm);
		}
	}
}
