package MouseDraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class DrawObjectOne extends DrawObject {

	private static int i = 1;
	public DrawObjectOne(Graphics g, MouseEvent e) {
		super(g, e);
		// TODO Auto-generated constructor stub
	}
	
	public static String getI() {
		return i + ".png";
	}
	
	public static void changeI() {
		if(i == 9) i = 0;
		i++;
	}
	
	public static void changeLastI() {
		if(i != 1) i--;
	}

	public static void changeI(int i) {
		DrawObjectOne.i = i;
	}
	
	@Override
	public Graphics Draws(int x1, int y1, int x2, int y2, Color c) {
		Font f = new Font(null, 1, 30);
		Graphics2D g = (Graphics2D) this.g;
		int d = (int)super.getSizeDrawObject();
		g.setColor(Color.lightGray);
		((Graphics2D) g).setStroke(new BasicStroke((float) (d)));
		g.fillOval(x2-(16+d/2), y2-(34+d/2), 49+d, 49+d);
		g.setColor(c);
		g.drawOval(x2-12, y2-30, 40, 40);
		g.setFont(f);
		g.drawString(""+i, x2, y2);
		g.dispose();
		return g;
	}

}
