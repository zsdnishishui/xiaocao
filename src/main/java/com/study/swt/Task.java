package com.study.swt;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.study.util.StringUtil;
import com.study.xiaocao.GetHtml;

public class Task extends Thread{
	 
		private Text text = null; 
		private String title = null; 
		private boolean stop; 
		private String fun;
		
		public String getFun() {
			return fun;
		}
		public void setFun(String fun) {
			this.fun = fun;
		}
		public Task(Text text) {
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
			if ("title".equals(fun)) {
				title();
			} else if ("people".equals(fun)){
				people();
			}else if ("pl".equals(fun)){
				plnum();
			}
			
	  
	  
	    } 
		public void title(){
			for (int i = 1; i <= 100; i++) {
	    		if(stop){
	    			break;
	    		}
	    		
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String html = GetHtml.getHtml("https://cl.w8li.com/thread0806.php?fid=7&search=&page=" + i);
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
		}
		public void plnum(){
			for (int i = 1; i <= 10; i++) {
	    		if(stop){
	    			break;
	    		}
	    		
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String html = GetHtml.getHtml("https://cl.w8li.com/thread0806.php?fid=7&search=&page=" + i);
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
			text.append("查询结束\n");
		}
		public void people(){
			for (int i = 1; i <= 100; i++) {
	    		if(stop){
	    			break;
	    		}
	    		
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String html = GetHtml.getHtml("https://cl.w8li.com/thread0806.php?fid=7&search=&page=" + i);
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
		}
}
