package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException, InterruptedException {
		// 创建Socket服务器      端口号8000 可以随便取 1024 以上的端口号
		ServerSocket server = new ServerSocket(8000);
		// 等待客户端连接  线程会在此阻塞
		System.out.println("服务器启动：8000");
		Socket socket = server.accept();
		System.out.println("客户端连接成功！！！");
		InputStream in = socket.getInputStream();   // 字节输入流
		OutputStream out = socket.getOutputStream();
		Scanner scan = new Scanner(System.in);
		
	boolean running = true;	
		// 服务器读取客户端发送过来的信息 的线程
		Thread t1 = new Thread() {
			public void run() {
			while(running) {
				// 存放读入的数据    1024个字节
				byte[] buffer = new byte[1024];
				// 存放读入的字节数
				int count;
				// read 是读取客户端发送过来的信息  线程在此阻塞
				try {
					count = in.read(buffer);
					if(count>0) {
						String msg = new String(buffer,0,count);
						System.out.println("客户端说："+msg);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			};
			}
		};
		t1.start();
		
		// 服务器发信息给客户端的线程
		Thread t2 = new Thread() {
			public void run() {
				while(running) {
					String msg1 = scan.nextLine();
					try {
						out.write(msg1.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
		};
		t2.start();
		
		t1.join();
		t2.join();
		scan.close();
		socket.close();
		server.close();
		
	}
}
