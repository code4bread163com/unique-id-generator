package com.cfc.uid.generate.config;

import com.cfc.uid.generate.core.DefaultUidGenerator;
import com.cfc.uid.generate.core.UidGenService;
import com.cfc.uid.generate.core.WorkerIdAssigner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangliang
 * @date 2020/9/25
 */
@Configuration
public class UIDConfig {

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setReadTimeout(25000);
        httpComponentsClientHttpRequestFactory.setConnectTimeout(5000);
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }

    @Bean
    public WorkerIdAssigner workerIdAssigner() {
        return new WorkerIdAssigner();
    }

    @Bean
    public DefaultUidGenerator defaultUidGenerator() {
        return new DefaultUidGenerator();
    }

    @Bean
    public UidGenService uidGenService() {
        return new UidGenService();
    }
}
