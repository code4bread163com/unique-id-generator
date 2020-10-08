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
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@ImportResource(locations = { "classpath:config/cached-uid-spring.xml" })
@Import(FeignClientsConfiguration.class)
public class UIDConfig {

    @Autowired
    WorkerIdInterface workerIdInterface;


    @Bean
    public WorkerIdInterface workerIdService(Decoder decoder, Encoder encoder, Contract contract) {


        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract).target(WorkerIdInterface.class, "http://" + serviceId);


//        return Feign.builder().contract(contract).encoder(encoder).decoder(decoder).target(WorkerIdService.class, "http://localhost:8004");
    }

    @Bean
    public WorkerIdAssigner workerIdAssigner() {
        WorkerIdAssigner workerIdAssigner = new WorkerIdAssigner();
//        workerIdAssigner.setWorkerIdService(workerIdService);
        return workerIdAssigner;
    }

    @Bean
    public DefaultUidGenerator defaultUidGenerator() {
        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
//        defaultUidGenerator.setWorkerIdAssigner(workerIdAssigner());
        return defaultUidGenerator;
    }

    @Bean
    public UidGenService uidGenService() {
        return new UidGenService();
    }
}
