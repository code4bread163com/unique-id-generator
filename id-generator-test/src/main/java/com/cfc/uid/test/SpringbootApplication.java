package com.cfc.uid.test;

import com.cfc.uid.generate.core.DefaultUidGenerator;
import com.cfc.uid.generate.core.UidGenService;
import com.cfc.uid.generate.core.UidGenerator;
import com.cfc.uid.generate.core.WorkerIdAssigner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);

//        SpringApplication springApplication = new SpringApplication(SpringbootApplication.class);
//        ApplicationContext ctx = springApplication.run(args);

//        List<String> serviceNames = SpringFactoriesLoader.loadFactoryNames(UidGenerator.class,null);
//        for (String serviceName:serviceNames){
//            System.out.println(serviceName);
//        }
//
//        List<UidGenerator> services = SpringFactoriesLoader.loadFactories(UidGenerator.class,null);
//        for (UidGenerator demoService:services){
//            demoService.getUID();
//        }

//        UidGenService impl = ctx.getBean(UidGenService.class);
//        long id = impl.getUid();


//        ((Lifecycle) ctx).start();
    }
}
