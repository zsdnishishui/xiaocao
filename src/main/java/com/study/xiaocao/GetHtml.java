package com.study.xiaocao;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class GetHtml {
private static String[] headers={"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50"};
	 
    public static  String getHtml (String url,boolean flag){
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
		int reand = (int)(Math.random()*headers.length);
		 //设置请求头
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("User-Agent", headers[reand]);
		//httpGet.setHeader("x-forwarded-for","125.77.49.244");
		//HttpHost proxy = new HttpHost("119.57.108.109", 53281, "http");
		
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 配置信息
			RequestConfig requestConfig = RequestConfig.custom()
					//.setProxy(proxy)
					// 设置连接超时时间(单位毫秒)
					.setConnectTimeout(5000)
					// 设置请求超时时间(单位毫秒)
					.setConnectionRequestTimeout(5000)
					// socket读写超时时间(单位毫秒)
					.setSocketTimeout(5000)
					// 设置是否允许重定向(默认为true)
					.build();
 
			// 将上面的配置信息 运用到这个Get请求里
			httpGet.setConfig(requestConfig);
			if (flag) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
 
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				html = new String(EntityUtils.toString(responseEntity).getBytes("ISO-8859-1"),"gbk");
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
    public static  String realUrl (String url){
        String realUrl = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpContext httpContext = new BasicHttpContext();
		// 创建Get请求
		HttpGet httpGet = new HttpGet(url);
		
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 配置信息
			RequestConfig requestConfig = RequestConfig.custom()
					//.setProxy(proxy)
					// 设置连接超时时间(单位毫秒)
					.setConnectTimeout(5000)
					// 设置请求超时时间(单位毫秒)
					.setConnectionRequestTimeout(5000)
					// socket读写超时时间(单位毫秒)
					.setSocketTimeout(5000)
					// 设置是否允许重定向(默认为true)
					.build();
 
			// 将上面的配置信息 运用到这个Get请求里
			httpGet.setConfig(requestConfig);
			/*try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet,httpContext);
			HttpHost targetHost = (HttpHost)httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
			HttpUriRequest realRequest = (HttpUriRequest)httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
			// 从响应模型中获取响应实体
            realUrl=targetHost+""+realRequest.getURI();
			
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
        return realUrl;
    }
}
