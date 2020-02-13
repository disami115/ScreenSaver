package GUIs;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import MouseDraw.DrawObject;
import Saves.MyHttpPost;
import Screens.Screen;

public class OptionsGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static OptionsGUI opGUI = null;
	private JPanel panelServer;
	private JTextField serverTextField = new JTextField();
	private JButton serverButton = new JButton("Изменить");
	private JPanel panelLogPass;
    private JTextField LoginField = new JTextField();
	private JPasswordField  PasswordField = new JPasswordField();
	private JLabel logLabel = new JLabel();
	private JLabel passLabel = new JLabel();
	private static JLabel userName = new JLabel();
	private JButton logPassButton = new JButton("Вход");
	private JButton exitButton = new JButton("Выход");
	private JPanel panelRadio;
	private JPanel panelShadow;
	private JRadioButton shadowOff = new JRadioButton("Выключить");
	private JRadioButton shadowOne = new JRadioButton("1 пкс.");
	private JRadioButton shadowTwo = new JRadioButton("2 пкс.");
	private JRadioButton yesRB = new JRadioButton("Да");
	private JRadioButton noRB = new JRadioButton("Нет");
	public static boolean isAuthorized, enabledPrtScr;
	private static int ShadowSize;
	private static String l = "", p = "", url = "";
	private Preferences userPrefsServer;
	private Preferences userPrefsPrtScr;
	private Preferences userPrefsIsAuthorized;
	private Preferences userPrefsShadowSize;
	
	public OptionsGUI() {
		super("Настройки");
		createGUI();
		createAllPanel();
	}
	
	private void changeVisible() {
		logLabel.setVisible(!isAuthorized);
		passLabel.setVisible(!isAuthorized);
		LoginField.setVisible(!isAuthorized);
		PasswordField.setVisible(!isAuthorized);
		logPassButton.setVisible(!isAuthorized);
		exitButton.setVisible(isAuthorized);
		userName.setVisible(isAuthorized);
	}
	
	private void createPanelServer() {
		panelServer = new JPanel(null);
        panelServer.setBorder(BorderFactory.createTitledBorder("Сервер"));
        serverTextField.setBounds(62, 30, 280, 20);
        url = userPrefsServer.get("value", url);
        serverTextField.setText(url);
        serverButton.setBounds(152, 70, 100, 20);
        serverButton.addActionListener(new serverButtonListener());
        panelServer.add(serverTextField);
        panelServer.add(serverButton);
	}
	
	private void createPanelLogPass() {
        panelLogPass = new JPanel(null);
        panelLogPass.setBorder(BorderFactory.createTitledBorder("Авторизация"));
        logPassButton.addActionListener(new logPassButtonEventListener());
        exitButton.addActionListener(new exitButtonEventListener());
        logLabel.setText("Логин");
        logLabel.setBounds(102, 20, 100, 20);
        passLabel.setText("Пароль");
        passLabel.setBounds(212, 20, 100, 20);
		LoginField.setBounds(102, 40, 100, 20);
		userName.setBounds(162, 30, 100, 20);
		isAuthorized = userPrefsIsAuthorized.getBoolean("value", isAuthorized);
		if(isAuthorized) {
			l = userPrefsIsAuthorized.get("login", l);
			p = userPrefsIsAuthorized.get("password", p);
			MyHttpPost.getAuthorized(l, p);
		}
		LoginField.setText(l);
		PasswordField.setBounds(212, 40, 100, 20);
		PasswordField.setEchoChar('*');
		logPassButton.setBounds(152, 70, 100, 20);
		exitButton.setBounds(152, 70, 100, 20);
		panelLogPass.add(logLabel);
		panelLogPass.add(passLabel);
		panelLogPass.add(LoginField);
		panelLogPass.add(PasswordField);
		panelLogPass.add(logPassButton);
		panelLogPass.add(exitButton);
		panelLogPass.add(userName);
		changeVisible();
	}
	
	private void createPanelRadio() {
		panelRadio = new JPanel(new GridLayout(0, 1, 0, 5));
        panelRadio.setBorder(BorderFactory.createTitledBorder("Реагировать на нажатие Prt Scr"));
        ButtonGroup bg1 = new ButtonGroup();
        panelRadio.add(yesRB);
        bg1.add(yesRB);
        panelRadio.add(noRB);
        bg1.add(noRB);
        yesRB.addActionListener(new yesRBListener()); 
        noRB.addActionListener(new noRBListener());
        yesRB.setSelected(enabledPrtScr);
        noRB.setSelected(!enabledPrtScr);
	}
	
	private void createPanelShadow() {
		panelShadow = new JPanel(new GridLayout(0, 1, 0, 5));
		panelShadow.setBorder(BorderFactory.createTitledBorder("Настройки обводки"));
        ButtonGroup bg1 = new ButtonGroup();
        panelShadow.add(shadowOff);
        bg1.add(shadowOff);
        panelShadow.add(shadowOne);
        bg1.add(shadowOne);
        panelShadow.add(shadowTwo);
        bg1.add(shadowTwo);
        shadowOff.addActionListener(new shadowOffListener()); 
        shadowOne.addActionListener(new shadowOneListener());
        shadowTwo.addActionListener(new shadowTwoListener());
        ShadowSize = userPrefsShadowSize.getInt("value", ShadowSize);
        shadowOff.setSelected(ShadowSize == 0);
        shadowOne.setSelected(ShadowSize == 1);
        shadowTwo.setSelected(ShadowSize == 2);
	}
	
	private void createAllPanel() {
		createPanelServer();
		createPanelLogPass();
		createPanelRadio();
		createPanelShadow();
		setLayout(new GridLayout(0, 1, 0, 5));
		JPanel panel1 = new JPanel(new GridLayout(0, 1, 0, 5));
		panel1.add(panelServer);
		panel1.add(panelLogPass);
		JPanel panel2 = new JPanel(new GridLayout(0, 2, 0, 5));
		panel2.add(panelRadio);
		panel2.add(panelShadow);
		add(panel1);
		add(panel2);
	}
	
	public static void setUserNameText(String s) {
		userName.setText("Привет " + s);
	}
	
	private void createGUI() {
		opGUI = this;
		url = MyHttpPost.getUrl();
		userPrefsServer = Preferences.userRoot().node("config").node("server");
		userPrefsPrtScr = Preferences.userRoot().node("config").node("enabledPrtScr");
		userPrefsIsAuthorized = Preferences.userRoot().node("config").node("isAuthorized");
		userPrefsShadowSize = Preferences.userRoot().node("config").node("ShadowSize");
		enabledPrtScr = userPrefsPrtScr.getBoolean("value", enabledPrtScr);
		setBounds(Screen.d.width/2 - 210, Screen.d.height/2 - 250, 420, 500);
		InputStream in = SecondGUI.class.getClassLoader().getResourceAsStream("screenshot.png");
        Image ico = null;
		try {
			ico = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setIconImage(ico);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {

				SecondGUI.setEnabled();
				opGUI.setVisible(false);
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
	}
	
	
	class yesRBListener implements ActionListener {
        
    	public void actionPerformed(ActionEvent e) {
    		enabledPrtScr = true;
    		changeEnabledPrtScr();
    		
        }
    }
	
	class noRBListener implements ActionListener {
        
    	public void actionPerformed(ActionEvent e) {
    		enabledPrtScr = false;
    		changeEnabledPrtScr();
        }
    }
	
	class shadowOffListener implements ActionListener {
        
    	public void actionPerformed(ActionEvent e) {
    		ShadowSize = 0;
    		changeShadowSize();
        }
    }
	
	class shadowOneListener implements ActionListener {
        
    	public void actionPerformed(ActionEvent e) {
    		ShadowSize = 1;
    		changeShadowSize();
        }
    }

	class shadowTwoListener implements ActionListener {
    
		public void actionPerformed(ActionEvent e) {
			ShadowSize = 2;
    		changeShadowSize();
	    }
	}
	
	private void changeEnabledPrtScr() {
		userPrefsPrtScr.putBoolean("value", enabledPrtScr);
		SecondGUI.setPrtScr(enabledPrtScr);
	}
	
	private void changeShadowSize() {
		userPrefsShadowSize.putInt("value", ShadowSize);
		DrawObject.setShadowSize(ShadowSize);
	}
	
	public static int getShadowSize() {
		return ShadowSize;
	}


	class serverButtonListener implements ActionListener {
        
    	public void actionPerformed(ActionEvent e) {
    		String s = serverTextField.getText().trim();
    		if(s.startsWith("http://") || s.startsWith("https://")) {
    			userPrefsServer.put("value", s);
    			MyHttpPost.setUrl(s);
    		}
    	}
    }
	
	class logPassButtonEventListener implements ActionListener {
        
		public void actionPerformed(ActionEvent e) {
    		l = LoginField.getText().trim();
    		p = String.valueOf(PasswordField.getPassword());
    		isAuthorized = MyHttpPost.getAuthorized(l, p);
    		changeVisible();
    	}
    }

	class exitButtonEventListener implements ActionListener {
    
	public void actionPerformed(ActionEvent e) {
		isAuthorized = false;
		userPrefsIsAuthorized.put("password", "");
		userPrefsIsAuthorized.putBoolean("value", false);
		changeVisible();
	}
}
	
	
}
