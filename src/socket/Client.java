package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Socket socket = new Socket("127.0.0.1",8000);
		System.out.println("服务器连接成功！");
		
		InputStream in = socket.getInputStream();   // 字节输入流
		OutputStream out = socket.getOutputStream();
		Scanner scan = new Scanner(System.in);
		
		boolean running = true;	
		// 客户端读取服务器发送的信息的线程
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
						System.out.println("服务器说："+msg);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			};
			}
		};
		t1.start();
		
		// 客户端发送信息给服务器的线程
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
	}
}
// 精灵线程+wait()
