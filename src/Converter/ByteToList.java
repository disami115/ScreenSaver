package Converter;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import GUIs.SecondGUI;

public class ByteToList {

	private byte[] bytes;
	
	public ByteToList(byte[] bytes) {
		this.bytes = bytes;
		
	}
	
	public void getList(){
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream in = new DataInputStream(bais);
		try {
			while (in.available() > 0) {
			    String element = in.readUTF();
			    SecondGUI.link.add(element);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
