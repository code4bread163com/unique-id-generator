package com.cfc.uid.test;

import com.cfc.uid.generate.core.DefaultUidGenerator;
import com.cfc.uid.generate.core.UidGenerator;
import com.cfc.uid.generate.core.WorkerIdAssigner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(SpringbootApplication.class);
        ApplicationContext ctx = springApplication.run(args);

        UidGenerator generator = ctx.getBean(DefaultUidGenerator.class);
        long id = generator.getUID();

        ((Lifecycle) ctx).start();
    }
}
