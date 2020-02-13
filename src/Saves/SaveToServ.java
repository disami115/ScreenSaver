package Saves;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import GUIs.SaveServGui;

public class SaveToServ{

	public String TrySave(BufferedImage bufferedImage, SaveServGui ssgui) throws ClientProtocolException, IOException {
		MyHttpPost hp = new MyHttpPost();
		ssgui.setVisible(false);
		return hp.post(bufferedImage, ssgui.getLogin(), ssgui.getPassword());
	}
	
}
