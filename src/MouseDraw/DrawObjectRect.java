package MouseDraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class DrawObjectRect extends DrawObject {
	
	private static boolean isForTxt = false;

	public DrawObjectRect(Graphics g, MouseEvent e) {
		super(g, e);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Graphics Draws(int x1, int y1, int x2, int y2, Color c) {
		Graphics g = this.g;
		int d = (int)super.getSizeDrawObject();
		Color sc = DrawObject.setContrColor(c);
		DrawObject.setNewRect(x1, y1, x2, y2);
		if(isForTxt) {
			((Graphics2D) g).setStroke(new BasicStroke((float) (1)));
			g.setColor(Color.black);
			g.drawRect(r.x, r.y, r.width, r.height);
		}
		else {
			((Graphics2D) g).setStroke(new BasicStroke((float) (d)));
			g.setColor(c);
			g.drawRect(r.x, r.y, r.width, r.height);
			g.setColor(sc);
			((Graphics2D) g).setStroke(new BasicStroke((float) DrawObject.SizeShadow));
			if(DrawObject.SizeShadow != 0) g.drawRect((int) (r.x-(d/2) - DrawObject.SizeShadow) + 1,(int)(r.y-(d / 2) - DrawObject.SizeShadow) + 1, (int) (r.width+d + 2* DrawObject.SizeShadow) -2, (int) (r.height+d + 2 * DrawObject.SizeShadow) - 2);
		}
		g.dispose();
		return g;
	}

	public static void setForTxt(boolean isForTxt) {
		DrawObjectRect.isForTxt = isForTxt;
	}

}
