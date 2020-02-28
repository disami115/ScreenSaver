package GUIs;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import Screens.Screen;

public class SelectCoordGui extends JFrame implements MouseListener, MouseMotionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private static Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
	private static int lx = 0;
	private static int ly = 0;
	private static int bx = d.width;
	private static int by = d.height;
	private static int x1 = 0;
	private static int y1 = 0;
	private static int x2 = 0;
	private static int y2 = 0;
	private static boolean isPressed = false;
	private static Image img = null;
	private static BufferedImage bf = null;
	private static Rectangle r;
	private SecondGUI SecG;
	private ImageDraw imgD;
	private Rectangle screenRect = null;
	private static int lastX = 0, lastY = 0;	
	public SelectCoordGui(Image img, SecondGUI SecG) throws IOException {
		setDefault(img, SecG);
		addKeyListener(this);
		setUndecorated(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		Rectangle screenRect = new Rectangle(0, 0, 0, 0);
		for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
			screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
		}
		d.setSize(screenRect.width, screenRect.height);
	    this.setBounds(screenRect.x, screenRect.y, d.width, d.height);
	    InputStream in = SecondGUI.class.getClassLoader().getResourceAsStream("screencrs.png");
        Image curImage = ImageIO.read(in);
	    this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(curImage, new Point(19,19), "CustomCursor"));
	    
	}
	
	public void setDefault(Image img,  SecondGUI SecG) {
		Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
		screenRect = new Rectangle(0, 0, 0, 0);
		for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
			screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
		}
		d.setSize(screenRect.width, screenRect.height);
		lx = 0;
		ly = 0;
		bx = d.width;
		by = d.height;
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
		isPressed = false;
		r = null;
		SelectCoordGui.img = img;
		this.SecG = SecG;
	    imgD = new ImageDraw(img, null);
	    getContentPane().add(imgD);
	    bf = Screen.grabScreen();
	}

	public static void setXYWH(int x1, int x2, int y1, int y2){
		bx = Math.max(x1, x2) ; // bigX
		by = Math.max(y1, y2) ; // bigY
		lx = Math.min(x1, x2) ; // littleX
		ly = Math.min(y1, y2) ; // littleY
		r = new Rectangle(lx , ly, bx-lx, by-ly);
	}
	
	class ImageDraw extends JComponent
	{
		private static final long serialVersionUID = 1L;
		private int x = 0;
	    private int y = 0;
		private GeneralPath gp;
	    
	    ImageDraw (Image capture, MouseEvent e) {
	        if(isPressed) {
		        this.x = e.getX();
		        this.y = e.getY();
	        }
	    }
	   
	    @Override
	    public void paintComponent(Graphics g) {
	    	Graphics2D g2d  = (Graphics2D)g; 
	    	g2d.drawImage(img, 0, 0, this);
	    	g2d.setColor(Color.black);
	        AlphaComposite composite = AlphaComposite.SrcOver.derive( 0.3f );
            g2d.setComposite( composite );
            g2d.fillRect(screenRect.x, screenRect.y, screenRect.width - screenRect.x, screenRect.height - screenRect.y);
	        g2d.dispose();
	    	
	    }
	    
	    public void repaint(Graphics g, Image img)
	    {
	    	BufferedImage tempBf = new BufferedImage(screenRect.width, screenRect.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2s = tempBf.createGraphics();
	    	setXYWH(x1, this.x, y1, this.y);
	    	AlphaComposite composite = AlphaComposite.SrcOver.derive( 0.3f );
	    	gp = new GeneralPath();
            gp.moveTo(0, 0);
            gp.lineTo(screenRect.getWidth(), 0);
            gp.lineTo(screenRect.getWidth(), ly);
            gp.lineTo(lx, ly);
            gp.lineTo(lx, by);
            gp.lineTo(bx, by);
            gp.lineTo(bx, ly);
            gp.lineTo(screenRect.getWidth(), ly);
            gp.lineTo(screenRect.getWidth(), screenRect.getHeight());
            gp.lineTo(0, screenRect.getHeight());
            gp.lineTo(0, 0);
            Graphics2D g2d  = (Graphics2D)g; 
            g2s.drawImage(img, 0, 0, this);
            g2s.setColor(Color.red);
	        g2s.drawRect(r.x , r.y , r.width-1, r.height-1);
	    	g2s.setComposite(composite);
		    g2s.setColor(Color.black);
	        g2s.fill(gp);  
	    	g2d.drawImage(tempBf, 0, 0, null);
	    	g2d.dispose();
	    	g2s.dispose();
	    }
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3) cancelScreen();
		else {
			isPressed = true;
			x1 = e.getX();
			y1 = e.getY();
		}
		
		
    }

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() != MouseEvent.BUTTON3) {
			x2 = e.getX() ;
	        y2 = e.getY() ;
	        Screen.setXYWH(x1,x2,y1,y2);
	        if(isPressed) img = bf.getSubimage(r.x, r.y, r.width+1, r.height+1);
	        lastX = r.x;
	        lastY = r.y;
	        SecG.setNewImage(img);
	        SecG.expand();
			this.setVisible(false);
			isPressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getButton() != MouseEvent.BUTTON3) {
			imgD = new ImageDraw(img, e);
			imgD.repaint(getGraphics(), img);
			getContentPane().add(imgD);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelScreen();
		}
	}

	private void cancelScreen() {
		int[] HW = SecG.getImgHW();
		if(HW[0] == 0 || HW[1] == 0) {
			img = bf.getSubimage(lastX, lastY, 1, 1);
		}
		else img = SecG.c.img;
		SecG.setNewImage(img);
		SecG.expand();
		this.setVisible(false);
	}
	
}
