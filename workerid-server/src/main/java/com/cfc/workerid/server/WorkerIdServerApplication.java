package com.cfc.workerid.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.cfc.workerid.server.dao")
public class WorkerIdServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(WorkerIdServerApplication.class, args);

    }
}
