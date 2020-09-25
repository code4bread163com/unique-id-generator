package com.cfc.common.workerid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cfc.common.workerid.dao")
public class WorkerIdServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(WorkerIdServerApplication.class, args);

    }
}
