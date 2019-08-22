package URL;
import java.io.FileInputStream;
// ���߳����� + �ϲ��ļ� + ͳ������ʱ�� + ���ؽ���  �ٷֱ�
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Download3 {
	// �Ѿ�������صĿ���
	static int downloadCount ;
	// �Ѿ���������ֽ���
	static int downloaddeSize = 0;
	// ��ǰ���ؽ���
	static int downloadedRate = 0;
	// ����߳�����
	final static int MAX_THREAD_SIZE = 3;
	//  ��ǰ�ѿ������߳�����
	static int curThreadSize = 0;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		String path = "http://mirror.bit.edu.cn/apache/tomcat/tomcat-7/"
				+ "v7.0.94/bin/apache-tomcat-7.0.94.zip";
		URL url = new URL(path);
		
		URLConnection conn = url.openConnection();
		String diskPath = "g:/"+path.substring(path.lastIndexOf("/")+1);
		
		// �ļ���С  
		int length = conn.getContentLength();
		System.out.println("Ҫ���ص��ļ���С"+length);
		
		// ����ֿ����صĴ�С   2M
		int blockSize = 1024 * 1024 * 2 ;
		// ѭ������
		int forCount = length % blockSize == 0 ? length / blockSize : (length / blockSize + 1);
		System.out.println("ѭ������"+forCount);

		System.out.println("�ļ���ʼ����");
		long time = System.currentTimeMillis();
		
		for(int i=0;i<forCount;i++) {
			System.out.println("������"+(i+1)+"��");
			int begin = i * blockSize;
			int end = begin + blockSize;
			URLConnection c = url.openConnection();
			int index = i;
			new Thread() {
				public void run() {
					try {
						download(c,diskPath,begin,end,index);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}.start();
			
			synchronized (Download3.class) {
				curThreadSize++;
				while(curThreadSize>=MAX_THREAD_SIZE) {
					System.out.println("��ǰ�߳���"+curThreadSize);
					Download3.class.wait();
				}
			}
		}
		
		// �ϲ��ļ�
		while(downloadCount<forCount) {
			synchronized (Download3.class) {
				Download3.class.wait();
			}
		}
		time = (System.currentTimeMillis() - time) / 1000;
		System.out.println("�ļ�������ɣ�����ʱ��:"+time+"��");
		
		FileOutputStream fos = new FileOutputStream(diskPath);
		for(int i=0;i<forCount;i++) {
			FileInputStream fis = new FileInputStream(diskPath+"_"+i);
			byte []buffer = new byte[1024];
			int count;
			while((count = fis.read(buffer))>0) {
				fos.write(buffer,0,count);
			}
			fis.close();
		} 
		fos.close();
		System.out.println("�ļ��ϲ���ɣ�����");
	}
	
	// �����ļ���                                           i Ϊ�ļ�����
	private static void download(URLConnection c, String diskPath, int begin, int end, int i) throws IOException {
		int contentLength = c.getContentLength();
		
		InputStream in = c.getInputStream();
		FileOutputStream fos = new FileOutputStream(diskPath+"_"+i);
		byte []buffer = new byte[1024];
		int count;
		long time = System.currentTimeMillis();
		// �������� begin ���ֽ�
		in.skip(begin);
		int downSize = begin;
		
		System.out.println("��"+(i+1)+"�鿪ʼ����");
		while( (count=in.read(buffer))>0  && downSize < end) {
			if(downSize + count > end) {
				count = 1024 -(downSize + count - end);
			}
			downSize += count;
			// ͳ�����ص�����
			synchronized (Download3.class) {
				Download3.downloaddeSize +=count;
				int rate = downloaddeSize * 100 / contentLength;
				if(rate > downloadedRate) {
					System.out.println("���ؽ��ȣ�"+rate+"%");
					downloadedRate = rate;
				}
			}
			
			fos.write(buffer,0,count);
		}
		
		time = (System.currentTimeMillis()-time)/1000;
		System.out.println("��"+(i+1)+"���������  "+"����ʱ��"+time);

		// ������ɺ�֪ͨ���߳�
		synchronized (Download3.class) {
			Download3.downloadCount++;
			Download3.class.notify();
		}
		fos.close();
		in.close();
	}
	
	
	
}
