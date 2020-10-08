package com.cfc.uid.generate.config;

import com.cfc.uid.generate.core.DefaultUidGenerator;
import com.cfc.uid.generate.core.UidGenService;
import com.cfc.uid.generate.core.WorkerIdAssigner;
import com.cfc.uid.generate.interfaces.WorkerIdInterface;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
//@ImportResource(locations = { "classpath:config/cached-uid-spring.xml" })
//@Import(FeignClientsConfiguration.class)
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
