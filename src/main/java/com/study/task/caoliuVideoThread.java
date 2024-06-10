package com.study.task;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class caoliuVideoThread implements Runnable {
    private int start;
    private int end;
    private String fileName;
    private String downUrl;
    private String dir;
    public static boolean stop = false;
    private int i;

    public caoliuVideoThread(int start, int end, String dir,
                             String fileName, String realUrl, int i) {
        super();
        this.start = start;
        this.end = end;
        this.fileName = fileName;
        this.dir = dir;
        this.downUrl = realUrl;
        this.i = i;
    }


    public void run() {
        CloseableHttpClient httpclient = null;
        try {
            httpclient = HttpClients.custom()
                    .setHostnameVerifier(new AllowAllHostnameVerifier())
                    .setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                            return true;
                        }
                    }).build()).build();
        } catch (KeyManagementException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (KeyStoreException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            //每次线程下载，先读缓存文本文件 断点续传
            File huancun = new File(dir + i + ".txt");
            if (huancun.exists()) {
                FileInputStream fis = new FileInputStream(huancun);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                int last = Integer.valueOf(br.readLine());
                //DownVideo.reveive+=last-start+1;
                start = last;
            }


            if (start < end) {
                HttpGet httpget = new HttpGet(downUrl);
                httpget.addHeader("Range", "bytes=" + start + "-" + end);

                HttpResponse response = httpclient.execute(httpget);

                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                File file = new File(dir + fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    //newFile.createNewFile();
                }
                byte[] b = new byte[1024];
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                raf.seek(start);
                int readcode = 0;
                int total = 0;
                while ((readcode = is.read(b)) != -1) {

                    if (stop) {
                        return;
                    } else {
                        total += readcode;
                        //每次下载都把新的下载位置写入缓存文本文件 断点续传
                        FileWriter writeName = new FileWriter(dir + i + ".txt");
                        writeName.write(start + total + "");
                        writeName.close();
                        raf.write(b, 0, readcode);
                    }

                }
                raf.close();
                is.close();
            }

            System.out.println("下载完成 thread" + i);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
