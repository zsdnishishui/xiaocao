package com.study.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

public class DownloadImage implements Runnable {
	private String downUrl;
	private String url;
	private ProgressBar bar;
	private Text text;
	 /**
	  * 
	  * @param downUrl
	  * @param url fileUrl
	 * @param bar 
	 * @param text 
	  */
     public DownloadImage(String downUrl,String url, ProgressBar bar, Text text){
         this.downUrl = downUrl;
         this.url = url;
         this.bar = bar;
         this.text = text;
     }
	public void run() {
		// 创建httpclient实例
    	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpHost proxy = new HttpHost("0.0.0.0", 8580, "http");
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setProxy(proxy)
					.build();

			// 将上面的配置信息 运用到这个Get请求里
			downUrl = downUrl.replace("x/?i=i","i");
			HttpGet PicturehttpGet = new HttpGet(downUrl);
			PicturehttpGet.setConfig(requestConfig);
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
            	text.append(downUrl+" 下载成功\n");
            	if (bar!=null) {
            		bar.setSelection(bar.getSelection() + 1);
				}
            }

        });
        pictureResponse.close();
		} catch (IOException e) {
			Display.getDefault().asyncExec(new Runnable(){  
                public void run() {  
                	text.append(downUrl+" 下载失败\n");
                	if (bar!=null) {
                		bar.setSelection(bar.getSelection() + 1);
    				}
                }
            });
			e.printStackTrace();
		}
}

}
