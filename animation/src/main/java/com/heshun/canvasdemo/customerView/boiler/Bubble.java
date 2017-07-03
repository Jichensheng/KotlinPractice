package com.heshun.canvasdemo.customerView.boiler;

/**
 * author：Jics
 * 2017/7/3 15:20
 */
public class Bubble {
	private int x;
	private int currentRadius;
	private float progress;//当前进度
	private float offset;//进度偏移量

	public Bubble() {
	}

	public Bubble(int x, int currentRadius, float progress, float offset) {
		this.x = x;
		this.currentRadius = currentRadius;
		this.progress = progress;
		this.offset = offset;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getCurrentRadius() {
		return currentRadius;
	}

	public void setCurrentRadius(int currentRadius) {
		this.currentRadius = currentRadius;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public float getOffset() {
		return offset;
	}

	public void setOffset(float offset) {
		this.offset = offset;
	}
}
