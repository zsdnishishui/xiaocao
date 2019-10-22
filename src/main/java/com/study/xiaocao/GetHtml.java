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
private static String[] headers={"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50"};
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
		int reand = (int)(Math.random()*headers.length);
		System.out.println(headers[reand]);
		/*httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
		httpGet.addHeader("Content-Type", "text/html");*/
		 //设置请求头
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.addHeader("User-Agent", headers[reand]);
		httpGet.addHeader("Cookie", "__cfduid=dda624dc3c43dd32d6594d24ce5ab70a11571729683; UM_distinctid=16df263d000ca-0501a5959945418-14377840-100200-16df263d0011df; CNZZDATA950900=cnzz_eid=173984157-1571725905-&ntime=1571732822; 227c9_lastvisit=0	1571734560	/read.php?tid=3540063&fpage=0&toread=&page=5; hibext_instdsigdipv2=1");
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
}
