package bank;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1",2516);
		OutputStream out = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(out);  // 处理流
		Scanner scan = new Scanner(System.in);
		boolean running = true;
		while(running) {
			String commond = scan.nextLine();
			switch(commond) {
			case "1":
				commond = "balance";
				break;
			case "2":
				commond = "diposit";
				break;
			case "3":
				commond = "withdraw";
				break;
			}
			dos.writeBytes(commond+"\n");
			System.out.println("请输入您的账号:");
			String account = scan.nextLine();
			dos.writeBytes(account+"\n");
			switch(commond) {
			case "diposit":
			case "withdraw":
				System.out.println("请输入金额:");
				dos.writeDouble(scan.nextDouble());
				scan.nextLine();
			}
		}
		
	}
}
