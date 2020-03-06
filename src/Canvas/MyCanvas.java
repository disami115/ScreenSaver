package Canvas;

import javax.swing.*;
import GUIs.SecondGUI;
import MouseDraw.DrawBrushLine;
import MouseDraw.DrawObject;
import MouseDraw.DrawObjectArrow;
import MouseDraw.DrawObjectBlur;
import MouseDraw.DrawObjectBrush;
import MouseDraw.DrawObjectLine;
import MouseDraw.DrawObjectNumberRect;
import MouseDraw.DrawObjectOne;
import MouseDraw.DrawObjectRect;
import MouseDraw.DrawObjectText;
import Screens.Screen;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
 
public class MyCanvas extends JComponent implements MouseWheelListener, MouseMotionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	private double zoom = 1.0;
    public Image img;
    public static final double SCALE_STEP = 0.05d;
    private Dimension initialSize;
    private AffineTransform tx = new AffineTransform();
    public static BufferedImage bufferedImage = null;
    public static Graphics g;
    protected SecondGUI SecG;
    public MouseEvent e;
    public Graphics2D lastG, g2t;
    private DrawObject drawObj;
	public DrawObjects dOs;
	private boolean isPressed = false;
	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;
	private double scrollX = 0d;
	private double scrollY = 0d;
	private boolean isReleased = false;
	private double previousZoom;
	private int rx1;
	private int ry1;
	private int lastX = 0;
	private int lastY = 0;
	private static Color color;
	private ArrayList<Image> listOfBf = new ArrayList<Image>();
    private int numberOfGraphicsList = -1;
	private boolean isMr;
    public static BufferedImage tempBf = null;
    public static Boolean isTextEnter = false;
    public int X,Y;
    
    public enum DrawObjects{
    	DrawObjectBrush,
    	DrawObjectArrow,
    	DrawObjectOne,
    	DrawObjectLine,
    	DrawObjectBlur,
    	DrawObjectText,
    	DrawObjectRect,
    	DrawObjectNumberRect
    }
    public MyCanvas(double zoom, Image img) {
        this.zoom = zoom;
        this.img = img;
        
        int w,h;
        w = img.getWidth(null) + 29;
        h = img.getHeight(null) + 105;
        if(h > 504 || w > 880) {
        	Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        	if(h > maxBounds.height || w > maxBounds.width) {
        		h = maxBounds.height - 95;
        		w = maxBounds.width - 19;
        	}
        	SecondGUI.g1.setBounds(Screen.d.width/2 -(w/2), Screen.d.height/2 - (h/2), w, h);
        }
        changeXY();
        bufferedImage = new BufferedImage(this.img.getWidth(null), this.img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        SecG = SecondGUI.g1;
        color = SecG.getColor();
        changeDrawObjects(DrawObjects.DrawObjectBrush.toString());
        setDrawObject(dOs, (Graphics2D) this.getGraphics());
        listOfBf.add(img);
        numberOfGraphicsList = 0;
        initialSize = new Dimension(
        	(int)(img.getWidth(null)*zoom),
        	(int)(img.getHeight(null)*zoom));
        Dimension d = new Dimension(
        	(int)(initialSize.width*zoom),
        	(int)(initialSize.height*zoom));
        setPreferredSize(d);
        setSize(d);
        
        
    }
    
    private void changeXY() {
    	int w,h;
        if(SecondGUI.CanvPan != null) {
        	w = SecondGUI.g1.getWidth() - 19;
        	h = SecondGUI.g1.getHeight() - 95;
        	if(SecondGUI.CanvPan.getSize().width < img.getWidth(null)) w = img.getWidth(null) ;
        	if(SecondGUI.CanvPan.getSize().height < img.getHeight(null)) h = img.getHeight(null) ; 
        }
        else {
        	w = 881;
        	h = 505;
        }
        this.X = (Math.abs(w - img.getWidth(null)))/2;
        this.Y = (Math.abs(h - img.getHeight(null)))/2;
        
	}

	public void paint(Graphics g) {
    	this.paintComponent(g);
    }
    
    public String getDrawObject() {
    	return ""+dOs.name();
    }
    
    public void changeDrawObjects(String s) {
    	dOs = DrawObjects.valueOf(s);
    }
 
    private void setDrawObject(DrawObjects dOs, Graphics2D g2s) {
    	switch(dOs){
    		case DrawObjectBrush: 
    			this.drawObj = new DrawObjectBrush(g2s, this.e);
    			break;
    		case DrawObjectArrow: 
    			this.drawObj = new DrawObjectArrow(g2s, this.e);
    			break;
    		case DrawObjectOne: 
    			this.drawObj = new DrawObjectOne(g2s, this.e);
    			break;
    		case DrawObjectLine: 
    			this.drawObj = new DrawObjectLine(g2s, this.e);
    			break;
    		case DrawObjectBlur: 
    			this.drawObj = new DrawObjectBlur(g2s, this.e);
    			break;
    		case DrawObjectText: 
    			this.drawObj = new DrawObjectText(g2s, this.e);
    			break;	
    		case DrawObjectRect: 
    			this.drawObj = new DrawObjectRect(g2s, this.e);
    			break;	
    		case DrawObjectNumberRect: 
    			this.drawObj = new DrawObjectNumberRect(g2s, this.e);
    			break;	
		default:
			break;
    	}
    }
    
    public Dimension getInitialSize() {
        return initialSize;
    }
 
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        changeXY();
        //CanvasPanel.changePanel(img.getWidth(null) + this.X, img.getHeight(null) + this.Y);
        Graphics2D g2s = bufferedImage.createGraphics();
        Graphics2D g2d = (Graphics2D) g.create();
        if(isPressed && dOs.name() == "DrawObjectBrush") g2s.drawImage(img, 0, 0, null);
        else g2s.drawImage(listOfBf.get(numberOfGraphicsList), 0, 0, null);
        g2d.transform(tx);
        g2s.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2s.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        if(drawObj != null){
	        setDrawObject(dOs,g2s);
	        if(isPressed && dOs.name() == "DrawObjectBrush") {
					if(lastX != 0 && lastY != 0) 
						{
						DrawBrushLine bl = new DrawBrushLine(g2s,e);
						g2s = (Graphics2D) bl.Draws(lastX - this.X, lastY - this.Y, x2 - this.X, y2 - this.Y, color);
						}
					lastX = x2;
					lastY = y2;
			}
	        else if(isTextEnter) {
	        	g2s = (Graphics2D) drawObj.Draws(x1, y1 , x2, y2, color);
	        }
	        else if(isReleased) {
	        	g2s = (Graphics2D) drawObj.Draws(x1 - this.X, y1 - this.Y, x2 - this.X, y2 - this.Y, color);
	        }
        }
        super.paintComponent(g2s);
        g2s.dispose();
		img = bufferedImage;
		if (img != null) {
			g2d.drawImage(img, this.X, this.Y, null);
		}
		
        
        g2s.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2s.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF); 
    }
  
    protected void repaint(Graphics g, MouseEvent e) {
    	super.paintComponent(g);
    	changeXY();
    	CanvasPanel.changePanel(img.getWidth(null) + this.X + 20, img.getHeight(null) + this.Y + 20);
        x2 = e.getX();
		y2 = e.getY();
		x1 = rx1;
		y1 = ry1;
		Graphics2D g2s  = (Graphics2D)g;
		bufferedImage = new BufferedImage(this.img.getWidth(null), this.img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.drawImage(img, 0, 0, null);
    	setDrawObject(dOs,g2d);
    	g2d = (Graphics2D) drawObj.Draws((int)(x1 - this.X * zoom), (int)(y1 - this.Y *zoom), x2 - this.X, y2 - this.Y, color);
        g2d.transform(tx);
        g2s.drawImage(bufferedImage, this.X, this.Y, null);
        g2d.dispose();
        g2s.dispose();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF); 
    }
    
    public BufferedImage getBufferedImage(Graphics g) {
    	zoom = 1;
    	paintComponent(g);
        return bufferedImage;
    }
    
    @Override
    public void setSize(Dimension size) {
        super.setSize(size);
        if (initialSize == null) {
            this.initialSize = size;
        }
    }
 
    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        if (initialSize == null) {
            this.initialSize = preferredSize;
        }
    }
 
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Rectangle visibleRect = getVisibleRect();
        int wr = -10*e.getWheelRotation();
        if(SecG.isAlt) {
        	scrollX = previousZoom*zoom - (wr-visibleRect.getX());
        	scrollY = visibleRect.getY();
        }
        else {
        	scrollX = visibleRect.getX();
        	scrollY = previousZoom*zoom - (wr-visibleRect.getY());
        }
        visibleRect.setRect(scrollX, scrollY, visibleRect.getWidth(), visibleRect.getHeight());
        scrollRectToVisible(visibleRect);
    }

    public void followMouseOrCenter(MouseWheelEvent e) {
        
    }
 
    
    
    public void mouseDragged(MouseEvent e) {
    	x2 = (int) (e.getX() / zoom);
        y2 = (int) (e.getY() / zoom);
        DrawObject.setNewRect(x1, y1, x2, y2);
        setDrawObject(dOs, (Graphics2D) this.getGraphics());
        if((isPressed && dOs.name() == "DrawObjectBrush")) {
        	this.paintComponent(this.getGraphics());
        }
        else {
        	if(dOs.name() == "DrawObjectText")
        	{
        		changeDrawObjects(DrawObjects.DrawObjectRect.toString());
        		setDrawObject(dOs, (Graphics2D) this.getGraphics());
        		DrawObjectRect.setForTxt(true);
        		this.repaint(this.getGraphics(), e);
        		DrawObjectRect.setForTxt(false);
            	changeDrawObjects(DrawObjects.DrawObjectText.toString());
            	setDrawObject(dOs, (Graphics2D) this.getGraphics());
        	}
        	else this.repaint(this.getGraphics(), e);
        }
    }
 
    public void mouseMoved(MouseEvent e) {
    }
 
    public void mouseClicked(MouseEvent e) {
    	if(!isTextEnter && dOs.name() == "DrawObjectText") {
    		isTextEnter = true;
    		SecondGUI.textField.setVisible(false);
            mReleased(this.e);
    	}
    }
 
    public void mousePressed(MouseEvent e) {
    	isPressed = true;
		x1 = (int) (e.getX() / zoom);
		y1 = (int) (e.getY() / zoom);
		rx1 = x1;
		ry1 = y1;
    }
 
    public void mouseReleased(MouseEvent e) {
    	this.e = e;
    	setMr(true);
        if(!SecondGUI.textField.isVisible()) mReleased(e);
        else {
        	isTextEnter = true;
    		SecondGUI.textField.setVisible(false);
            mReleased(this.e);
            if(dOs.name() == "DrawObjectText") mReleased(this.e);
        }
    }
    
    public void mReleased(MouseEvent e) {
    	x2 = (int) (e.getX() / zoom);
        y2 = (int) (e.getY() / zoom);
        DrawObject.setNewRect(x1, y1, x2, y2);
        isReleased  = true;
        if(dOs.name() == "DrawObjectBlur") DrawObjectBlur.setBlur();
        tempBf = new BufferedImage(this.img.getWidth(null), this.img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2s = tempBf.createGraphics();
        g2s.setColor(color);
        this.paintComponent(this.getGraphics());
        if(listOfBf.size() - 1 != numberOfGraphicsList) {
    		int ls = listOfBf.size();
    		for(int i = ls-1; i > numberOfGraphicsList; i--) {
    			listOfBf.remove(i);
    		}
    	}
        if(dOs.name() == "DrawObjectOne" || dOs.name() == "DrawObjectNumberRect") SecondGUI.changeNumber(dOs.name(), 0);
    	g2s.drawImage(img, 0, 0, (int)(img.getWidth(null)* zoom), (int)(img.getHeight(null)*zoom), this);
        listOfBf.add(tempBf);
        numberOfGraphicsList = listOfBf.size()-1;
    	if(dOs.name() == "DrawObjectText" && !SecondGUI.textField.isVisible()) {
        	Rectangle r = DrawObject.r;
        	Font f = new Font(null, 1, SecondGUI.FontSize);
        	SecondGUI.textField.setFont(f);
        	SecondGUI.textField.setCaretPosition(0);
        	SecondGUI.textField.setEditable(true);
        	r.height += 2;
        	r.width += 2;
        	if(r.width > bufferedImage.getWidth()) r.width = bufferedImage.getWidth();
        	if(r.height > bufferedImage.getHeight()) r.height = bufferedImage.getHeight();
        	if(r.x < 0) r.x = 0;
        	if(r.y < 0) r.y = 0;
        	if(r.x + r.width > bufferedImage.getWidth()) r.x = bufferedImage.getWidth() - r.width;
        	if(r.y + r.height > bufferedImage.getHeight()) r.y = bufferedImage.getHeight() - r.height;
        	r.x += this.X;
        	r.y += this.Y;
        	SecondGUI.textField.setBounds(r);
        	SecondGUI.textField.setText("");
        	SecondGUI.textField.setOpaque(false);
        	SecondGUI.textField.setBorder(new JTextField().getBorder());
			SecondGUI.textField.setCaretColor(new Color(0,0,0));
        	SecondGUI.textField.setVisible(!isTextEnter);
        	isTextEnter = false;
        }
    	isPressed = false;
        isReleased = false;
        lastX = 0;
        lastY = 0;
		System.gc();
		Runtime.getRuntime().gc();
    }
    
    public Image getImg() {
    	return this.img;
    }
 
    public void mouseEntered(MouseEvent e) {
 
    }
 
    public void mouseExited(MouseEvent e) {
 
    }
    
    public static void setColor(Color color) {
    	MyCanvas.color = color;
    }
    
    public void setLastGraphics(Graphics2D g) {
    	if(numberOfGraphicsList > 0) {
    		numberOfGraphicsList = numberOfGraphicsList - 1;
    		if(dOs.name() == "DrawObjectOne" || dOs.name() == "DrawObjectNumberRect") {
    			SecondGUI.changeNumber(dOs.name(), -1);
			}
    	}
    	this.paintComponent(this.getGraphics());
    }
    
    public void setNextGraphics(Graphics2D g) {
    	if(numberOfGraphicsList < (listOfBf.size() - 1)) {
    		numberOfGraphicsList = numberOfGraphicsList + 1;
    		if(dOs.name() == "DrawObjectOne" || dOs.name() == "DrawObjectNumberRect") {
				SecondGUI.changeNumber(dOs.name(), 0);
			}
    	}
    	this.paintComponent(this.getGraphics());
    }
    
    public static BufferedImage getBf() {
    	return bufferedImage;
    }

	public boolean isMr() {
		return isMr;
	}

	public void setMr(boolean isMr) {
		this.isMr = isMr;
	}

}
