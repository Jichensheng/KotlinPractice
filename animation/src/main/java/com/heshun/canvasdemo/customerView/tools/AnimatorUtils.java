package com.heshun.canvasdemo.customerView.tools;

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

    /**
     * 适合先高后低的即sY < eY
     * @param sX
     * @param sY
     * @param eX
     * @param eY
     * @return
     */
    public static Path makeBezierJumpPath(int sX, int sY, int eX, int eY) {
        Path path = new Path();
        path.moveTo(sX, sY);
        path.quadTo(eX, sY-sY*0.3f, eX, eY);
        path.lineTo(eX, eY*0.8f);
        path.lineTo(eX, eY);
        path.lineTo(eX, eY*0.9f);
        path.lineTo(eX, eY);
        path.lineTo(eX, eY*0.95f);
        path.lineTo(eX, eY);
        return path;
    }
}
