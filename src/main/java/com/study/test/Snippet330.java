package com.study.test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Snippet330 {
	public static void main(String[] args) throws Exception {
		/*Desktop desktop = Desktop.getDesktop();
		if (desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
	        URI uri = new URI("https://blog.csdn.net/qq_36281804/article/details/80338292");
	        desktop.browse(uri); //使用系统默认的浏览器执行这个url
	        //Thread.sleep(2000);
	        //Runtime.getRuntime().exec("taskkill /F /IM Iexplore.exe");
	        //Runtime.getRuntime().exec("taskkill  /IM firefox.exe"); //因为我系统默认的是火狐,然后关闭火狐浏览器
	        }*/
		WebClient wc = new WebClient(BrowserVersion.CHROME);    
	      	    
	    //wc.setWebConnection(  
	    		
	    //		   new WebConnectionWrapper(wc) {  
	    //		    public WebResponse getResponse(WebRequest request) throws IOException {  
	    //		                 WebResponse response = super.getResponse(request);  
	    //		                 if (request.getUrl().toExternalForm().contains("test.js")) {  
	    //		                     String content = response.getContentAsString("GBK");  
	    //		                     WebResponseData data = new WebResponseData(content.getBytes("UTF-8"),  
	    //		                             response.getStatusCode(), response.getStatusMessage(), response.getResponseHeaders());  
	    //		                     response = new WebResponse(data, request, response.getLoadTime());  
	    //		                 }  
	    //		                 return response;  
	    //		             }  
	    //		   }  
	    		    
	    //); 
 
	    try {
	    	//htmlunit 对css和javascript的支持不好，所以请关闭之
			wc.getOptions().setJavaScriptEnabled(false);
			wc.getOptions().setCssEnabled(false);
			// 获取某网站页面
			HtmlPage page=wc.getPage("https://cl.w8li.com/htm_data/1910/22/3690347.html");
			System.out.println(page.asXml());
			HtmlElement passwordElmt = page.getElementByName("a");
		} catch (Exception e) {
			e.printStackTrace();}
	    }
	}
