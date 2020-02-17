package GUIs;

import static java.awt.SystemTray.getSystemTray;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList;
import java.util.List;

import Saves.SaveToFile;
import Screens.Screen;
import Canvas.CanvasPanel;
import Canvas.MyCanvas;
import Canvas.MyCanvas.DrawObjects;
import Canvas.SystemColorChooserPanel;
import MouseDraw.DrawObjectNumberRect;
import MouseDraw.DrawObjectOne;
import Converter.ByteToList;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class SecondGUI extends JFrame implements NativeKeyListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JButton ScreenButton = new JButton();
	private JButton SaveButton = new JButton();
	private JButton ArrowButton = new JButton();
	private JButton BrushButton = new JButton();
	private static JButton Button1 = new JButton();
	private JButton LineButton = new JButton();
	private JButton TextButton = new JButton();
	private JButton BlurButton = new JButton();
	private JButton SaveServButton = new JButton();
	private JButton RectangleButton = new JButton();
	public static JButton NumberRectButton = new JButton();
	private JButton BufferButton = new JButton();
	private JButton OptionButton = new JButton();
	private JButton BackButton = new JButton();
	private JButton NextButton = new JButton();
	private JMenuItem Size2 = new JMenuItem();
	private JMenuItem Size4 = new JMenuItem();
	private JMenuItem Size6 = new JMenuItem();
	private JMenuItem Size8 = new JMenuItem();
	private JMenu ColorMenu = new JMenu ();
	private JMenu SizeMenu = new JMenu ();
	private JMenu FontMenu = new JMenu ();
	private JPanel statusPanel = new JPanel();
	private JLabel statusText = new JLabel();
	private static double sizeDrawObject = 2.0;
	public static CanvasPanel CanvPan;
	public SelectCoordGui g2 = null;
	public MyCanvas c;
	public static SecondGUI g1 = null;
	public Graphics g;
	private static int imdH;
	private static int imdW;
	private TrayIcon icon = null;
	private static ImageIcon imgico = null;
	private Color color;
	private boolean isCtrl = false, isTray = false;
	private static boolean isPrtScr;
	private JMenuBar BP = new JMenuBar();
	private JColorChooser colorChooser = new JColorChooser();
	private Preferences userPrefsPrtScr;
	public static Preferences userPrefsColor;
	private Preferences userPrefsLineSize;
	private Preferences userPrefsFontSize;
	public static Preferences userPrefsLink;
	private JSeparator[] separator = {null, null, null};
	private boolean isTreyScreen;
	private static MenuItem linkItem;
	public static JTextArea textField = new JTextArea();
	public static int FontSize = 12;
	public static List<String> link = new ArrayList<String>();
	public static Menu LinkItemMenu = new Menu("Загрузить с сервера");
	private static LinkEventListener linkEvLists[];
	public SecondGUI(Image img) throws IOException, URISyntaxException {
		super("ScreenSaver");
		this.setTitle("LeadVertex Скриншот");
		userPrefsPrtScr = Preferences.userRoot().node("config").node("enabledPrtScr");
		isPrtScr = userPrefsPrtScr.getBoolean("value", isPrtScr);
		userPrefsColor = Preferences.userRoot().node("config").node("Color");
		int colorR = 0, colorG = 0, colorB = 0;
		colorR = userPrefsColor.getInt("r", colorR);
		colorG = userPrefsColor.getInt("g", colorG);
		colorB = userPrefsColor.getInt("b", colorB);
		try {
		    color = new Color(colorR,colorG,colorB);
		} catch (Exception e) {
		    color = Color.black; // Not defined
		}
		textField.setForeground(color);		
		userPrefsLineSize = Preferences.userRoot().node("config").node("LineSize");
		sizeDrawObject = userPrefsLineSize.getDouble("value", sizeDrawObject);
		userPrefsFontSize = Preferences.userRoot().node("config").node("FontSize");
		FontSize = userPrefsFontSize.getInt("value", FontSize);
		userPrefsLink = Preferences.userRoot().node("config").node("Link");
		
		byte[] bytes = null;
		bytes = userPrefsLink.getByteArray("value", bytes);
		ByteToList btl = new ByteToList(bytes);
		if(bytes != null) btl.getList();
		
		boolean b = true;  
		if(b) {
			try {
				GlobalScreen.registerNativeHook();
			}
			catch (NativeHookException ex) {
				System.err.println("There was a problem registering the native hook.");
				System.err.println(ex.getMessage());
				System.exit(1);
			}
		}
		g1 = this;
		GlobalScreen.addNativeKeyListener(this);
		InputStream in = SecondGUI.class.getClassLoader().getResourceAsStream("screenshot.png");
        Image ico = ImageIO.read(in);
        setIconImage(ico);
		c = new MyCanvas(1.0, img);
		CanvPan = new CanvasPanel(true, c);
		this.remove(CanvPan);
		this.add(CanvPan, BorderLayout.CENTER);
		if(g2 == null) g2 = new SelectCoordGui(img, this);
		this.setBounds(Screen.d.width/2 - 420, Screen.d.height/2 - 300, 900, 600);
		this.setMinimumSize(new Dimension(900, 300));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			
            @Override
            public void windowClosing(WindowEvent e) {
            	collapse();
            }

        });
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		for(int i = 0; i < 3; i++) {
			separator[i] = new JSeparator(JSeparator.VERTICAL);
			separator[i].setOpaque(true);
			separator[i].setMaximumSize(new Dimension(2, Button1.getHeight()));
		}
		LineButton.setToolTipText("Линия");
		ArrowButton.setToolTipText("Стрелка");
        ScreenButton.setToolTipText("Сделать скриншот");
        BrushButton.setToolTipText("Кисть");
        Button1.setToolTipText("Круг нумерованный");
        TextButton.setToolTipText("Текст");
        BlurButton.setToolTipText("Пикселизация");
        SaveButton.setToolTipText("Сохранить");
        RectangleButton.setToolTipText("Прямоугольник");
        NumberRectButton.setToolTipText("Прямоугольник №" + DrawObjectNumberRect.i);
        SaveServButton.setToolTipText("Сохранить на сервер");
        ColorMenu.setToolTipText("Выбрать цвет");
        BufferButton.setToolTipText("Скопировать в буфер");
        SizeMenu.setToolTipText("Толщина линии");
        OptionButton.setToolTipText("Настройки");
        BackButton.setToolTipText("Отменить(Ctrl + Z)");
        NextButton.setToolTipText("Вернуть(Ctrl + Y)");
		LineButton.setIcon(setIcon("line.png"));
		ArrowButton.setIcon(setIcon("arrow.png"));
        ScreenButton.setIcon(setIcon("screen.png"));
        BrushButton.setIcon(setIcon("brush.png"));
        BackButton.setIcon(setIcon("back.png"));
        NextButton.setIcon(setIcon("next.png"));
        Button1.setIcon(setIcon(DrawObjectOne.getI()));
        TextButton.setIcon(setIcon("T.png"));
        BlurButton.setIcon(setIcon("blur.png"));
        SaveButton.setIcon(setIcon("save.png"));
        SaveServButton.setIcon(setIcon("server.png"));
        ColorMenu.setIcon(setIcon("color.png"));
        RectangleButton.setIcon(setIcon("rect.png"));
        BufferButton.setIcon(setIcon("buffer.png"));
        OptionButton.setIcon(setIcon("option.png"));
        SizeMenu.setIcon(setIcon("line2.png", 30, 23));
        Size2.setIcon(setIcon("line2.png", 120, 23));
        Size4.setIcon(setIcon("line4.png", 120, 25));
        Size6.setIcon(setIcon("line6.png", 120, 27));
        Size8.setIcon(setIcon("line8.png", 120, 29));
        ColorMenu.setOpaque(true);
        ColorMenu.setBackground(new Color(200, 218, 235));
        ColorMenu.setMinimumSize(Button1.getMinimumSize());
        ColorMenu.setMaximumSize(new Dimension(38, 30));
        ColorMenu.setBorder(Button1.getBorder());
        SizeMenu.setBorder(Button1.getBorder());
        SizeMenu.setMinimumSize(Button1.getMinimumSize());
        SizeMenu.setMaximumSize(new Dimension(48, 30));
        SizeMenu.setOpaque(true);
        SizeMenu.setBackground(new Color(200, 218, 235));
        SizeMenu.setIcon(setIcon("lw.png"));
        FontMenu.setBorder(Button1.getBorder());
        FontMenu.setMinimumSize(Button1.getMinimumSize());
        FontMenu.setMaximumSize(new Dimension(48, 30));
        FontMenu.setOpaque(true);
        FontMenu.setBackground(new Color(200, 218, 235));
        FontMenu.setToolTipText("Размер шрифта");
        FontMenu.setIcon(setIcon("ts.png"));
        NumberRectButton.setIcon(setIcon(DrawObjectNumberRect.getI()));
		ScreenButton.addActionListener(new ScreenButtonEventListener());
		ArrowButton.addActionListener(new ArrowButtonEventListener());
		Button1.addActionListener(new Button1EventListener());
		BackButton.addActionListener(new BackButtonEventListener());
		NextButton.addActionListener(new NextButtonEventListener());
		LineButton.addActionListener(new LineButtonEventListener());
		TextButton.addActionListener(new TextButtonEventListener());
		BlurButton.addActionListener(new BlurButtonEventListener());
		SaveButton.addActionListener(new SaveButtonEventListener());
		BrushButton.addActionListener(new BrushButtonEventListener());
		SaveServButton.addActionListener(new SaveServButtonEventListener());
		BufferButton.addActionListener(new BufferButtonEventListener());
		RectangleButton.addActionListener(new RectangleButtonEventListener());
		NumberRectButton.addActionListener(new NumberRectButtonEventListener());
		OptionButton.addActionListener(new OptionButtonEventListener());
		statusText.addMouseListener(new MouseStatusListener());
		Size2.addActionListener(new SizeEventListener(2));
		Size4.addActionListener(new SizeEventListener(4));
		Size6.addActionListener(new SizeEventListener(6));
		Size8.addActionListener(new SizeEventListener(8));
		createColorChooser();
		ColorMenu.add(colorChooser);
		SizeMenu.add(Size2);
		SizeMenu.add(Size4);
		SizeMenu.add(Size6);
		SizeMenu.add(Size8);
		JMenuItem j = null;
		for(int i = 1; i < 10 ;i ++) {
			j = new JMenuItem(""+(8 + 2*i));
			j.addActionListener(new FontEventListener(8+2*i));
			FontMenu.add(j);
		}
		j = new JMenuItem(""+36);
		j.addActionListener(new FontEventListener(36));
		FontMenu.add(j);
		j = new JMenuItem(""+48);
		j.addActionListener(new FontEventListener(48));
		FontMenu.add(j);
		j = new JMenuItem(""+72);
		j.addActionListener(new FontEventListener(72));
		FontMenu.add(j);
		BP.add(ScreenButton);
		BP.add(separator[0]);
		BP.add(BrushButton);
		BP.add(LineButton);
		BP.add(ArrowButton);
		BP.add(RectangleButton);
		BP.add(NumberRectButton);
		BP.add(TextButton);
		BP.add(BlurButton);
		BP.add(BackButton);
		BP.add(NextButton);
		BP.add(separator[1]);
		BP.add(BufferButton);
		BP.add(SaveButton);
		BP.add(SaveServButton);
		BP.add(separator[2]);
		BP.add(ColorMenu);
		BP.add(SizeMenu);
		BP.add(FontMenu);
		BP.add(OptionButton);
	    setJMenuBar(BP);
	    textField.addKeyListener(this);
	    textField.setVisible(false);
	    this.add(textField);
	    statusPanel.setLayout(new BorderLayout());
	    statusPanel.add(new JLabel(""), BorderLayout.CENTER);
	    statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
	    setStatusPanelText(" ");
	    this.add(statusPanel,BorderLayout.SOUTH);
	    ActionListener exitListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            System.exit(0);
	        }
	    };
	    
	    ActionListener expandListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	g1.expand();
	        }
	    };   
	    
	    ActionListener aboutListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	try {
					Desktop.getDesktop().browse(new URI("https://github.com/leadvertex/screenshot-app"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	    };   
	    
	    ActionListener openListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	Image openImg = null;
	        	File f = null;
	        	JFileChooser fileopen = new JFileChooser();
	        	int ret = fileopen.showDialog(null, "Открыть файл");                
	        	if (ret == JFileChooser.APPROVE_OPTION) f = fileopen.getSelectedFile();
				try {
					openImg  = ImageIO.read(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					doUnvisible();
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				setNewImage(openImg);
				g1.expand();
	        }
	    };   
	    
	    if(SystemTray.isSupported()) {
	    	PopupMenu popup = new PopupMenu();
	 	    MenuItem exitItem = new MenuItem("Выход");
	 	    exitItem.addActionListener(exitListener);
	 	    MenuItem OpenItem = new MenuItem("Загрузить файл");
	 	    OpenItem.addActionListener(openListener);
	 	    MenuItem expandItem = new MenuItem("Развернуть");
	 	    expandItem.addActionListener(expandListener);
	 	    MenuItem aboutItem = new MenuItem("О программе");
	 	    aboutItem.addActionListener(aboutListener);
	 	    MenuItem ScreenItem = new MenuItem("Скрин");
	 	    ScreenItem.addActionListener(new ScreenButtonEventListener());
	 	    linkEvLists = new LinkEventListener[10];
	 	    for(int i = 0; i < SecondGUI.link.size() ;i ++) {
	 	    	linkEvLists[i] = new LinkEventListener(i);
			}
	 	    updateLinkOnTray();
	 	    popup.add(ScreenItem);
	 	    popup.add(OpenItem);
	 	    popup.add(LinkItemMenu);
	 	    popup.add(expandItem);
	 	    popup.add(aboutItem);
	 	    popup.add(exitItem);
            icon = new TrayIcon(ico, "hello", popup);
            icon.setImageAutoSize(true);
            icon.setToolTip("ScreenSaver");
            icon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	g1.expand();
                }

            });
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowIconified(WindowEvent e) {
                	collapse();
                }

            });
	    }
	}
	
	public static void updateLinkOnTray() {
		LinkItemMenu.removeAll();
		for(int i = 0; i < SecondGUI.link.size() ;i ++) {
			linkItem = new MenuItem((i+1) + ") " + SecondGUI.link.get(i).substring(28));
			linkItem.addActionListener(linkEvLists[i]);
			LinkItemMenu.add(linkItem);
		}
	}
	
	public void collapse() {
		isTray = true;
		SecondGUI.this.setVisible(false);
        try {
            getSystemTray().add(icon);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
	}
	
	public void setNewImage(Image img) {
		c = new MyCanvas(1.0, img);
		SecondGUI.imdH = img.getHeight(null);
		SecondGUI.imdW = img.getWidth(null);
		CanvPan = new CanvasPanel(true, c);
		this.add(CanvPan, BorderLayout.CENTER);
		MyCanvas.setColor(color);
	}
	
	public int[] getImgHW() {
		int[] arr = {SecondGUI.imdH, SecondGUI.imdW};
		return arr;
	}
	
	public void expand() {
		isTray = false;
		SecondGUI.this.setVisible(true);
    	SecondGUI.this.setExtendedState(SecondGUI.NORMAL);
        getSystemTray().remove(icon);
	}
	
	public void doScreen() {
		try {
			doUnvisible();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Screen.setXYWH(0, Screen.d.width, 0, Screen.d.height);
		int colorR = 0, colorG = 0, colorB = 0;
		colorR = userPrefsColor.getInt("r", colorR);
		colorG = userPrefsColor.getInt("g", colorG);
		colorB = userPrefsColor.getInt("b", colorB);
		try {
		    color = new Color(colorR,colorG,colorB);
		} catch (Exception e) {
		    color = Color.black; // Not defined
		}
		g2.setDefault(Screen.grabScreen(), this);
		g2.setVisible(true);
		System.gc();
		Runtime.getRuntime().gc();
	}
	
	public void doSave() {
		SaveToFile s = new SaveToFile();
		s.TrySave(c.getBufferedImage(c.getGraphics()));
	}
	
	public void doServerSave() {
		setStatusPanelText("Сохраняю на сервер...");//
		statusText.repaint();
		statusPanel.repaint();
		Preferences userPrefsIsAuthorized;
		userPrefsIsAuthorized = Preferences.userRoot().node("config").node("isAuthorized");
		boolean isAuth = false;
		isAuth = userPrefsIsAuthorized.getBoolean("value", isAuth);
		SaveServGui ssgui = new SaveServGui(c);
		ssgui.setVisible(false);
		if(isAuth) ssgui.trySave();
		else setStatusPanelText("Пользователь не авторизован. Для авторизации зайдите в настройки.");
			//showMessageDialog(null, "Пользователь не авторизован. Для авторизации зайдите в настройки.");
	}
	
	public void openOptions() {
		OptionsGUI opGUI = new OptionsGUI();
		opGUI.setVisible(true);
		g1.setEnabled(false);
	}

	private void doUnvisible() throws InterruptedException
	{
		if(!isTray) {
			g1.collapse();
			TimeUnit.MILLISECONDS.sleep(150);
		}
		else if(isTreyScreen) TimeUnit.MILLISECONDS.sleep(300); 
		this.setVisible(false);
		this.remove(CanvPan);
		
	}
	
	class ScreenButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			isTreyScreen = true;
			doScreen();
			isTreyScreen = false;
		}
	}
	
	class ArrowButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.changeDrawObjects(DrawObjects.DrawObjectArrow.toString());
		}
	}
	
	class Button1EventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.changeDrawObjects(DrawObjects.DrawObjectOne.toString());
		}
	}

	class NumberRectButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.changeDrawObjects(DrawObjects.DrawObjectNumberRect.toString());
		}
	}

	class TextButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.changeDrawObjects(DrawObjects.DrawObjectText.toString());
		}
	}
	
	class SizeEventListener implements ActionListener {
		
		private int i;
		SizeEventListener(int i){
			this.i = i;
		}
		
		public void actionPerformed(ActionEvent e) {
			sizeDrawObject = i;
			userPrefsLineSize.putDouble("value", sizeDrawObject);
		}
	}
	
	class FontEventListener implements ActionListener {
		private int i;
		FontEventListener(int i){
			this.i = i;
		}
		
		public void actionPerformed(ActionEvent e) {
			FontSize = i;
			userPrefsFontSize.putInt("value", FontSize);
			textField.setFont(new Font(null, 1, i));
		}
	}
	
	class LinkEventListener implements ActionListener {
		private int i;
		
		LinkEventListener(int i){
			this.i = i;
		}
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(link.get(i)),null);
			
		}
	}
	
	class BrushButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.changeDrawObjects(DrawObjects.DrawObjectBrush.toString());
		}
	}
	
	class BlurButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.changeDrawObjects(DrawObjects.DrawObjectBlur.toString());
		}
	}
	
	class LineButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.changeDrawObjects(DrawObjects.DrawObjectLine.toString());
		}
	}
	
	class RectangleButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.changeDrawObjects(DrawObjects.DrawObjectRect.toString());
		}
	}

	class SaveServButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			doServerSave();
		}
	}
	
	class SaveButtonEventListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			doSave();
		}
	}
	
	class BackButtonEventListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			c.setLastGraphics((Graphics2D) c.getGraphics());
		}
	}
	
	class NextButtonEventListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			c.setNextGraphics((Graphics2D) c.getGraphics());
		}
	}
	
	
	class OptionButtonEventListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			openOptions();
		}
	}
	
	class MouseStatusListener implements MouseListener {
		
	
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if(statusText.getText() == "Пользователь не авторизован. Для авторизации зайдите в настройки.") openOptions();
			else Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(statusText.getText()),null);
		}
	}
	
	
	class BufferButtonEventListener implements ActionListener {
		
		public class ImageTransferable implements Transferable
	    {
	        private Image image;

	        public ImageTransferable (Image image)
	        {
	            this.image = image;
	        }

			@Override
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			@Override
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				 return flavor == DataFlavor.imageFlavor;
			}

			@Override
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
	            {
	                return image;
	            }
	            else
	            {
	                throw new UnsupportedFlavorException(flavor);
	            }
			}
	    }
		
		public void actionPerformed(ActionEvent e) {
			
			ImageTransferable transferable = new ImageTransferable( c.getImg());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable,null);
		}
	}
	
	
	protected void createColorChooser() {
		
		AbstractColorChooserPanel[] oldPanels = colorChooser.getChooserPanels();
	    for (int i = 0; i < oldPanels.length; i++) {
	      String clsName = oldPanels[i].getClass().getName();
	      if (clsName.equals("javax.swing.colorchooser.ColorChooserPanel") || clsName.equals("javax.swing.colorchooser.DefaultSwatchChooserPanel")) {
	    	  colorChooser.removeChooserPanel(oldPanels[i]);
	      }
	    }
	    colorChooser.addChooserPanel(new SystemColorChooserPanel());
        colorChooser.setBounds(ColorMenu.getX(), ColorMenu.getY()-100, 100, 50);
        colorChooser.setPreviewPanel(new JPanel());
        colorChooser.setVisible(true);
	}
	
	class SizeChangeEventListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			sizeDrawObject = ((JSlider) e.getSource()).getValue();
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getKeyCode() == NativeKeyEvent.VC_CONTROL) isCtrl = true;
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if(e.getKeyCode() == NativeKeyEvent.VC_CONTROL) isCtrl = false;
		if(g1.isFocused()) {
			if(e.getKeyCode() == NativeKeyEvent.VC_Z && isCtrl) c.setLastGraphics((Graphics2D) c.getGraphics());	
			if(e.getKeyCode() == NativeKeyEvent.VC_Y && isCtrl) c.setNextGraphics((Graphics2D) c.getGraphics());
			if(e.getKeyCode() == NativeKeyEvent.VC_ENTER && isCtrl && !textField.isVisible()) doServerSave();
			if(e.getKeyCode() == NativeKeyEvent.VC_S && isCtrl && g1.isFocused()) doSave();
			if((c.dOs.name() == "DrawObjectOne" || c.dOs.name() == "DrawObjectNumberRect") && isCtrl) {
				int i = e.getKeyCode() - 1;
				if(i>0 && i<10) SecondGUI.changeNumber(c.dOs.name(), i);
			}
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN && isPrtScr) doScreen();
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}

	public Color getColor() {
		if(color == null) color = Color.black;
		return color;
	}
	
	public static ImageIcon setIcon(String str) throws URISyntaxException, IOException {
		Image timg;
		Image newimg;
		if(str != "") {
			InputStream in = SecondGUI.class.getClassLoader().getResourceAsStream(str);
			Image ico = ImageIO.read(in);
			imgico = new ImageIcon(ico);
	        timg = imgico.getImage() ;
	        newimg = timg.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
	        imgico = new ImageIcon(newimg);
		}
        return imgico;
	}
	
	public ImageIcon setIcon(String str, int w, int h) throws URISyntaxException, IOException {
		Image timg;
		Image newimg;
		if(str != "") {
			InputStream in = SecondGUI.class.getClassLoader().getResourceAsStream(str);
			Image ico = ImageIO.read(in);
			imgico = new ImageIcon(ico);
	        timg = imgico.getImage() ;
	        newimg = timg.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH ) ;
	        imgico = new ImageIcon(newimg);
		}
        return imgico;
	}
	
	public static double getSizeDrawObject(){
		return sizeDrawObject;
	}
	
	public static void setEnabled() {
		g1.setEnabled(true);
	}
	
	public static void setPrtScr(boolean b) {
		isPrtScr = b;
	}
	
	public static boolean getPrtScr() {
		return isPrtScr;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER && isCtrl) {
			MyCanvas.isTextEnter = true;
			textField.setVisible(false);
			c.mReleased(c.e);
		}
	}
	
	public static void changeNumber(String s, int i) {
		JButton b = null;
		if(s.equals("DrawObjectNumberRect")) {
			b = NumberRectButton;
			if(i > 0) DrawObjectNumberRect.changeI(i);
			else if(i == 0) DrawObjectNumberRect.changeI();
			else DrawObjectNumberRect.changeLastI();
			try {
				b.setIcon(setIcon(DrawObjectNumberRect.getI()));
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(s.equals("DrawObjectOne")) {
			b = Button1;
			if(i > 0) DrawObjectOne.changeI(i);
			else if(i == 0) DrawObjectOne.changeI();
			else DrawObjectOne.changeLastI();
			try {
				b.setIcon(setIcon(DrawObjectOne.getI()));
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void setStatusPanelText(String text) {
		statusText.setText(text);
		statusPanel.removeAll();
		statusPanel.add(statusText);
		g1.paintAll(getGraphics());
	}
	
	public String getStatusPanelText() {
		return statusText.getText();
	}

	
}
