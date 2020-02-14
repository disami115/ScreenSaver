package Converter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class ListToByte {

	private List<String> list;
	
	public ListToByte(List<String> list){
		this.list = list;
	}
	
	public byte[] getByte() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : list) {
		    try {
				out.writeUTF(element);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
}
