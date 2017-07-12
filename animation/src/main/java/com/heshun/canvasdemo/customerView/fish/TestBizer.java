package com.heshun.canvasdemo.customerView.fish;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * author：Jics
 * 2017/7/12 12:48
 */
public class TestBizer extends View {
	private static final String TAG = "TestBizer++++++++";
	private Paint paint;

	public TestBizer(Context context) {
		this(context, null);
	}

	public TestBizer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TestBizer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawTr(canvas, new PointF(300, 400), new PointF(400, 200), new PointF(0, 700));//钝角
		drawTr(canvas, new PointF(700, 400), new PointF(800, 200), new PointF(700, 350));//钝角
		drawTr(canvas, new PointF(500, 400), new PointF(600, 200), new PointF(450, 900));//钝角
		drawTr(canvas, new PointF(900, 800), new PointF(1000, 600), new PointF(800, 1000));//钝角
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(1000, 1000);
	}

	private void drawTr(Canvas canvas, PointF middle, PointF head, PointF touch) {
		canvas.drawCircle(middle.x, middle.y, 4, paint);
		canvas.drawCircle(head.x, head.y, 4, paint);
		canvas.drawCircle(touch.x, touch.y, 7, paint);
		canvas.drawLine(middle.x, middle.y, head.x, head.y, paint);
		canvas.drawLine(head.x, head.y, touch.x, touch.y, paint);
		canvas.drawLine(touch.x, touch.y, middle.x, middle.y, paint);
		Path path = new Path();
		path.moveTo(middle.x, middle.y);
		float angle = includedAngle(middle, head, touch);
		float delta = calcultatAngle(middle, head);
		PointF controlF = calculatPoint(middle, 224, angle / 2 + delta);
		canvas.drawCircle(controlF.x, controlF.y, 4, paint);
		path.cubicTo(head.x, head.y, controlF.x, controlF.y, touch.x, touch.y);
		canvas.drawPath(path, paint);
	}

	/**
	 * 起点长度角度计算终点
	 * 正逆负顺
	 *
	 * @param startPoint
	 * @param length
	 * @param angle
	 * @return
	 */
	private static PointF calculatPoint(PointF startPoint, float length, float angle) {
		float deltaX = (float) Math.cos(Math.toRadians(angle)) * length;
		//符合Android坐标的y轴朝下的标准
		float deltaY = (float) Math.sin(Math.toRadians(angle - 180)) * length;
		return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
	}

	/**
	 * 线和x轴夹角
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public static float calcultatAngle(PointF start, PointF end) {
		return includedAngle(start, new PointF(start.x + 1, start.y), end);
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
		float abc = (head.x - center.x) * (touch.x - center.x) + (head.y - center.y) * (touch.y - center.y);
		float angleCos = (float) (abc /
				((Math.sqrt((head.x - center.x) * (head.x - center.x) + (head.y - center.y) * (head.y - center.y)))
						* (Math.sqrt((touch.x - center.x) * (touch.x - center.x) + (touch.y - center.y) * (touch.y - center.y)))));
		System.out.println(angleCos + "angleCos");
		//弧度值*180
		float temAngle = (float) Math.toDegrees(Math.acos(angleCos));
		//判断方向  正左侧  负右侧 0线上,但是Android的坐标系Y是朝下的，所以左右颠倒一下
		float direction = (center.x - touch.x) * (head.y - touch.y) - (center.y - touch.y) * (head.x - touch.x);
		if (direction == 0) {
			if (abc >= 0) {
				return 0;
			} else
				return 180;
		} else {
			if (direction > 0) {//右侧顺时针为负
				return -temAngle;
			} else {
				return temAngle;
			}
		}
	}
}
