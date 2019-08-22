package http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8080);
		System.out.println("服务器启动8080端口:");
		
		/*boolean running = true;
		while(running) {*/
			Socket socket = server.accept();
			System.out.println("接受到客户端请求：");
			byte[] buffer = new byte[1024];
			int count = socket.getInputStream().read(buffer);
			
			if(count>0) {
				String content = new String(buffer, 0, count);
				System.out.println(content);
			}
			

			/*OutputStream out = socket.getOutputStream();
			out.write("HTTP/1.1 200 OK\n".getBytes());
			out.write("Content-Type: text/html\n".getBytes());
			out.write("Content-Type: image/svg+xml\n".getBytes());
			
			out.write("\n".getBytes());
			out.write("<html><body><h1>hello world!</h1></body></html>".getBytes());*/
			socket.close();
		//}
		
		server.close();
	}
}
