package com.study.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;

public class DownloadImage implements Runnable {
	private String downUrl;
	private String url;
	private ProgressBar bar;
	 /**
	  * 
	  * @param downUrl
	  * @param url fileUrl
	 * @param bar 
	  */
     public DownloadImage(String downUrl,String url, ProgressBar bar){
         this.downUrl = downUrl;
         this.url = url;
         this.bar = bar;
     }
	public void run() {
		// 创建httpclient实例
    	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		try {
			HttpGet PicturehttpGet = new HttpGet(downUrl);
        CloseableHttpResponse pictureResponse = httpclient.execute(PicturehttpGet);
        HttpEntity pictureEntity = pictureResponse.getEntity();
        InputStream inputStream = pictureEntity.getContent();
        // 使用 common-io 下载图片到本地，注意图片名不能重复 ✔
        if (!url.contains(".")) {
			url+=".jpg";
		}
        FileUtils.copyToFile(inputStream, new File(url));
        Display.getDefault().asyncExec(new Runnable(){  
            
            public void run() {
            	if (bar!=null) {
            		bar.setSelection(bar.getSelection() + 1);
				}
            }

        });
        pictureResponse.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
