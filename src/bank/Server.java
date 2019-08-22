package bank;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException {
		// 启动Socket服务器
		ServerSocket serversocket = new ServerSocket(2516);
		// 循环接受请求，获取Socket对象
		Socket socket = serversocket.accept();
		// 接受请求:输出请求的消息
		InputStream in = socket.getInputStream();
		DataInputStream dis = new DataInputStream(in);  // 处理流  通过构造方法处理
		Scanner scan = new Scanner(System.in);
		boolean running = true;
		while (running) {

			String commond = dis.readLine();
			System.out.println("命令" + commond);
			String account = dis.readLine();
			System.out.println("账户" + account);
			double money;

			switch (commond) {
			case "balance":
				break;
			case "diposit":
				money = dis.readDouble();
				System.out.println("金额" + money);
				break;
			case "withdraw":
				money = dis.readDouble();
				System.out.println("金额" + money);
				break;

			}
		}
	}
}
