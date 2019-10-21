package com.study.swt;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.study.xiaocao.GetHtml;

public class Task extends Thread{
	 

		//线程方法体，与前面单个的进度条的程序类似  
		private Text text = null; 
		private String title = null; 
		public Task(Text text,String title) {  
	       this.text=text;
	       this.title=title;
	    } 
	    public void run() {
	    	for (int i = 1; i <= 100; i++) {
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
						/*
						 * if("东尼".equals(ele.text())){
						 * System.out.println("页面链接："+ele.parent().parent().
						 * select("h3 a").attr("href")); }
						 * if("大麦麦".equals(ele.text())&&ele.parent().parent().
						 * text().contains("美元")){
						 * System.out.println("页面链接："+ele.parent().parent().
						 * select("h3 a").attr("href")); }
						 */
						/*Display.getCurrent().asyncExec(new Runnable() {
							public void run() {
								
							}
						});*/
						if (ele.text().contains(title)) {
							Display.getDefault().asyncExec(new Runnable(){  
				                
				                public void run() {  
				  
				                	text.append(ele.text() + "：页面链接：" + ele.parent().parent().select("h3 a").attr("href")+"\n");
				                }
				  
				            });
							
							
						}
					}
				}
				
			}
	  
	  
	    } 
}
