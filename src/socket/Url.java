package socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Url {
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://www.hyycinfo.com");
		System.out.println(url.getProtocol());
		System.out.println(url.getHost());
		System.out.println(url.getPort());
		System.out.println(url.getPath());
		System.out.println(url.getQuery());
		
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		// ���ֽ���תΪ�ַ��� �ɽ�����һ���ַ�����������
		byte []buffer = new byte[1024];
		int count;
		while( (count=in.read(buffer))>0 ) {
			String content = new String(buffer,0,count,"utf-8");
			System.out.println(content);
		}
	}
}
