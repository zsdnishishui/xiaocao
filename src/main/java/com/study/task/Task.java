package com.study.task;

import com.study.util.GetHtmlUtil;
import com.study.util.StringUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Task extends Thread {

    private Text text = null;
    private String title = null;
    private boolean stop = false;
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
        } else if ("people".equals(fun)) {
            people();
        } else if ("pl".equals(fun)) {
            plnum();
        } else if ("downImg".equals(fun)) {
            downImg();
        } else if ("downVideo".equals(fun)) {
            downVideo();
        }


    }

    public void title() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        for (int i = 1; i <= 100; i++) {
            if (!stop) {
                break;
            }

            String html = GetHtmlUtil.getHtmlForEach(httpClient, StringUtil.url + "?fid=7&page=" + i, true);
            if ("".equals(html)) {
                i--;
            } else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)) {

                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        text.setText("ip被锁");
                    }

                });
                break;
            } else {
                System.out.println(i);
                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        if (bar != null) {
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
                        Element parent = ele.parent().parent();
                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                String time = parent.child(2).text();
                                if (time.contains("昨天")) {
                                    Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String yesterday = simpleDateFormat.format(today);
                                    time = time.replace("昨天", yesterday);
                                } else if (time.contains("今天")) {
                                    Date today = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    time = time.replace("今天", simpleDateFormat.format(today));
                                }
                                text.append(time + "：" + ele.text() + "：页面链接：" + StringUtil.host + parent.select("h3 a").attr("href") + "\n");
                            }

                        });


                    }
                }
            }

        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Display.getDefault().asyncExec(new Runnable() {

            public void run() {
                text.append("查询结束\n");
            }

        });
    }

    public void plnum() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        for (int i = 1; i <= 100; i++) {
            if (!stop) {
                break;
            }

            String html = GetHtmlUtil.getHtmlForEach(httpClient, StringUtil.url + "?fid=7&page=" + i, true);
            if ("".equals(html)) {
                i--;
            } else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)) {

                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        text.setText("ip被锁");
                    }

                });
                break;
            } else {
                System.out.println(i);
                Display.getDefault().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (bar != null) {
                            bar.setSelection(bar.getSelection() + 1);
                        }
                    }
                });
                Document doc = Jsoup.parse(html);
                // 获取目标HTML代码
                Elements elements1 = doc.select(".tr3.t_one.tac").select("td:eq(3)");
                for (Element ele : elements1) {
                    if (StringUtil.isInteger(ele.text()) && Integer.valueOf(ele.text()) >= Integer.valueOf(title)) {
                        Display.getDefault().asyncExec(new Runnable() {

                            public void run() {
                                String time = ele.parent().child(2).text();
                                if (time.contains("昨天")) {
                                    Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String yesterday = simpleDateFormat.format(today);
                                    time = time.replace("昨天", yesterday);
                                } else if (time.contains("今天")) {
                                    Date today = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    time = time.replace("今天", simpleDateFormat.format(today));
                                }
                                text.append(time + "：" + ele.parent().select(".tal").select("h3").text() + "：页面链接：" + StringUtil.host + ele.parent().select("h3 a").attr("href") + "\n");
                            }

                        });


                    }
                }
            }

        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Display.getDefault().asyncExec(new Runnable() {

            public void run() {
                text.append("查询结束\n");
            }

        });

    }

    public void people() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        for (int i = 1; i <= 100; i++) {
            if (!stop) {
                break;
            }

            String html = GetHtmlUtil.getHtmlForEach(httpClient, StringUtil.url + "?fid=7&page=" + i, true);
            if ("".equals(html)) {
                i--;
            } else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)) {

                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        text.setText("ip被锁");
                    }

                });
                break;
            } else {
                System.out.println(i);
                Display.getDefault().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (bar != null) {
                            bar.setSelection(bar.getSelection() + 1);
                        }
                    }

                });
                Document doc = Jsoup.parse(html);
                // 获取目标HTML代码
                Elements elements1 = doc.select("tbody td a");
                Elements elements2 = elements1.select(".bl");
                for (Element ele : elements2) {
                    if (ele.text().equals(title)) {
                        Element tr = ele.parent().parent();
                        Display.getDefault().asyncExec(new Runnable() {

                            public void run() {
                                String time = tr.child(2).text();
                                if (time.contains("昨天")) {
                                    Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String yesterday = simpleDateFormat.format(today);
                                    time = time.replace("昨天", yesterday);
                                } else if (time.contains("今天")) {
                                    Date today = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    time = time.replace("今天", simpleDateFormat.format(today));
                                }
                                text.append(time + "：" + tr.select(".tal").select("h3").text() + "：页面链接：" + StringUtil.host + tr.select("h3 a").attr("href") + "\n");
                            }

                        });


                    }
                }
            }

        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Display.getDefault().asyncExec(new Runnable() {

            public void run() {
                text.append("查询结束\n");
            }

        });
    }

    public void downImg() {
        String html = GetHtmlUtil.getHtml(title, false);
        if ("".equals(html)) {
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    text.setText("止路径请求失败");
                }
            });
        } else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)) {
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    text.setText("ip被锁");
                }
            });
        } else {
            List<String> listImgUrl = new ArrayList<String>();
            Document doc = Jsoup.parse(html);
            // 获取目标HTML代码
            Elements elements1 = doc.select("img");
            Elements elements2 = doc.select("input");
            Elements topName = doc.select("h4");
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    text.append(topName.get(0).text() + "\n");
                }
            });
            for (Element ele : elements1) {
                String url = ele.attr("ess-data");
                if (!"".equals(url)) {
                    listImgUrl.add(url);
                }
            }
            for (Element ele : elements2) {

                if ("image".equals(ele.attr("type"))) {
                    String url = ele.attr("data-src");
                    if (!"".equals(url)) {
                        if (url.contains("sinaimg")) {
                            url = url.replace("https:", "http:");
                        }
                        listImgUrl.add(url);

                    }
                }

            }
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    bar.setMaximum(listImgUrl.size());
                }
            });

            //创建一个缓冲池
            ExecutorService pool = Executors.newCachedThreadPool();
            //设置其容量为9
            pool = Executors.newFixedThreadPool(9);

            for (String url : listImgUrl) {
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                pool.execute(new DownloadImage(url, diaoNaoUrl + topName.get(0).text().replace("\\", "").replace("/", "").replace("?", "").replace(":", "").replace("*", "").replace("\"", "").replace("<", "").replace(">", "").replace("|", "") + "/" + imageName, bar, text));
            }
            pool.shutdown();
            //线程池关闭了，但是线程没有关闭
        }
    }

    public void downVideo() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String html = GetHtmlUtil.getHtmlForEach(httpClient, title, false);
        if ("".equals(html)) {
            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    text.setText("止路径请求失败");
                }

            });
        } else if ("<html><head><meta http-equiv='refresh' content='2;url=codeform.php'></head>".equals(html)) {

            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    text.setText("ip被锁");
                }

            });
        } else {
            Document doc = Jsoup.parse(html);
            // 获取目标HTML代码
            Elements elements1 = doc.select(".tpc_content.do_not_catch a");
            Elements topName = doc.select("h4");
            String title = topName.get(0).text();
		        /*Display.getDefault().asyncExec(new Runnable(){  
	                public void run() {  
	                	text.append(title+"\n");
	                }
	            });*/
            String src = elements1.get(1).attr("onclick");
            //getElementById('iframe1').src='http://www.uuge010.com/play/video.php?id=64#iframeload'
            String url = src.substring(src.indexOf("=") + 2, src.indexOf("#"));
            String html2 = GetHtmlUtil.getHtmlForEach(httpClient, url, false);
            int n = 0;
            while (StringUtil.isEmpty(html2)) {
                html2 = GetHtmlUtil.getHtmlForEach(httpClient, url, false);
                n++;
                if (n > 1) {
                    Display.getDefault().asyncExec(new Runnable() {

                        public void run() {
                            text.setText("下载失败");
                        }

                    });
                    return;
                }
            }
            String realUrl = "";
            if (html2.indexOf("video_url: '") > 0) {
                String html3 = html2.substring(html2.indexOf("video_url: '"));

                String url2 = html3.substring(12, html3.indexOf(",") - 1);
                if (url2.startsWith("function")) {
                    System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                    //初始化一个chrome浏览器实例，实例名称叫driver
                    WebDriver driver = new ChromeDriver();
                    //最大化窗口
                    //driver.manage().window().maximize();
                    //设置隐性等待时间
                    driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
                    // get()打开一个站点
                    driver.get(url);
                    //getTitle()获取当前页面title的值
                    WebElement ele = driver.findElement(By.id("kt_player"));
                    ele.click();
                    String mp4Url = driver.findElements(By.tagName("video")).get(0).getAttribute("src");
                    driver.get(mp4Url);
                    realUrl = driver.getCurrentUrl();
                    driver.close();

                } else {
                    realUrl = GetHtmlUtil.realUrl(httpClient, url2);
                }
            } else if (html2.indexOf("video: '") > 0) {
                String html3 = html2.substring(html2.indexOf("video: '") + 8);
                realUrl = html3.substring(0, html3.indexOf("'"));
            } else if (html2.indexOf("url: '") > 0) {
                String html3 = html2.substring(html2.indexOf("url: '") + 6);
                realUrl = html3.substring(0, html3.indexOf("'"));
            } else {
                doc = Jsoup.parse(html2);

                if (doc.select("source").get(0) != null) {
                    realUrl = doc.select("source").get(0).attr("src");
                } else {
                    String videoSrc = doc.select("iframe").get(0).attr("src");
                    realUrl = videoSrc.split("=")[1];
                }

            }
            System.out.println(realUrl);
            DownVideo down = new DownVideo();
            String dir = title.replace("\\", "").replace("/", "").replace("?", "").replace(":", "").replace("*", "").replace("\"", "").replace("<", "").replace(">", "").replace("|", "");
            String windowUrl = diaoNaoUrl + dir + "/";
            int res = down.startDown(windowUrl, realUrl);
            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    text.setText(windowUrl + "1024.mp4");
                }

            });
            if (res != -1) {
                down.printProgress(bar);
            } else {
                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        text.setText("下载失败");
                    }

                });
            }

        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
