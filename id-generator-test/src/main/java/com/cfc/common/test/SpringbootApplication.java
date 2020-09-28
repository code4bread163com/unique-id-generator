package com.cfc.common.test;

import com.cfc.common.uid.core.WorkerIdAssigner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.cfc.common.uid.core", "com.cfc.common.uid.config"})
public class SpringbootApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(SpringbootApplication.class);
        ApplicationContext ctx = springApplication.run(args);

        WorkerIdAssigner assigner = ctx.getBean(WorkerIdAssigner.class);
        assigner.getWorkerId();

        ((Lifecycle) ctx).start();
    }
}
