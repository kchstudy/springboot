package blog.benggri.springboot.config.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

import static blog.benggri.springboot.comm.util.StringUtil.STEP;

@Slf4j
@Configuration
public class RestTemplateConfig {

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        STEP(log, "- poolingHttpClientConnectionManager");
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(200);           // 최대 오픈되는 커넥션 수
        connectionManager.setDefaultMaxPerRoute(100); // IP, 포트 1쌍에 대해 수행할 커넥션 수
        return connectionManager;
    }

    @Bean
    public RequestConfig requestConfig() {
        STEP(log, "- requestConfig");
        return RequestConfig.custom()
                            .setConnectionRequestTimeout(15000) // 연결요청시간초과, ms
                            .setConnectTimeout(15000)           // 연결시간초과, ms
                            .setSocketTimeout(15000)            // 소켓시간초과, ms
                            .build();
    }

    @Bean
    public CloseableHttpClient httpClient(@Autowired PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, @Autowired RequestConfig requestConfig) {
        STEP(log, "- httpClient");
        return HttpClientBuilder.create()
                                .setConnectionManager(poolingHttpClientConnectionManager)
                                .setDefaultRequestConfig(requestConfig)
                                .build();
    }

    /**
     * restTemplate 생성 <br/>
     * 실제 소스에서 사용 시  <br/>
     * @Autowired  <br/>
     * // @Qualifier("restTemplate") // Bean 이름을 명시적으로 지정할 경우 사용  <br/>
     * private RestTemplate restTemplate;  <br/>
     * @param httpClient
     * @return
     */
    @Bean
    public RestTemplate restTemplate(@Autowired HttpClient httpClient) {
        STEP(log, "- restTemplate");
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
            httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }


    /* 추가 RestTemplate 이 필요할 경우 아래처럼 별도의 Bean을 생성 후 사용 */
    //@Bean("mydataRestTemplate")
    //public RestTemplate mydataRestTemplate(@Autowired HttpClient httpClient) {
    //    STEP(log, "- mydataRestTemplate");
    //    try {
    //        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
    //        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
    //                .loadTrustMaterial(null, acceptingTrustStrategy)
    //                .build();
    //        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
    //        httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //
    //    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    //    requestFactory.setHttpClient(httpClient);
    //    return new RestTemplate(requestFactory);
    //}
}
