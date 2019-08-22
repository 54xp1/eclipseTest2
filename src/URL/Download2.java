package URL;
import java.io.FileInputStream;
// 多线程下载 + 合并文件 + 统计下载时间
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Download2 {
	// 已经完成下载的块数
	static int downloadCount ;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		String path = "http://mirror.bit.edu.cn/apache/tomcat/tomcat-7/"
				+ "v7.0.94/bin/apache-tomcat-7.0.94.zip";
		URL url = new URL(path);
		
		URLConnection conn = url.openConnection();
		String diskPath = "g:/"+path.substring(path.lastIndexOf("/")+1);
		
		// 文件大小  
		int length = conn.getContentLength();
		System.out.println("要下载的文件大小"+length);
		
		// 定义分块下载的大小   2M
		int blockSize = 1024 * 1024 * 2 ;
		// 循环次数
		int forCount = length % blockSize == 0 ? length / blockSize : (length / blockSize + 1);
		System.out.println("循环次数"+forCount);

		System.out.println("文件开始下载");
		long time = System.currentTimeMillis();
		
		for(int i=0;i<forCount;i++) {
			System.out.println("开启第"+(i+1)+"块");
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
		}
		
		// 合并文件
		while(downloadCount<forCount) {
			synchronized (Download2.class) {
				Download2.class.wait();
			}
		}
		time = (System.currentTimeMillis() - time) / 1000;
		System.out.println("文件下载完成，共用时间:"+time+"秒");
		
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
		System.out.println("文件合并完成！！！");
	}
	
	// 下载文件块                                           i 为文件块数
	private static void download(URLConnection c, String diskPath, int begin, int end, int i) throws IOException {
		InputStream in = c.getInputStream();
		FileOutputStream fos = new FileOutputStream(diskPath+"_"+i);
		byte []buffer = new byte[1024];
		int count;
		long time = System.currentTimeMillis();
		// 让流跳过 begin 个字节
		in.skip(begin);
		int downSize = begin;
		
		System.out.println("第"+(i+1)+"块开始下载");
		while( (count=in.read(buffer))>0  && downSize < end) {
			if(downSize + count > end) {
				count = 1024 -(downSize + count - end);
			}
			downSize += count;
			fos.write(buffer,0,count);
		}
		
		time = (System.currentTimeMillis()-time)/1000;
		System.out.println("第"+(i+1)+"块下载完成  "+"共用时："+time);

		// 下载完成后通知主线程
		synchronized (Download2.class) {
			Download2.downloadCount++;
			Download2.class.notify();
		}
		fos.close();
		in.close();
	}
	
	
	
}
