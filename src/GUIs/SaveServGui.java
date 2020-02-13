package GUIs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;

import Canvas.MyCanvas;
import Saves.SaveToServ;
import Screens.Screen;

import static javax.swing.JOptionPane.showMessageDialog;

public class SaveServGui extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton SaveButton = new JButton("Сохранить");
	private MyCanvas c;
	private static String login, password;
	private SaveServGui ssgui = this;
	private static Preferences userPrefsIsAuthorized;
	
	
	public SaveServGui(MyCanvas c) {
		super("На сервер");
		userPrefsIsAuthorized = Preferences.userRoot().node("config").node("isAuthorized");
		this.c = c;
		this.setBounds((Screen.d.width-255)/2, (Screen.d.height-110)/2, 255, 110);
		this.setLayout(null);
		//this.setVisible(true);
		SaveButton.addActionListener(new SaveButtonEventListener());
		SaveButton.setBounds(70, 20, 100, 20);
		this.add(SaveButton);
		
	}
	
	public String getLogin() {
		login = userPrefsIsAuthorized.get("login", login);
		return login;
	}

	public static void setLogin(String login) {
		SaveServGui.login = login;
	}

	public String getPassword() {
		password = userPrefsIsAuthorized.get("password", password);
		return password;
	}

	public static void setPassword(String password) {
		SaveServGui.password = password;
	}
	
	public void trySave() {
		SaveToServ s = new SaveToServ();
		String l = getLogin();
		String p = getPassword();
		if(!l.isEmpty() && !p.isEmpty())
		{
			try {
				showMessageDialog(null, s.TrySave(c.getBufferedImage(c.getGraphics()), ssgui));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
			}
		}
		else {
			showMessageDialog(null, "Не корректный");
		}
	}

class SaveButtonEventListener implements ActionListener {
		
		
		public void actionPerformed(ActionEvent e) {
			
			
		}
	}
	
}
