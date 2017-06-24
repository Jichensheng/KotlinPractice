package com.heshun.firstkotlin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.heshun.firstkotlin.R;
import com.heshun.firstkotlin.customer.FlikerProgressBar;

/**
 * author：Jics
 * 2017/6/24 11:39
 */
public class PrograssFragment extends Fragment  implements View.OnClickListener , Runnable{
	FlikerProgressBar flikerProgressBar;
	FlikerProgressBar roundProgressbar;
Button btn_reload;
	Thread downLoadThread;

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			flikerProgressBar.setProgress(msg.arg1);
			roundProgressbar.setProgress(msg.arg1);
			if(msg.arg1 == 100){
				flikerProgressBar.finishLoad();
				roundProgressbar.finishLoad();
			}
		}
	};
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_progress,container,false);
		flikerProgressBar = (FlikerProgressBar) view.findViewById(R.id.flikerbar);
		roundProgressbar = (FlikerProgressBar) view.findViewById(R.id.round_flikerbar);
		flikerProgressBar.setOnClickListener(this);
		roundProgressbar.setOnClickListener(this);
		btn_reload= (Button) view.findViewById(R.id.btn_reload);
		btn_reload.setOnClickListener(this);
		downLoad();
		return view;
	}

	private void downLoad() {
		downLoadThread = new Thread(this);
		downLoadThread.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_reload:
				downLoadThread.interrupt();
				// 重新加载
				flikerProgressBar.reset();
				roundProgressbar.reset();

				downLoad();
				break;
			default:
				if(!flikerProgressBar.isFinish()){
					flikerProgressBar.toggle();
					roundProgressbar.toggle();

					if(flikerProgressBar.isStop()){
						downLoadThread.interrupt();
					} else {
						downLoad();
					}

				}
		}

	}

	@Override
	public void run() {
		try {
			while( ! downLoadThread.isInterrupted()){
				float progress = flikerProgressBar.getProgress();
				progress  += 2;
				Thread.sleep(30);
				Message message = handler.obtainMessage();
				message.arg1 = (int) progress;
				handler.sendMessage(message);
				if(progress == 100){
					break;
				}
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
