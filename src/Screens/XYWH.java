package Screens;

import java.awt.Rectangle;

public class XYWH {
	
	private static int bx;
	private static int by;
	private static int lx;
	private static int ly;
	XYWH(){
		
	}

	public static Rectangle setXYWH(int x1, int x2, int y1, int y2){
		bx = Math.max(x1, x2) ; // bigX
		by = Math.max(y1, y2) ; // bigY
		lx = Math.min(x1, x2) ; // littleX
		ly = Math.min(y1, y2) ; // littleY
		return new Rectangle(lx, ly, bx-lx, by-ly);
	}
}
