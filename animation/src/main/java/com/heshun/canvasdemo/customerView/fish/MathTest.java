package com.heshun.canvasdemo.customerView.fish;


/**
 * author：Jics
 * 2017/7/11 10:38
 */
public class MathTest {
	public static void main(String[] args){
		double a=30;
		double b=150;

		double aa=Math.toRadians(a);//  π/6
		double bb=Math.toRadians(b);//  π/4
		System.out.println(""+((-1)%360));
		System.out.println(String.format("a = %s\nb = %s\naa = %s\nbb = %s\nsin(aa) = %s\nsin(bb) = %s\n ",a,b,aa,bb,Math.sin(aa),Math.sin(bb)));

		PointF start=new PointF(100,100);
		PointF end=calculatPoint(start,100,90);
		System.out.println(end.toString());
	}

	private static PointF calculatPoint(PointF startPoint, float length, float angle) {
		float deltaX = (float) Math.cos(Math.toRadians(angle)) * length;
		float deltaY = (float) Math.sin(Math.toRadians(angle)) * length;
		return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
	}
	static class  PointF{
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
