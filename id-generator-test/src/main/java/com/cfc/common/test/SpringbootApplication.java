package com.cfc.common.test;

import com.cfc.common.workerid.api.GetWorkerIdRequest;
import com.cfc.common.workerid.api.TransInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.Lifecycle;


@SpringBootApplication
public class SpringbootApplication {




    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(SpringbootApplication.class);
        ApplicationContext ctx = springApplication.run(args);


        ((Lifecycle) ctx).start();
    }
}
