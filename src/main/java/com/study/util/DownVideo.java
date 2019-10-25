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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;

public class DownVideo {
    long length=0;
    public static long reveive=0;//这是全局(全项目)变量，而 long reveive=0；也是全局变量，只是在这个类中是
    
	public float getDownloadProgress()
	{
		
		return (float) (reveive*100.0/length);
	}
	public int startDown(String dirUrl, String realUrl){
		try {
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
	        System.out.println(length);
	        if (length<=1000) {
				return -1;
			}
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
		return 0;
	}
	public void printProgress(ProgressBar bar){
		final Timer timer = new Timer();  
	    timer.scheduleAtFixedRate(new TimerTask() {  
	            public void run() {
	            	if (caoliuVideoThread.stop) {
	            		timer.cancel();
					}
	            	float pro =getDownloadProgress(); 
	            	Display.getDefault().asyncExec(new Runnable(){  
	                    
	                    public void run() {
	                    	if (bar!=null) {
	                    		bar.setSelection(Math.round(pro));
	        				}
	                    }

	                });
	            	System.out.println("下载："+pro+"%"); 
	                if(pro >= 99.9){
	                	System.out.println("下载完成"); 
	                	timer.cancel();
	                	Display.getDefault().asyncExec(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								MessageBox messageBox = new MessageBox(bar.getShell());
						        messageBox.setMessage("下载完成");
						        messageBox.open();
							}  
		                    
	                		

		                });
	                	
	                } 
	            }  
	    }, 1000 , 2000);
	}
}
