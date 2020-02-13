package MouseDraw;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

public class DrawObjectArrow extends DrawObject {

	private int x, y;
	private GeneralPath gp;
	
	public DrawObjectArrow(Graphics g, MouseEvent e) {
		super(g, e);
	}

	@Override
	public Graphics Draws(int x1, int y1, int x2, int y2, Color c) {
		x = x1;
		y = y1;
		Graphics2D g = (Graphics2D) this.g;
		int t = (int)super.getSizeDrawObject();
		double a = Math.max(y1, y2) - Math.min(y1, y2);
		double b = Math.max(x1, x2) - Math.min(x1, x2);
		double d = Math.sqrt(a*a + b*b);
		int yd = y-(int)d;
		double ang = Math.asin(a/d);
		double dang = Math.toRadians(90);
		if(x > x2 && y >= y2) g.rotate(ang-dang, x, y);
		if(x1 >= x2 && y1 < y2) g.rotate(-dang-ang, x, y);
		if(x1 < x2 && y1 >= y2) g.rotate(dang-ang, x, y);
		if(x1 < x2 && y1 < y2) g.rotate(ang+dang, x, y);
		g.setStroke(new BasicStroke((float) (t)));
		gp = new GeneralPath();
		gp.moveTo(x, y); //1
		gp.lineTo(x - t/2, y); //2 
		gp.lineTo(x - t/2 , yd + t); //3
		gp.lineTo(x - t - 10 , yd + 25); //4
		gp.lineTo(x - t - 10 , yd + 25 - 2*t); //5
		gp.lineTo(x , yd - Math.sqrt(2) * t); //6
		gp.lineTo(x + t + 10 , yd + 25 - 2*t); //7
		gp.lineTo(x + t + 10 , yd + 25); //8
		gp.lineTo(x + t/2 , yd + t); //9
		gp.lineTo(x + t/2 , y); //10
		gp.lineTo(x, y);
		g.setColor(c);
		if(d > 10) {
			Color sc = DrawObject.setContrColor(c);
			drawShade(g, gp, sc, (int) DrawObject.SizeShadow + 1);
			g.setColor(c);
			g.fill(gp);
		}
		else {
			g.drawLine(x, yd, x - (int)((int)d/2), yd + (int)d);
			g.drawLine(x, yd, x + (int)((int)d/2), yd + (int)d);
		}
		g.dispose();
		return g;
	}
	
	private void drawShade ( Graphics2D g2d, GeneralPath gp, Color shadeColor, int width )
	{
	  Composite comp = g2d.getComposite ();
	  Stroke old = g2d.getStroke ();
	  width = width * 2;
	  for ( int i = width; i >= 2; i -= 2 )
	  {
	    float opacity = ( float ) ( width - i ) / ( width - 1 );
	    g2d.setColor ( shadeColor );
	    g2d.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, opacity ) );
	    g2d.setStroke ( new BasicStroke ( i ) );
	    g2d.draw ( gp );
	  }
	  g2d.setStroke ( old );
	  g2d.setComposite ( comp );
	}
}