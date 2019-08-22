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
		System.out.println("���������ӳɹ���");
		
		InputStream in = socket.getInputStream();   // �ֽ�������
		OutputStream out = socket.getOutputStream();
		Scanner scan = new Scanner(System.in);
		
		boolean running = true;	
		// �ͻ��˶�ȡ���������͵���Ϣ���߳�
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
						System.out.println("������˵��"+msg);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			};
			}
		};
		t1.start();
		
		// �ͻ��˷�����Ϣ�����������߳�
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
// �����߳�+wait()
