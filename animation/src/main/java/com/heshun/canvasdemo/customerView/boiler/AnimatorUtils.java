package com.heshun.canvasdemo.customerView.boiler;

import android.graphics.Path;
import android.graphics.PathMeasure;

/**
 * author：Jics
 * 2017/7/3 17:11
 */
public class AnimatorUtils {

	public static float[] getPathPoint(Path path, float persent) {
		float[] pos = new float[2];
		float[] tan = new float[2];
		PathMeasure pathMeasure = new PathMeasure(path, false);
		float lenght = pathMeasure.getLength();
		pathMeasure.getPosTan(lenght * persent, pos, tan);
		return pos;
	}
}
