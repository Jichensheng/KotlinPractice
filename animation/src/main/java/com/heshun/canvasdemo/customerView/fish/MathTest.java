package com.heshun.canvasdemo.customerView.fish;


/**
 * author：Jics
 * 2017/7/11 10:38
 */
public class MathTest {
	public static void main(String[] args) {
		double a = 30;
		double b = 150;

		double aa = Math.toRadians(a);//  π/6
		double bb = Math.toRadians(b);//  π/4
		System.out.println("" + ((-1) % 360));
		System.out.println(String.format("a = %s\nb = %s\naa = %s\nbb = %s\nsin(aa) = %s\nsin(bb) = %s\n ", a, b, aa, bb, Math.sin(aa), Math.sin(bb)));

		PointF start = new PointF(100, 100);
		PointF end = calculatPoint(start, 100, 90);
		System.out.println(end.toString());

		System.out.println("........" + caculatAngle(new PointF(0, 0), new PointF(-10, -10)));
		System.out.println("...++....." + includedAngle(new PointF(700,400),new PointF(800,200),new PointF(701,0)));
	}

	public static float caculatAngle(PointF startPoint, PointF endPoint) {
		float deltaY = startPoint.y - endPoint.y;
		float deltaX = endPoint.x - startPoint.x;
		return (float) Math.toDegrees(Math.atan((deltaY / (deltaX == 0 ? 0.1 : deltaX))));
	}

	private static PointF calculatPoint(PointF startPoint, float length, float angle) {
		float deltaX = (float) Math.cos(Math.toRadians(angle)) * length;
		float deltaY = (float) Math.sin(Math.toRadians(angle)) * length;
		return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
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

	/**
	 * 判断touch在点的哪侧
	 *
	 * @param center
	 * @param head
	 * @param touch
	 * @return 1左侧  -1右侧 0线上
	 */
	public static int alignWhere(PointF center, PointF head, PointF touch) {
		//[(x1-x3)*(y2-y3)-(y1-y3)*(x2-x3)]/2
		float result = (center.x - touch.x) * (head.y - touch.y) - (center.y - touch.y) * (head.x - touch.x);
		if (result < 0) {
			return -1;
		} else if (result > 0) {
			return 1;
		} else
			return 0;
	}

	static class PointF {
		public float x;
		public float y;

		public PointF(float x, float y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "PointF{" +
					"x=" + x +
					", y=" + y +
					'}';
		}
	}
}
