package com.szy.blogcode.https;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.blogcode.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class HttpsActivity extends AppCompatActivity {
    private TextView textView;
    private Button getButton;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https);
        getButton = (Button) findViewById(R.id.getButton);
        textView = (TextView) findViewById(R.id.textView);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadThread().start();
            }
        });
    }

    class DownloadThread extends Thread {
        @Override
        public void run() {
            HttpsURLConnection conn = null;
            InputStream is = null;
            try {
                URL url = new URL("https://kyfw.12306.cn/otn/regist/init");
                conn = (HttpsURLConnection) url.openConnection();

                //创建X.509格式的CertificateFactory
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                //从asserts中获取证书的流
                InputStream cerInputStream = getAssets().open("srca.cer");//SRCA.cer
                //ca是java.security.cert.Certificate，不是java.security.Certificate，
                //也不是javax.security.cert.Certificate
                Certificate ca;
                try {
                    //证书工厂根据证书文件的流生成证书Certificate
                    ca = cf.generateCertificate(cerInputStream);
                    System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                } finally {
                    cerInputStream.close();
                }

                // 创建一个默认类型的KeyStore，存储我们信任的证书
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                //将证书ca作为信任的证书放入到keyStore中
                keyStore.setCertificateEntry("ca", ca);

                //TrustManagerFactory是用于生成TrustManager的，我们创建一个默认类型的TrustManagerFactory
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                //用我们之前的keyStore实例初始化TrustManagerFactory，这样tmf就会信任keyStore中的证书
                tmf.init(keyStore);
                //通过tmf获取TrustManager数组，TrustManager也会信任keyStore中的证书
                TrustManager[] trustManagers = tmf.getTrustManagers();

                //创建TLS类型的SSLContext对象， that uses our TrustManager
                SSLContext sslContext = SSLContext.getInstance("TLS");
                //用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
                sslContext.init(null, trustManagers, null);

                //通过sslContext获取SSLSocketFactory对象
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                //将sslSocketFactory通过setSSLSocketFactory方法作用于HttpsURLConnection对象
                //这样conn对象就会信任我们之前得到的证书对象
                conn.setSSLSocketFactory(sslSocketFactory);

                is = conn.getInputStream();
                //将得到的InputStream转换为字符串
                final String str = getStringByInputStream(is);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(str);
                        getButton.setEnabled(true);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                final String errMessage = e.getMessage();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getButton.setEnabled(true);
                        Toast.makeText(HttpsActivity.this, errMessage, Toast.LENGTH_LONG).show();
                    }
                });
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        /**
         * 将一个输入流转化为字符串
         */
        public  String getStringByInputStream(InputStream tInputStream){
            if (tInputStream != null){
                try{
                    BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
                    StringBuffer tStringBuffer = new StringBuffer();
                    String sTempOneLine = new String("");
                    while ((sTempOneLine = tBufferedReader.readLine()) != null){
                        tStringBuffer.append(sTempOneLine);
                    }
                    return tStringBuffer.toString();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            return null;
        }
    }
}
