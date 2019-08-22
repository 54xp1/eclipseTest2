package http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class http1 {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8080);
		System.out.println("·þÎñÆ÷Æô¶¯");
		
		boolean running = true;
		while(running) {
			Socket socket = server.accept();
			byte[] buffer = new byte[1024];
			int count = socket.getInputStream().read(buffer);
			
				String content = new String(buffer,0,count);
				System.out.println(content);
				String [] lines = content.split("\r\n");
				String [] items = content.split("\\s");
				String uri = items[1];
				String contentType = getContentType(uri);
			
			
			
			
			
			OutputStream out = socket.getOutputStream();
			out.write("HTTP/1.1 200 OK\n".getBytes());
			out.write("Content-Type: text/html\n".getBytes());
			
			out.write("\n".getBytes());
		//	out.write("<html><body><h1>hello world!</h1></body></html>".getBytes());
			
			FileInputStream fis = new FileInputStream(""+uri);
			while((count=fis.read(buffer))>0) {
				out.write(buffer, 0, count);
			}
			fis.close();
			out.close();
			socket.close();
		}
		
		server.close();
	}
	
public static String getContentType(String uri) {
	if(uri.endsWith(".js")) {
		return "";
	}else if(uri.endsWith(".css")) {
		return "text/css";
	}else {
		return "text/html";
	}
}	
	
}
