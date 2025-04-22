package chatbot.api.infrastructure.utils;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Https工具类
 */
public class HttpsUtils {

    /**
     * 获取httpclinent,忽略证书认证
     *
     * @return HttpClients.createDefault()
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            // 构建忽略证书的 SSLContext
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            // 跳过主机名验证
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            org.apache.http.conn.ssl.SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            return HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 获取httpclinent,忽略证书认证,配置代理
     *
     * @return HttpClients.createDefault()
     */
    public static CloseableHttpClient createSSLClientDefaultWithProxy() {
        try {
            // 构建忽略证书的 SSLContext
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            // 跳过主机名验证
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            org.apache.http.conn.ssl.SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            // 定义本地Clash代理
            HttpHost proxy = new HttpHost("127.0.0.1", 7890);

            // 构建HttpClient:加入SSL忽略,代理配置
            return HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .setProxy(proxy)
                    .build();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

}
