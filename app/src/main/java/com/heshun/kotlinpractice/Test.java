package com.heshun.kotlinpractice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * author：Jics
 * 2017/6/14 15:00
 */
public class Test {
	private List<byte[]> packageFrames;
	private float progress = 0f;
	private Ymodem ymodem;
	private boolean isFirstFrame = true;//是否是Head帧

	public static void main(String[] args) {
		Test test = new Test();
		test.init();
	}

	/**
	 * 1、初始化Ymodem以及升级包的帧
	 */
	private void init() {
		//初始化Ymodem类
		ymodem = new Ymodem();
		//初始化数据帧List示例
		File file = new File("文件名");
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		double fileSize = 0;
		packageFrames = Ymodem.getPackage(inputStream, file.getName(), fileSize);
	}

	/**
	 * 2、发送升级包的逻辑
	 * 此处模拟信息处理入口处理逻辑
	 * 一般为通讯数据回调函数或者socket的输入流
	 *
	 * @param data
	 */
	private void receiveData(byte[] data) {
		byte flag = 0; //获取到接收的数据的标志

		/**
		 * 发数据及状态处理过程示例
		 */
		if (packageFrames != null) {//包不空
			if (flag == Ymodem.CHAR_C && isFirstFrame) {//接到接收端的请求（第一帧）
				int state = ymodem.send(packageFrames, Ymodem.CHAR_C);
				deelState(state);
			} else {//接下来的帧
				int state = ymodem.send(packageFrames, flag);
				deelState(state);
			}
		} else {
			//文件为空
		}

		//获取大致下载进度示例
		progress = ((float) (ymodem.getFrameNumber() - 1) / (packageFrames.size())) * 100;
	}


	/**
	 * 根据状态做处理
	 *
	 * @param state
	 */
	private void deelState(int state) {
		switch (state) {
			case Ymodem.STATE_COMPLETE:
				isFirstFrame = true;
				//  TODO 传输完成 操作
				break;
			case Ymodem.STATE_FORCE_STOP:
				isFirstFrame = true;
				//   TODO  传输被强停
				break;
			case Ymodem.STATE_MORE_RETRY:
				//  TODO 重试次数太多被强停
				isFirstFrame = true;
				break;
			case Ymodem.STATE_NEXT:
				progress = ((float) (ymodem.getFrameNumber() - 1) / (packageFrames.size())) * 100;
				//下一个包
				isFirstFrame = false;
				break;
		}
	}
}
