package com.cfc.workerid.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.cfc.workerid.server.dao")
@EnableEurekaClient
public class WorkerIdServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkerIdServerApplication.class, args);
    }
}
