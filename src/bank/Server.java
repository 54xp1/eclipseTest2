package bank;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException {
		// ����Socket������
		ServerSocket serversocket = new ServerSocket(2516);
		// ѭ���������󣬻�ȡSocket����
		Socket socket = serversocket.accept();
		// ��������:����������Ϣ
		InputStream in = socket.getInputStream();
		DataInputStream dis = new DataInputStream(in);  // ������  ͨ�����췽������
		Scanner scan = new Scanner(System.in);
		boolean running = true;
		while (running) {

			String commond = dis.readLine();
			System.out.println("����" + commond);
			String account = dis.readLine();
			System.out.println("�˻�" + account);
			double money;

			switch (commond) {
			case "balance":
				break;
			case "diposit":
				money = dis.readDouble();
				System.out.println("���" + money);
				break;
			case "withdraw":
				money = dis.readDouble();
				System.out.println("���" + money);
				break;

			}
		}
	}
}
