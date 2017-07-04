package com.heshun.canvasdemo.customerView.fish;

import android.animation.TypeEvaluator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {
	//三阶贝塞尔曲线的两个控制点
	private PointF pointF1;
	private PointF pointF2;
	private FishView fishView;
	private PathMeasure measure;
	private Path path;

	public BezierEvaluator(PointF pointF1, PointF pointF2, FishView fishView) {
		this.pointF1 = pointF1;
		this.pointF2 = pointF2;
		this.fishView = fishView;
		measure = new PathMeasure();
		path = new Path();
	}

	/**
	 * 三阶贝塞尔曲线
	 *
	 * @param time
	 * @param startValue ofObject的第二个参数
	 * @param endValue   ofObject的第三个参数
	 * @return
	 */
	@Override
	public PointF evaluate(float time, PointF startValue, PointF endValue) {
		float timeLeft = 1.0f - time;
		float slopeX;
		float slopeY;
		float angle;
		float[] angles = new float[2];
		PointF point = new PointF();// 结果

		point.x = timeLeft * timeLeft * timeLeft * (startValue.x) + 3
				* timeLeft * timeLeft * time * (pointF1.x) + 3 * timeLeft
				* time * time * (pointF2.x) + time * time * time * (endValue.x);

		point.y = timeLeft * timeLeft * timeLeft * (startValue.y) + 3
				* timeLeft * timeLeft * time * (pointF1.y) + 3 * timeLeft
				* time * time * (pointF2.y) + time * time * time * (endValue.y);

		path.moveTo(startValue.x, startValue.y);
		path.cubicTo(pointF1.x, pointF1.y, pointF2.x, pointF2.y, endValue.x, endValue.y);
		measure.setPath(path, false);
		if (time > 0.98 || time < 0.02) {
			if (time > 0.98) {
				measure.getPosTan(measure.getLength() * 0.98f, new float[2], angles);
			} else
				measure.getPosTan(measure.getLength() * 0.02f, new float[2], angles);
		} else
			measure.getPosTan(measure.getLength() * time, new float[2], angles);

		angle = (float) (Math.atan2(angles[1], angles[0]) * 180.0 / Math.PI);
		fishView.setFatherAngle(180 + angle);

		return point;
	}
}