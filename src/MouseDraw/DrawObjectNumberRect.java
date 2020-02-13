package MouseDraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import GUIs.SecondGUI;

public class DrawObjectNumberRect extends DrawObject {

	public static int i = 1;
	private static Color sc = Color.black;
	public DrawObjectNumberRect(Graphics g, MouseEvent e) {
		super(g, e);
		// TODO Auto-generated constructor stub
	}
	
	public static String getI() {
		if(i > 9) return "r" + ".png";
		else return "r" + i + ".png";
	}
	
	public static void changeI() {
		//if(i == 9) i = 0;
		i++;
	}
	
	public static void changeLastI() {
		if(i != 1) i--;
	}
	
	public static void changeI(int i) {
		DrawObjectNumberRect.i = i;
		
	}

	@Override
	public Graphics Draws(int x1, int y1, int x2, int y2, Color c) {
		Font f = new Font(null, 1, 20);
		sc = DrawObject.setContrColor(c);
		boolean iswh = r.width < 5 && r.height < 5;
		Graphics2D g = (Graphics2D)this.g;
		int d = (int)super.getSizeDrawObject();
		g.setStroke(new BasicStroke((float) (d)));
		g.setFont(f);
		
		g.setStroke(new BasicStroke((float) (d)));
		if(!iswh) {
			DrawObject.setNewRect(x1, y1, x2, y2);
			g.setColor(c);
			g.drawRect(r.x, r.y, r.width, r.height);
			g.setColor(sc);
			((Graphics2D) g).setStroke(new BasicStroke((float) DrawObject.SizeShadow));
			if(DrawObject.SizeShadow != 0) g.drawRect((int) (r.x-(d/2) - DrawObject.SizeShadow) + 1,(int)(r.y-(d / 2) - DrawObject.SizeShadow) + 1, (int) (r.width+d + 2* DrawObject.SizeShadow) -2, (int) (r.height+d + 2 * DrawObject.SizeShadow) - 2);
			
		}
		g.setColor(c);
		if(iswh) g.fillOval(x1-19, y1-19, 40, 40);
		else g.fillOval(x2 - 15, y2 - 15, 30, 30);
		g.setStroke(new BasicStroke((float) (DrawObject.SizeShadow)));
		g.setColor(sc);
		if(iswh) g.drawOval(x1-19, y1-19, 40, 40);
		else g.drawOval(x2-15, y2-15, 30, 30);
		if(i < 10) {
			if(iswh) g.drawString(""+i, x1 - 4, y1 + 9);
			else g.drawString(""+i, x2 - 5, y2 + 8);
		}
		else {
			if(iswh) g.drawString(""+i, x1 - 10, y1 + 9);
			else g.drawString(""+i, x2 - 10, y2 + 8);
		}
		SecondGUI.NumberRectButton.setToolTipText("Прямоугольник №" + (DrawObjectNumberRect.i + 1));
		g.dispose();
		return g;
	}

}
