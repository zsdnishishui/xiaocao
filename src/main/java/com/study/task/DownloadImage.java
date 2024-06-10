package com.study.task;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class DownloadImage implements Runnable {
    private String downUrl;
    private String url;
    private ProgressBar bar;
    private Text text;

    /**
     * @param downUrl
     * @param url     fileUrl
     * @param bar
     * @param text
     */
    public DownloadImage(String downUrl, String url, ProgressBar bar, Text text) {
        this.downUrl = downUrl;
        this.url = url;
        this.bar = bar;
        this.text = text;
    }

    @Override
    public void run() {

        //实现https免证书认证
        CloseableHttpClient httpclient = null;
        try {
            httpclient = HttpClients.custom()
                    .setHostnameVerifier(new AllowAllHostnameVerifier())
                    .setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                            return true;
                        }
                    }).build()).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            RequestConfig requestConfig = RequestConfig.custom()
//                    .setProxy(GetHtmlUtil.proxy)
                    .build();

            // 将上面的配置信息 运用到这个Get请求里
            downUrl = downUrl.replace("x/?i=i", "i");
            HttpGet PicturehttpGet = new HttpGet(downUrl);
            PicturehttpGet.setConfig(requestConfig);
            CloseableHttpResponse pictureResponse = httpclient.execute(PicturehttpGet);
            HttpEntity pictureEntity = pictureResponse.getEntity();
            InputStream inputStream = pictureEntity.getContent();
            // 使用 common-io 下载图片到本地，注意图片名不能重复 ✔
            if (!url.contains(".")) {
                url += ".jpg";
            }

            FileUtils.copyToFile(inputStream, new File(url));
            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    text.append(downUrl + "\n");
                    if (bar != null) {
                        bar.setSelection(bar.getSelection() + 1);
                    }
                }

            });
            pictureResponse.close();
        } catch (IOException e) {
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    text.append(downUrl + "\n");
                    if (bar != null) {
                        bar.setSelection(bar.getSelection() + 1);
                    }
                }
            });
            e.printStackTrace();
        }
    }

}
