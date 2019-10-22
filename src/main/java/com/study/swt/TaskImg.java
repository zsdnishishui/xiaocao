package com.study.swt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.study.util.DownloadImage;
import com.study.xiaocao.GetHtml;

public class TaskImg extends Thread{
	 
		private Text text = null; 
		private String title = null; 
		private boolean stop;
		public TaskImg(Text text) {
			this.text = text;
		}
		public boolean isStop() {
			return stop;
		}
		public void setStop(boolean stop) {
			this.stop = stop;
		}
		
	    public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void run() {
			title();
	    } 
		public void title(){
			String html = GetHtml.getHtml(title);
			if ("".equals(html)) {
				Display.getDefault().asyncExec(new Runnable(){  
	                
	                public void run() {  
	                	text.setText("止路径请求失败");
	                }
	  
	            });
			}else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)){
				
				Display.getDefault().asyncExec(new Runnable(){  
	                
	                public void run() {  
	                	text.setText("ip被锁");
	                }
	  
	            });
			}else{
				List<String> listImgUrl = new ArrayList<String>();
				System.out.println(html);
		    	Document doc = Jsoup.parse(html);
		        // 获取目标HTML代码
		        Elements elements1 = doc.select("img");
		        Elements topName = doc.select("h4");
		        Display.getDefault().asyncExec(new Runnable(){  
	                
	                public void run() {  
	                	text.append(topName.get(0).text()+"\n");
	                }
	  
	            });
		        for(Element ele:elements1){
		        	String url = ele.attr("data-src");
		        	if (!"".equals(url)) {
						listImgUrl.add(url);
						Display.getDefault().asyncExec(new Runnable(){  
			                
			                public void run() {  
			                	text.append(url+"\n");
			                }
			  
			            });
					}
		        }
		        /*//创建一个缓冲池
		        ExecutorService pool = Executors.newCachedThreadPool();
		        //设置其容量为9
		        pool = Executors.newFixedThreadPool(9);
		        String diaoNaoUrl = "D:/Temp/";
		        for(String url:listImgUrl){
		        	System.out.println(url);
		            String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
		            pool.execute(new DownloadImage(url,diaoNaoUrl+imageName));
		        }
		        pool.shutdown();
		        //线程池关闭了，但是线程没有关闭
*/			}
		}
		
}
