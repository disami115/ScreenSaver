package MouseDraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import Canvas.MyCanvas;
import GUIs.SecondGUI;

public class DrawObjectText extends DrawObject{

	private static String txt;
	private static Boolean isTextSet = false;
	private Graphics2D g;
	
	public DrawObjectText(Graphics g, MouseEvent e) {
		super(g, e);
		this.g = (Graphics2D) g;
	}

	@Override
	public Graphics Draws(int x1, int y1, int x2, int y2, Color c) {
		DrawObject.setNewRect(x1, y1, x2, y2);
		Rectangle r = SecondGUI.textField.getBounds();
		int w = MyCanvas.bufferedImage.getWidth();
		int h = MyCanvas.bufferedImage.getHeight();
		g.setColor(c);
		if(MyCanvas.isTextEnter) {
			BufferedImage tempBf = new BufferedImage(w - (r.x-SecondGUI.g1.c.X), h - (r.y-SecondGUI.g1.c.Y), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2s = tempBf.createGraphics();
	        g2s.drawImage(MyCanvas.bufferedImage.getSubimage(r.x-SecondGUI.g1.c.X, r.y-SecondGUI.g1.c.Y, w - (r.x-SecondGUI.g1.c.X), h - (r.y-SecondGUI.g1.c.Y)), 0, 0, null);
			g2s.setColor(c);
			SecondGUI.textField.setBorder(null);
			SecondGUI.textField.setCaretColor(new Color(0,0,0,0));
			SecondGUI.textField.paint(g2s);
			g.drawImage(tempBf, r.x-SecondGUI.g1.c.X, r.y-SecondGUI.g1.c.Y, null);
		}
		return g;
	}
	
	
	public static String getTxt() {
		return txt;
	}
	
	public static Boolean getIsTextSet() {
		return isTextSet;
	}

}



