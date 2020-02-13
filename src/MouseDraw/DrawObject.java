package MouseDraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.prefs.Preferences;

import GUIs.SecondGUI;
import Screens.XYWH;

public abstract class DrawObject {
	
	protected Graphics g = null;
	protected MouseEvent e = null;
	private Preferences userPrefsShadowSize;
	public static Rectangle r = null;
	protected static double SizeDrawObject = 2, SizeShadow = 0;
	
	public DrawObject(Graphics g, MouseEvent e){
		this.g = g;
		this.e = e;
		SizeDrawObject = SecondGUI.getSizeDrawObject();
		userPrefsShadowSize = Preferences.userRoot().node("config").node("ShadowSize");
		SizeShadow = userPrefsShadowSize.getInt("value", (int) SizeShadow);
	}
	
	abstract public Graphics Draws(int x1, int y1, int x2, int y2, Color c);
	
	public static void setNewRect(int x1, int y1, int x2, int y2) {
		r = XYWH.setXYWH(x1, x2, y1, y2);
	}
	
	public double getSizeDrawObject() {
		return SizeDrawObject;
	}
	
	public static Color setContrColor(Color c) {
		if(c.equals(Color.black) || c.equals(Color.gray) || c.equals(Color.blue) || c.equals(Color.red) || c.equals(Color.magenta)) {
			return Color.white;
		}
		else return Color.black;
	}
	
	
	public static void setShadowSize(int i) {
		SizeShadow = i;
	}
	
}
