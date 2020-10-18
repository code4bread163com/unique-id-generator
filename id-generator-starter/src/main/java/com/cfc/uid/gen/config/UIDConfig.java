package com.cfc.uid.gen.config;

import com.cfc.uid.gen.core.CachedUidGenerator;
import com.cfc.uid.gen.core.DefaultUidGenerator;
import com.cfc.uid.gen.core.UidGenService;
import com.cfc.uid.gen.core.WorkerIdAssigner;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${uid.generate.use-buffer:true}")
    private Boolean useBuffer;

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setReadTimeout(250000);
        httpComponentsClientHttpRequestFactory.setConnectTimeout(5000);
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }

    @Bean
    public WorkerIdAssigner workerIdAssigner() {
        return new WorkerIdAssigner();
    }

    @Bean
    public DefaultUidGenerator defaultUidGenerator() {
        return !useBuffer ? new DefaultUidGenerator() : null;
    }

    @Bean
    public CachedUidGenerator cachedUidGenerator() {
        return useBuffer ? new CachedUidGenerator() : null;
    }

    @Bean
    public UidGenService uidGenService() {
        UidGenService uidGenService = new UidGenService();
        uidGenService.setUidGenerator(useBuffer ? cachedUidGenerator() : defaultUidGenerator());
        return uidGenService;
    }
}
