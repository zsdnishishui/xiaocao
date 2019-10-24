package com.study.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DownVideo {
    long length=0;
    public static long reveive=0;//这是全局(全项目)变量，而 long reveive=0；也是全局变量，只是在这个类中是
    
	public float getDownloadProgress()
	{
		
		return (float) (reveive*100.0/length);
	}
	public void startDown(String dirUrl, String realUrl){
		System.out.println(dirUrl);
		try {
			//final String fileUrl = "D:/Temp/070000.mp4";
			//String downUrl = "https://vod.she1shipin1.com/remote_control.php?time=1569033308&cv=3d0d35047eedcab53a8d4f1a0092cd6a&lr=348000&cv2=9e86b8a69669865687122f9fc8d52789&file=/videos/24000/24491/24491.mp4&cv3=6fc17b8fea133d6914ab62ff3df10012&cv4=4f9117d51211085bf5822e6b485ed205";
			//String downUrl = "https://vod.she1shipin1.com/remote_control.php?time=1568985352&cv=e276fccad028e8912e5030bdbca02546&lr=229000&cv2=fef4a16be3cfd13dee2b2da099c93ebd&file=/videos/24000/24282/24282.mp4&cv3=6fc17b8fea133d6914ab62ff3df10012&cv4=b852fc6e73c136d72983f7bbcfe0b6b6";
			//String downUrl = "https://haicao22.com/get_file/3/6ae59d3edc2e98cc2c5a136939e5f71ad7722f74e3/2000/2571/2571_360p.mp4/?br=497&amp;embed=true&amp;rnd=1568986302137";
			//String downUrl = "https://haicao22.com/get_file/3/7e8e202caae1f2cf1a903b5331095b7e87f9aba3eb/2000/2438/2438_360p.mp4/?br=305&amp;embed=true&amp;rnd=1568992701884";
			//String downUrl = "https://yuese57.com/get_file/1/9139fbe6edce6047ec24636b2ebb4a39acdf7deaa2/7000/7434/7434.mp4/?br=510&amp;embed=true&amp;rnd=1569032402400";
			//String downUrl = "https://ppx129.com/get_file/1/dfb86b59c5ead11eedf0104a20a8d268/10000/10885/10885.mp4/?br=628&amp;embed=true&amp;rnd=1569034792745";
			//String downUrl = "https://vod.yue1shipin1.com/remote_control.php?time=1569069749&cv=3ffd2d28bc08240f3a13d14e87041400&lr=152250&cv2=249053e4547270b493582c8f7f98eb1f&file=/contents/videos/6000/6596/6596.mp4&cv3=d5682d5be1c21b902e64be381f9ca298&cv4=d29c90a030b53f14feaae105b3b55407";
			//String downUrl ="https://vod.yue1shipin1.com/remote_control.php?time=1569071435&cv=6e604efc9f1324f362511720efca7843&lr=197625&cv2=fc56ed22b4093c08a38c4aa422f52479&file=/contents/videos/21000/21615/21615.mp4&cv3=d5682d5be1c21b902e64be381f9ca298&cv4=fab97dc3e3e17fe16f0f80480ebc6e17";
			//实现https免证书认证
			CloseableHttpClient httpclient = HttpClients.custom()
			        .setHostnameVerifier(new AllowAllHostnameVerifier())
			        .setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
			            {
			                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
			                {
			                    return true;
			                }
			            }).build()).build();
	        HttpGet httpget = new HttpGet(realUrl);
	        HttpResponse response = httpclient.execute(httpget);
	        HttpEntity responseEntity = response.getEntity();
	        length = responseEntity.getContentLength();
	      //创建一个缓冲池
	        ExecutorService pool = Executors.newCachedThreadPool();
	        //设置其容量为9
	        pool = Executors.newFixedThreadPool(10);
	        
	        // 定义线程数量
			int size = 5;
			int block = (int) (length % size == 0 ? length / size : length / size + 1);
			for (int i = 0; i < size; i++) {
				int start = i * block;
				System.out.println("block=" + block);
				int end = start + (block - 1);
				if (i == size - 1) {
					end++;
				}
				pool.execute(new caoliuVideoThread(start,end,dirUrl,"1024.mp4",realUrl,i));
				System.out.println("start=" + start + "end=" + end);
				
			}
			pool.shutdown();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void printProgress(){
		final Timer timer = new Timer();  
	    timer.scheduleAtFixedRate(new TimerTask() {  
	            public void run() {  
	            	float pro =getDownloadProgress(); 
	            	System.out.println("下载："+pro+"%"); 
	                if(pro >= 99.9){
	                	System.out.println("下载完成"); 
	                	timer.cancel();
	                	System.exit(0);
	                } 
	            }  
	    }, 1000 , 2000);
	}
}
