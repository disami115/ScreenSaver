package Saves;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import Converter.ListToByte;
import GUIs.OptionsGUI;
import GUIs.SecondGUI;

public class MyHttpPost {
	
	private static String url = "";
	public static Preferences userPrefsIsAuthorized;
	private static Preferences userPrefsServer;
	private static String str = "";
	public static boolean isAuthorized;
	public static List<String> list = null;
	
	public String post(BufferedImage bufferedImage, String l, String p) throws ClientProtocolException, IOException
	{
		ArrayList<String> s = new ArrayList<>();
		if(setUrl()) {
			HttpPost httppost = new HttpPost(url);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addTextBody("username", l, ContentType.TEXT_PLAIN);
			builder.addTextBody("password", p, ContentType.TEXT_PLAIN);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", os);                          
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			builder.addBinaryBody("image", is ,ContentType.APPLICATION_OCTET_STREAM, "temp.png");
		    HttpEntity multipart = builder.build();
			httppost.setEntity(multipart); 
		    try (CloseableHttpResponse response = httpclient.execute(httppost)){
			    HttpEntity responseEntity = response.getEntity();    
			    try (Scanner sc = new Scanner(responseEntity.getContent())) {
					while(sc.hasNext()) {
					   s.add(sc.nextLine());
					}
					
					if(s.size() > 1) {
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s.get(1)),null);
						SecondGUI.g1.setStatusPanelText(s.get(1));
						if(SecondGUI.link.size() > 9) {
							List<String> templist = new ArrayList<String>();
							templist = SecondGUI.link.subList(1, 10);
							SecondGUI.link = templist;
						}
						SecondGUI.link.add(s.get(1));
						ListToByte ltb = new ListToByte(SecondGUI.link);
						byte[] bytes = ltb.getByte();
						SecondGUI.userPrefsLink.putByteArray("value", bytes);
						SecondGUI.updateLinkOnTray();
						}
					else str = s.get(0);
				}
		    }
	    }
	    return str;
	}
	
	public static void setUrl(String url) {
		MyHttpPost.url = url;
	}
	
	public static String getUrl() {
		return MyHttpPost.url;
	}
	
	private static boolean setUrl() throws IOException {
		userPrefsServer = Preferences.userRoot().node("config").node("server");
		url =  userPrefsServer.get("value", url);
		return (url != null);
	}

	public static boolean getAuthorized(String l, String p) {
		isAuthorized = postAuthorized(l, p);
		userPrefsIsAuthorized = Preferences.userRoot().node("config").node("isAuthorized");
		userPrefsIsAuthorized.put("login", l);
		userPrefsIsAuthorized.put("password", p);
		userPrefsIsAuthorized.putBoolean("value", isAuthorized);
		return isAuthorized;
	}

	private static boolean postAuthorized(String l, String p) {
		ArrayList<String> s = new ArrayList<>();
		try {
			if(setUrl()) {
				HttpPost httppost = new HttpPost(url);
				CloseableHttpClient httpclient = HttpClients.createDefault();
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.addTextBody("username", l, ContentType.TEXT_PLAIN);
				builder.addTextBody("password", p, ContentType.TEXT_PLAIN);
				HttpEntity multipart = builder.build();
				httppost.setEntity(multipart); 
			    try (CloseableHttpResponse response = httpclient.execute(httppost)){
				    HttpEntity responseEntity = response.getEntity();    
				    try (Scanner sc = new Scanner(responseEntity.getContent())) {
						while(sc.hasNext()) {
							s.add(sc.nextLine());
						}
					}
			    }
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean b = false;
		if(s.size() > 0) b = s.get(0).equals("202");
		if(b) OptionsGUI.setUserNameText(l);
		return b;
	}

}
	