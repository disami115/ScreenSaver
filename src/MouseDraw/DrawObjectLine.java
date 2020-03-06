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

public class DrawObjectLine extends DrawObject {
	
	private int x, y;
	private GeneralPath gp;
	private static boolean isShift = false;
	public DrawObjectLine(Graphics g, MouseEvent e) {
		super(g, e);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Graphics Draws(int x1, int y1, int x2, int y2, Color c) {
		x = x1;
		y = y1;
		Graphics2D g = (Graphics2D) this.g;
		int t = (int)super.getSizeDrawObject();
		if(DrawObjectLine.isShift) {
			double a = Math.max(y1, y2) - Math.min(y1, y2);
			double b = Math.max(x1, x2) - Math.min(x1, x2);
			double d = Math.sqrt(a*a + b*b);
			int yd = y-(int)d;
			int area = getArea(x1, x2, y1, y2);
			switch(area) {
				case(1): 
					g.rotate(Math.toRadians(0), x, y);
					break;
				case(2): 
					g.rotate(Math.toRadians(45), x, y);
					break;
				case(3): 
					g.rotate(Math.toRadians(90), x, y);
					break;
				case(4): 
					g.rotate(Math.toRadians(135), x, y);
					break;
				case(5): 
					g.rotate(Math.toRadians(180), x, y);
					break;
				case(6): 
					g.rotate(Math.toRadians(225), x, y);
					break;
				case(7): 
					g.rotate(Math.toRadians(270), x, y);
					break;
				case(8): 
					g.rotate(Math.toRadians(315), x, y);
					break;
			}
			g.setStroke(new BasicStroke((float) (t)));
			gp = new GeneralPath();
			gp.moveTo(x, y); //1
			gp.lineTo(x - t/2, y); //2 
			gp.lineTo(x - t/2 , yd + t); //3
			gp.lineTo(x + t/2 , yd + t); //4
			gp.lineTo(x + t/2 , y); //5
			gp.lineTo(x, y);//6
			g.setColor(c);
			Color sc = DrawObject.setContrColor(c);
			drawShade(g, gp, sc, (int) DrawObject.SizeShadow + 1);
			g.setColor(c);
			g.fill(gp);
			
		}
		else {
			g.setColor(Color.lightGray);
			((Graphics2D) g).setStroke(new BasicStroke((float) (t*2)));
			g.setColor(c);
			((Graphics2D) g).setStroke(new BasicStroke((float) (t)));
			g.drawLine(x1, y1, x2, y2);
		}
		g.dispose();
		return g;
	}
	
	private int getArea(int x1, int x2, int y1, int y2) {
		if( (y2 < y1) && ( (y1 - y2  > 2*(x2 - x1) ) && (y1 - y2  > -2*(x2 - x1) ) ) ) return 1;
		else if( (x2 > x1) && ( (x1 - x2  > 2*(y2 - y1) ) && (x1 - x2  < (y2 - y1)/2 ) ) ) return 2;
		else if( (x2 > x1) && ( (x1 - x2  < 2*(y2 - y1) ) && (x1 - x2  < -2*(y2 - y1) ) ) ) return 3;
		else if( (x2 > x1) && ( (x1 - x2  > -2*(y2 - y1) ) && (x1 - x2  < -(y2 - y1)/2 ) ) ) return 4;
		else if( (y2 > y1) && ( (y1 - y2  < 2*(x2 - x1) ) && (y1 - y2  < -2*(x2 - x1) ) ) ) return 5;
		else if( (x2 < x1) && ( (x1 - x2  < 2*(y2 - y1) ) && (x1 - x2  > (y2 - y1)/2 ) ) ) return 6;
		else if( (x2 < x1) && ( (x1 - x2  > 2*(y2 - y1) ) && (x1 - x2  > -2*(y2 - y1) ) ) ) return 7;
		else if((x2 < x1) && ( (x1 - x2  < -2*(y2 - y1) ) && (x1 - x2  > -(y2 - y1)/2 ) )) return 8;
		return 0;
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
	
	public static void setShift(boolean isShift) {
		DrawObjectLine.isShift = isShift;
	}

}
