package MouseDraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class DrawObjectBrush extends DrawObject {
	
	public DrawObjectBrush(Graphics g, MouseEvent e) {
		super(g, e);
	}
	
	@Override
	public Graphics Draws(int x1, int y1, int x2, int y2, Color c) {
		Graphics g = this.g;
		g.setColor(c);
		g.fillOval(x2, y2, 10, 10);
		g.dispose();
		return g;
	}
	
	public Graphics Draws(int x2, int y2, Color c) {
		Graphics2D g = (Graphics2D) this.g;
		g.setColor(c);
		g.fillOval(x2, y2, (int)super.getSizeDrawObject(), (int)super.getSizeDrawObject());
		g.dispose();
		return g;
	}
	
}
