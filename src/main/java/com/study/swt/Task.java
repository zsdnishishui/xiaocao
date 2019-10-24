package com.study.swt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.study.util.DownVideo;
import com.study.util.DownloadImage;
import com.study.util.StringUtil;
import com.study.xiaocao.GetHtml;

public class Task extends Thread{
	 
		private Text text = null; 
		private String title = null; 
		private boolean stop=false; 
		private String fun;
		private String pageNo;
		private ProgressBar bar;
		private String diaoNaoUrl = "D:/Temp/";
		public String getFun() {
			return fun;
		}
		public void setFun(String fun) {
			this.fun = fun;
		}
		public Task(Text text, ProgressBar bar) {
			this.text = text;
			this.bar = bar;
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
			if ("title".equals(fun)) {
				title();
			} else if ("people".equals(fun)){
				people();
			}else if ("pl".equals(fun)){
				plnum();
			}else if ("downImg".equals(fun)){
				downImg();
			}else if ("downVideo".equals(fun)){
				downVideo();
			}
			
	  
	  
	    } 
		public void title(){
			for (int i = 1; i <= 100; i++) {
	    		if(stop){
	    			break;
	    		}
	    		pageNo=i+"";
				String html = GetHtml.getHtml("https://cl.w8li.com/thread0806.php?fid=7&search=&page=" + i,true);
				if ("".equals(html)) {
					i--;
				}else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)){
					
					Display.getDefault().asyncExec(new Runnable(){  
		                
		                public void run() {  
		                	text.setText("ip被锁");
		                }
		  
		            });
					break;
				}else{
					System.out.println(i);
					Display.getDefault().asyncExec(new Runnable(){  
		                
		                public void run() {
		                	if (bar!=null) {
		                		bar.setSelection(bar.getSelection() + 1);
							}
		                }
		  
		            });
					Document doc = Jsoup.parse(html);
					// 获取目标HTML代码
					Elements elements1 = doc.select("tbody td");
					Elements elements2 = elements1.select(".tal").select("h3");
					for (Element ele : elements2) {
						if (ele.text().contains(title)) {
							Display.getDefault().asyncExec(new Runnable(){  
				                
				                public void run() {  
				  
				                	text.append(ele.text() + "：页面链接：https://cl.w8li.com/" + ele.parent().parent().select("h3 a").attr("href")+"\n");
				                }
				  
				            });
							
							
						}
					}
				}
				
			}
			Display.getDefault().asyncExec(new Runnable(){  
                
                public void run() {  
                	text.append("查询结束\n");
                }
  
            });
		}
		public void plnum(){
			for (int i = 1; i <= 100; i++) {
	    		if(stop){
	    			break;
	    		}
	    		pageNo=i+"";
				String html = GetHtml.getHtml("https://cl.w8li.com/thread0806.php?fid=7&search=&page=" + i,true);
				if ("".equals(html)) {
					i--;
				}else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)){
					
					Display.getDefault().asyncExec(new Runnable(){  
		                
		                public void run() {  
		                	text.setText("ip被锁");
		                }
		  
		            });
					break;
				}else{
					System.out.println(i);
					Display.getDefault().asyncExec(new Runnable(){  
		                
		                public void run() {  
		                	text.append("第"+pageNo+"页\n");
		                }
		  
		            });
					Document doc = Jsoup.parse(html);
					// 获取目标HTML代码
					Elements elements1 = doc.select(".tr3.t_one.tac").select("td:eq(3)");
					for (Element ele : elements1) {
						if (StringUtil.isInteger(ele.text())&&Integer.valueOf(ele.text())>=Integer.valueOf(title)) {
							Display.getDefault().asyncExec(new Runnable(){  
				                
				                public void run() {  
				  
				                	text.append(ele.parent().select(".tal").select("h3").text()+ "：页面链接：https://cl.w8li.com/" + ele.parent().select("h3 a").attr("href")+"\n");
				                }
				  
				            });
							
							
						}
					}
				}
				
			}
			Display.getDefault().asyncExec(new Runnable(){  
                
                public void run() {  
                	text.append("查询结束\n");
                }
  
            });
			
		}
		public void people(){
			for (int i = 1; i <= 100; i++) {
	    		if(stop){
	    			break;
	    		}
	    		pageNo=i+"";
				String html = GetHtml.getHtml("https://cl.w8li.com/thread0806.php?fid=7&search=&page=" + i,true);
				if ("".equals(html)) {
					i--;
				}else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)){
					
					Display.getDefault().asyncExec(new Runnable(){  
		                
		                public void run() {  
		                	text.setText("ip被锁");
		                }
		  
		            });
					break;
				}else{
					System.out.println(i);
					Display.getDefault().asyncExec(new Runnable(){  
		                
		                public void run() {  
		                	text.append("第"+pageNo+"页\n");
		                }
		  
		            });
					Document doc = Jsoup.parse(html);
					// 获取目标HTML代码
					Elements elements1 = doc.select("tbody td a");
					Elements elements2 = elements1.select(".bl");
					for (Element ele : elements2) {
						if (ele.text().equals(title)) {
							Display.getDefault().asyncExec(new Runnable(){  
				                
				                public void run() {  
				  
				                	text.append(ele.parent().parent().select(".tal").select("h3").text() + "：页面链接：https://cl.w8li.com/" + ele.parent().parent().select("h3 a").attr("href")+"\n");
				                }
				  
				            });
							
							
						}
					}
				}
				
			}
			Display.getDefault().asyncExec(new Runnable(){  
                
                public void run() {  
                	text.append("查询结束\n");
                }
  
            });
		}
		public void downImg(){
			String html = GetHtml.getHtml(title,false);
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
		        Display.getDefault().asyncExec(new Runnable(){  
	                public void run() {  
	                	bar.setMaximum(listImgUrl.size());
	                }
	            });
		        
		        //创建一个缓冲池
		        ExecutorService pool = Executors.newCachedThreadPool();
		        //设置其容量为9
		        pool = Executors.newFixedThreadPool(9);
		        
		        for(String url:listImgUrl){
		            String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
		            pool.execute(new DownloadImage(url,diaoNaoUrl+topName.get(0).text().replace("\\", "").replace("/", "").replace("?", "").replace(":", "").replace("*", "").replace("\"", "").replace("<", "").replace(">", "").replace("|", "")+"/"+imageName,bar));
		        }
		        pool.shutdown();
		        //线程池关闭了，但是线程没有关闭
			}
		}
		public void downVideo(){
			String html = GetHtml.getHtml(title,false);
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
		    	Document doc = Jsoup.parse(html);
		        // 获取目标HTML代码
		        Elements elements1 = doc.select(".tpc_content.do_not_catch a");
		        Elements topName = doc.select("h4");
		        String title = topName.get(0).text();
		        Display.getDefault().asyncExec(new Runnable(){  
	                public void run() {  
	                	text.append(title+"\n");
	                }
	            });
		        String src = elements1.get(1).attr("onclick").split("=")[1];
		        String url = src.substring(1, src.indexOf("#"));
		        String html2 = GetHtml.getHtml(url,false);
		        while(StringUtil.isEmpty(html2)){
		        	html2 = GetHtml.getHtml(url,false);
		        }
		        //获取视频需要这个参数
		        String rnd = html2.substring(html2.indexOf("rnd: "));
		        
		        String html3 =html2.substring(html2.indexOf("video_url: '"));
		        
		        String url2 =html3.substring(12, html3.indexOf(",")-1);
		        String toGetUrl = url2.substring(url2.indexOf("http"));
		        System.out.println(toGetUrl);
		        String realUrl = GetHtml.realUrl(toGetUrl+"&rnd="+rnd.substring(6,rnd.indexOf(",")-1));
		        DownVideo down = new DownVideo();
		        String dir=title.replace("\\", "").replace("/", "").replace("?", "").replace(":", "").replace("*", "").replace("\"", "").replace("<", "").replace(">", "").replace("|", "");
		        down.startDown(diaoNaoUrl+dir+"/",realUrl);
		        down.printProgress(bar);
			}
		}
}
