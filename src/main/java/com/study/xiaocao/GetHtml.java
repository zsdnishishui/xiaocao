package com.study.xiaocao;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetHtml {

	 /**
	  * 
	  * @param url 访问路径
	  * @return
	  */
    public  Document getDocument (String url){
        try {
       	 //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(50000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static  String getHtml (String url){
        String html = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        
		/*// 参数
		StringBuffer params = new StringBuffer();
		try {
			// 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
			params.append("name=" + URLEncoder.encode("&", "utf-8"));
			params.append("&");
			params.append("age=24");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}*/
 
		// 创建Get请求
		HttpGet httpGet = new HttpGet(url);
		//HttpHost proxy = new HttpHost("123.163.97.219", 9);
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 配置信息
			RequestConfig requestConfig = RequestConfig.custom()
					// 设置连接超时时间(单位毫秒)
					.setConnectTimeout(5000)
					// 设置请求超时时间(单位毫秒)
					.setConnectionRequestTimeout(5000)
					// socket读写超时时间(单位毫秒)
					.setSocketTimeout(5000)
					// 设置是否允许重定向(默认为true)
					.setRedirectsEnabled(true).build();
 
			// 将上面的配置信息 运用到这个Get请求里
			httpGet.setConfig(requestConfig);
 
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
 
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			//System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				//System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				//System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
				html = new String(EntityUtils.toString(responseEntity).getBytes("ISO-8859-1"),"gbk");
				//System.out.println("响应内容为:" + new String(EntityUtils.toString(responseEntity).getBytes("gbk"),"utf-8"));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return html;
    }
public static void showMaxPinglun() throws InterruptedException{
	for (int i = 1; i <=100; i++) {
    	Thread.sleep(1000);
    	System.out.println(i);
    	String html = getHtml("https://cl.w8li.com/thread0806.php?fid=7&search=&page="+i);
       //System.out.println(html);
    	//这一句增强了系统健壮性
        if ("".equals(html)) {
			i--;
		}
        Document doc = Jsoup.parse(html);
        // 获取目标HTML代码
        Elements elements1 = doc.select("span[style=font-size:7pt;font-family:verdana;]");
        //今天
        //Elements elements2 = elements1.select("td a");
        for(Element ele:elements1){
        	/*if("东尼".equals(ele.text())){
        		System.out.println("页面链接："+ele.parent().parent().select("h3 a").attr("href"));
        	}*/
        	/*if("大麦麦".equals(ele.text())&&ele.parent().parent().text().contains("美元")){
        		System.out.println("页面链接："+ele.parent().parent().select("h3 a").attr("href"));
        	}*/
        	/*if(ele.text().contains("")){
        		System.out.println("页面链接："+ele.parent().parent().select("h3 a").attr("href"));
        	}*/
        	String pages = ele.text();
        	String[] page = pages.split(" ");
        	int last = Integer.valueOf(page[page.length-1]);
        	
        	if (last>=100) {
        		System.out.println(ele.parent().parent().text()+":页面链接："+ele.parent().parent().select("h3 a").attr("href"));
			}
        }
	}
}
public static void showtitleorzuozhe() throws InterruptedException{
	for (int i = 1; i <=100; i++) {
    	Thread.sleep(1000);
    	System.out.println(i);
    	String html = getHtml("https://cl.w8li.com/thread0806.php?fid=7&search=&page="+i);
       //System.out.println(html);
    	//这一句增强了系统健壮性
        if ("".equals(html)) {
			i--;
		}
        Document doc = Jsoup.parse(html);
        // 获取目标HTML代码
        Elements elements1 = doc.select("tbody");
        //今天
        Elements elements2 = elements1.select("td a");
        for(Element ele:elements2){
        	/*if("东尼".equals(ele.text())){
        		System.out.println("页面链接："+ele.parent().parent().select("h3 a").attr("href"));
        	}*/
        	/*if("大麦麦".equals(ele.text())&&ele.parent().parent().text().contains("美元")){
        		System.out.println("页面链接："+ele.parent().parent().select("h3 a").attr("href"));
        	}*/
        	if(ele.text().contains("下载番号")){
        		System.out.println(ele.text()+"：页面链接："+ele.parent().parent().select("h3 a").attr("href"));
        	}
        }
	}
}
    public static void main(String[] args) throws InterruptedException {
        
    	//showtitleorzuozhe();
    	showMaxPinglun();
    }
}
