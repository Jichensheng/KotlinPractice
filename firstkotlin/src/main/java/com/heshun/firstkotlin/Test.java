package com.heshun.firstkotlin;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/6/15 14:08
 */
public class Test {
	public static void main(String[] args) {

            int y0=100;
            int y1=500;
            float bounce=0.0f;
            for (float i = 0; i <= 1; i+=0.01) {
                System.out.println((i*y1-y0)*(i*y1-y0)/400);
            }
	/*	printSwitch();


		Test test = new Test();
		List<ChildView> childs = test.getChilds();
		int margin = 10;
		int l = 0;
		int t = 0;
		int r = 0;
		int b = 0;
		for (int i = 0; i < childs.size(); i++) {
			ChildView childView = childs.get(i);
			if (i != 0) {
				//上一个高度加上margin
				t += margin + childView.getHeight();
			}
			b = t + childView.getHeight();
			r = childView.getWidth();
			System.out.println(childView.setLtrb(l,t,r,b)
										.toString());
		}*/
	}

	public static void printSwitch(){
		int[] i=new int[]{1,2,3,4,5,6,7,8,9};
		for (int i1 = 0; i1 < i.length; i1++) {
			switch (i[i1]%4){
				case 0:
					System.out.println(i[i1]+"被4整除");
					break;
				case 99: case 1:
					System.out.println(i[i1]+"被4除余1或2");
					break;
				case 3:
					System.out.println(i[i1]+"被4除余3");
					break;
			}
		}
	}

	/**
	 * int[] i = getBlock(6, 6);
	 * System.out.println(Arrays.toString(i));
	 *
	 * @param width
	 * @param height
	 * @return
	 */
	public static int[] getBlock(int width, int height) {
		int totalPiexl = width * height;
		int half = width / 2;
		int[] result = new int[totalPiexl];
		for (int i = 0; i < totalPiexl; i++) {
			if (i % height < half) {
				result[i] = 0xFFFFFFFF;
			} else result[i] = 0xFFCCCCCC;
		}
		return result;
	}

	public List<ChildView> getChilds() {
		List<ChildView> childViews = new ArrayList<>();
		childViews.add(new ChildView(100, 50));
		childViews.add(new ChildView(100, 30));
		childViews.add(new ChildView(100, 40));
		childViews.add(new ChildView(100, 62));
		childViews.add(new ChildView(100, 70));
		childViews.add(new ChildView(100, 83));
		return childViews;
	}

	class ChildView {
		int width;
		int height;
		int l, t, r, b;

		public ChildView(int height, int width) {
			this.height = height;
			this.width = width;
		}

		public ChildView(int width, int height, int l, int t, int r, int b) {
			this.width = width;
			this.height = height;
			this.l = l;
			this.t = t;
			this.r = r;
			this.b = b;
		}

		public ChildView setLtrb(int l, int t, int r, int b) {
			this.l = l;
			this.t = t;
			this.r = r;
			this.b = b;
			return this;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getL() {
			return l;
		}

		public void setL(int l) {
			this.l = l;
		}

		public int getT() {
			return t;
		}

		public void setT(int t) {
			this.t = t;
		}

		public int getR() {
			return r;
		}

		public void setR(int r) {
			this.r = r;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}

		@Override
		public String toString() {
			return "ChildView{" +
					"width=" + width +
					", height=" + height +
					", l=" + l +
					", t=" + t +
					", r=" + r +
					", b=" + b +
					'}';
		}
	}
}
