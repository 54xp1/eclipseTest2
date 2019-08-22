package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException, InterruptedException {
		// ����Socket������      �˿ں�8000 �������ȡ 1024 ���ϵĶ˿ں�
		ServerSocket server = new ServerSocket(8000);
		// �ȴ��ͻ�������  �̻߳��ڴ�����
		System.out.println("������������8000");
		Socket socket = server.accept();
		System.out.println("�ͻ������ӳɹ�������");
		InputStream in = socket.getInputStream();   // �ֽ�������
		OutputStream out = socket.getOutputStream();
		Scanner scan = new Scanner(System.in);
		
	boolean running = true;	
		// ��������ȡ�ͻ��˷��͹�������Ϣ ���߳�
		Thread t1 = new Thread() {
			public void run() {
			while(running) {
				// ��Ŷ��������    1024���ֽ�
				byte[] buffer = new byte[1024];
				// ��Ŷ�����ֽ���
				int count;
				// read �Ƕ�ȡ�ͻ��˷��͹�������Ϣ  �߳��ڴ�����
				try {
					count = in.read(buffer);
					if(count>0) {
						String msg = new String(buffer,0,count);
						System.out.println("�ͻ���˵��"+msg);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			};
			}
		};
		t1.start();
		
		// ����������Ϣ���ͻ��˵��߳�
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
