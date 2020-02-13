package Screens;

import java.io.File;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.filechooser.FileSystemView;
import Screens.XYWH;

public class Screen {
	private static int lx = 0;
	private static int ly = 0;
	public static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	private static int bx = d.width;
	private static int by = d.height;
	public static Rectangle r = new Rectangle(lx, ly, bx-lx, by-ly);
	
	public static void SaveScreen(BufferedImage img, String name)
	{
		try {	
			ImageIO.write(img, "png", new File(getHomeDir(), name+".png"));
			System.out.println(getHomeDir() + name+".png");
		} catch (IOException e) {
			System.out.println("IO exception"+e);
		}
		
	}
	
	private static File getHomeDir() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		return fsv.getHomeDirectory();
	}

	public static BufferedImage grabScreen() { 
		try {
        	for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
        	    r = r.union(gd.getDefaultConfiguration().getBounds());
        	}
        	BufferedImage capture = new Robot().createScreenCapture(r);
        	return capture;
        } catch (AWTException e) {
            e.printStackTrace();
        } return null;
        
	}
	
	public static void setXYWH(int x1, int x2, int y1, int y2){
		r = XYWH.setXYWH(x1, x2, y1, y2);
	}
	
}
