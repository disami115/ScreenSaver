package MouseDraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class DrawBrushLine extends DrawObject {

public DrawBrushLine(Graphics g, MouseEvent e) {
		super(g, e);
	}

	@Override
	public Graphics Draws(int x1, int y1, int x2, int y2, Color c) {
		Graphics g = this.g;
		int d = (int)super.getSizeDrawObject();
		g.setColor(c);
		((Graphics2D) g).setStroke(new BasicStroke((float) (d)));
		g.drawLine(x1, y1, x2, y2);
		g.dispose();
		return g;
	}

}
