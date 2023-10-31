package com.ltcyu.hotel.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: RestClientConfig
 * Description:
 * {@code @Author} 苏羽晨
 * {@code @Create} 2023/10/19 14:57
 * {@code @Version}  1.0
 */
@Configuration
public class RestClientConfig {
    @Bean
    public RestHighLevelClient client(){
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.10.100:9200")
        ));
    }
}
