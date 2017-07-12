package com.heshun.canvasdemo.customerView.tools;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

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
    /**
     * 利用向量的夹角公式计算夹角
     * cosBAC = (AB*AC)/(|AB|*|AC|)
     * 其中AB*AC是向量的数量积AB=(Bx-Ax,By-Ay)  AC=(Cx-Ax,Cy-Ay),AB*AC=(Bx-Ax)*(Cx-Ax)+(By-Ay)*(Cy-Ay)
     *
     * @param center 顶点 A
     * @param head   点1  B
     * @param touch  点2  C
     * @return
     */
    public static float includedAngle(PointF center, PointF head, PointF touch) {
        //判断方向  正左侧  负右侧 0线上,但是Android的坐标系Y是朝下的，所以左右颠倒一下
        float direction = (center.x - touch.x) * (head.y - touch.y) - (center.y - touch.y) * (head.x - touch.x);
        if(direction==0){
            return 0;
        }else{

            float angleCos= (float) (((head.x-center.x)*(touch.x-center.x)+(head.y-center.y)*(touch.y-center.y))/
                    ((Math.sqrt((head.x-center.x)*(head.x-center.x)+(head.y-center.y)*(head.y-center.y)))
                            *(Math.sqrt((touch.x-center.x)*(touch.x-center.x)+(touch.y-center.y)*(touch.y-center.y)))));
            System.out.println(angleCos+"angleCos");
            //弧度值*180
            float temAngle= (float) Math.toDegrees(Math.acos(angleCos));

            if (direction > 0) {//右侧顺时针为负
                return -temAngle;

            } else  {
                return temAngle;
            }
        }
    }
}
