package com.study.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class DownloadImage implements Runnable {
	 String downUrl;
	 String url;
	 /**
	  * 
	  * @param downUrl
	  * @param url fileUrl
	  */
     public DownloadImage(String downUrl,String url){
         this.downUrl = downUrl;
         this.url = url;
     }
	public void run() {

        try{
        	URL uri = new URL(downUrl);
			InputStream in = uri.openStream();
	        FileUtils.copyToFile(in, new File(url));         
        }catch(Exception e){
            e.printStackTrace();
        }
}

}
