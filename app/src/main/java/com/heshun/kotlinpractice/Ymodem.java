package com.heshun.kotlinpractice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/3/16 15:38
 */
public class Ymodem {
	//请求字符
	public static final byte CHAR_C = (byte) 'C';
	// 开始(128B)
	public static final byte SOH = 0x01;
	// 开始(1024B)
	public static final byte STX = 0x02;
	// 结束
	public static final byte EOT = 0x04;
	// 应答
	public static final byte ACK = 0x06;
	// 重传
	public static final byte NAK = 0x15;
	// 无条件结束
	public static final byte CAN = 0x18;
	// 填充数据包
	public static final byte EOF = 0x1A;
	//单帧数据位大小
	private static final int SECTOR_SIZE = 1024;
	//第一次请求
	public static final int FIRST = 0x2A;

	//6个send状态
	//发完了
	public static final int STATE_COMPLETE = 0x3A;
	//下一包
	public static final int STATE_NEXT = 0x4A;
	//强停
	public static final int STATE_FORCE_STOP = 0x5A;
	//超过10次
	public static final int STATE_MORE_RETRY = 0x6A;
	//最后一次等C
	public static final int STATE_WAIT_CHAR_C = 0x7A;
	//握手结束
	public static final int STATE_HANDSHAKE_OVER = 0x8A;

	//容错次数<=10次
	private int retryCount = 0;
	//下次的帧号从下标开始，因为下标0是文件的info，非文件体
	private int nextFrameNum = 1;
	//发送完成
	private boolean isComplete = false;

	//---针对接收端
	// 错误包数
	private int errorCount = 0;
	// 包序号
	private byte blocknumber = 0x01;

	//第一次收到C 标志
	private boolean firstGetC = true;
	//正式开始下标是1的帧
	private boolean startFirstFrame = false;
	//握手结束标志
	private boolean handshakeOver = false;

	//是否是主动开始发文件的
	public static boolean initiativeStart = false;


	/**
	 * 将文件流拆封成数据帧
	 * 第 0 帧是包含文件信息的数据帧
	 * 第 一 帧到最后是正常的文件数据帧
	 * @param inputStream
	 * @return
	 */
	public static List<byte[]> getPackage(InputStream inputStream, String name, double size) {
		List<byte[]> byteList = new ArrayList<>();

		//第 0 帧 head frame  Head帧按SOH方式(数据部分128Byte)
		///数据帧长度为1+1+1+128+2=133
		byte[] head = new byte[133];
		head[0] = SOH;
		head[1] = (byte) 0;
		head[2] = (byte) ~head[1];
		byte[] data = new byte[128];
		byte[] info = (name + '\0' + (int) size).getBytes();//文件名”，“空字符”“文件大小”  空字符'\0'隔开
		for (int j = 0; j < info.length; j++) {
			data[j] = info[j];
		}
		for (int i = info.length; i < 128; i++) {
			data[i] = 0x00;
		}
		System.arraycopy(data, 0, head, 3, data.length);
		System.arraycopy(CRC16.calcByteArray(data), 0, head, 131, 2);//crc填充131 132
		byteList.add(head);

		//第 1 帧到最后 rest frame
		int blockNumber = 1;//包序号
		int nbytes;
		//封包（数据包按STX方式，每包数据部分1024Byte
		byte[] sector = new byte[SECTOR_SIZE];
		try {
			while ((nbytes = inputStream.read(sector)) > 0) {
				//数据帧长度为1+1+1+1024+2=1029
				byte[] temp = new byte[1029];
				//最后一份不足1024的用0补足
				if (nbytes < SECTOR_SIZE) {
					for (int i = nbytes; i < SECTOR_SIZE; i++) {
						sector[i] = 0x00;
					}
				}
				temp[0] = STX;
				temp[1] = (byte) blockNumber;
				temp[2] = (byte) ~blockNumber;
				System.arraycopy(sector, 0, temp, 3, sector.length);
				System.arraycopy(CRC16.calcByteArray(sector), 0, temp, 1027, 2);
//				Log.e("------------原始---------", String.format("head%s   block%s   _block%s  crc_L%s   crc_H%s", temp[0], temp[1], temp[2], temp[1027], temp[1028]));
				byteList.add(temp);
				blockNumber++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteList;
	}


	/**
	 * 发送
	 * 正常结束或者强停的话会重置nextFrameNum、retryCount、isComplete
	 *
	 * @param packages
	 * @param flag
	 * @return true代表还要发，false代表此文件不发了
	 */

	public int send(List<byte[]> packages, int flag) {
		if (initiativeStart) {
			//不被停或不主动停就继续
			if (flag != CAN && flag != EOT) {
				//收到应答或者第一次就正常发
				if (flag == ACK || flag == CHAR_C) {
					//前三个if只有isComplete为真才触发
					if (flag == ACK && isComplete && !handshakeOver) {
						//不做重置标志操作，继续等待C
						return STATE_WAIT_CHAR_C;
					}
					if (flag == CHAR_C && isComplete) {
						subAndSendPack(makeEmptyFrame());//最后的全0的校验帧
						handshakeOver = true;
						return STATE_HANDSHAKE_OVER;
					}
					if (flag == ACK && isComplete && handshakeOver) {//最后一次收到ACK并且结束标志和握手成功标志都为true
						resetSendState();
						return STATE_COMPLETE;
					}
					//开始发
					if (flag == CHAR_C && firstGetC) {//第一次收到C就发head帧
						subAndSendPack(packages.get(0));
						firstGetC = false;
					} else {//不是第一次收到C就发正常帧
						if (flag == ACK && !startFirstFrame) {
							retryCount = 0;//head的重发次数置0
							return STATE_WAIT_CHAR_C;
						} else {//包括ACK或者第二次C的情况
							isComplete = false;//没发完
							retryCount = 0;//重发次数置0
							startFirstFrame = true;
							if (nextFrameNum >= packages.size()) {
								isComplete = true;//文件发完了
								subAndSendPack(new byte[]{EOT});
							} else
								subAndSendPack(packages.get(nextFrameNum));
							nextFrameNum++;
						}
					}
					//发完后帧号加一
				}
				//收到重发命令
				//发出去一帧帧号就加一，回滚的时候再减一
				if (flag == NAK) {
					if (!startFirstFrame) {//重试head帧
						retryCount++;
						if (retryCount < 10) {

							subAndSendPack(packages.get(0));
						} else {
							resetSendState();//重置状态
							return STATE_MORE_RETRY;
						}

					} else {//重试正常文件帧
						nextFrameNum--;//回滚一次
						retryCount++;
						isComplete = false;//没发完
						if (retryCount < 10) {
							if (nextFrameNum >= packages.size()) {
								//文件发完了
								isComplete = true;
								subAndSendPack(new byte[]{EOT});
							} else {
								subAndSendPack(packages.get(nextFrameNum));
								nextFrameNum++;
							}
						} else {
							resetSendState();//重置状态
							return STATE_MORE_RETRY;
						}
					}
				}
			} else {
				resetSendState();//重置状态
				return STATE_FORCE_STOP;
			}
//		Log.e("---------------------", String.format("flag=%s   RetryCount=%s", flag, retryCount));
			return STATE_NEXT;
		} else
			return STATE_WAIT_CHAR_C;

	}

	/**
	 * 将数据帧拆成20B的包并发送出去
	 *	Todo 数据发送出口要在此类里
	 * @param data
	 */
	private void subAndSendPack(final byte[] data) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int packSize = 20;
				int length = data.length;
				int packCount = length % packSize == 0 ? length / packSize : length / packSize + 1;
				for (int i = 1; i <= packCount; i++) {
					if (i == packCount) {//最后一个小于或等于20B的数据包
						byte[] bytes = new byte[length % packSize == 0 ? packSize : length % packSize];
						System.arraycopy(data, (i - 1) * packSize, bytes, 0, bytes.length);
						//Todo 数据发送出口,如果发送失败一定要执行i--操作，否则数据包不对
					} else {//完整的20B的数据包
						byte[] bytes = new byte[packSize];
						System.arraycopy(data, (i - 1) * packSize, bytes, 0, packSize);
						//Todo 数据发送出口,如果发送失败一定要执行i--操作，否则数据包不对
					}
				}
			}
		}).start();
	}

	/**
	 * @param initiative
	 */
	public static void setInitiativeStart(boolean initiative) {
		initiativeStart = initiative;
	}

	/**
	 * 文件发完、强停、重试次数超过十次触发状态重置
	 */
	public void resetSendState() {
		initiativeStart = false;
		firstGetC = true;//第一次拿到C
		nextFrameNum = 1;//强停，重置帧号
		retryCount = 0;//重发次数置0
		isComplete = false;//重置完成标志
		handshakeOver = false;//最后一次握手
		startFirstFrame = false;//是否开始下标是1的帧
	}

	/**
	 * 构建结束的全0空包
	 * @return
	 */
	private byte[] makeEmptyFrame() {
		byte[] empty = new byte[133];
		empty[0] = SOH;
		empty[1] = (byte) 0;
		empty[2] = (byte) ~empty[1];
		byte[] data = new byte[128];
		System.arraycopy(data, 0, empty, 3, 128);
		System.arraycopy(CRC16.calcByteArray(data), 0, empty, 131, 2);
		return empty;
	}

	/**
	 * 获取当前的帧序号，用于计算发送进度
	 * @return
	 */
	public int getFrameNumber() {
		return nextFrameNum;
	}


}
