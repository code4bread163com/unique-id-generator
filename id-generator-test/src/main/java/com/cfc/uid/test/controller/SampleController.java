package com.cfc.uid.test.controller;

import com.cfc.uid.generate.core.UidGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by apple on 2018/5/29.
 */
@Controller
@EnableAutoConfiguration
public class SampleController {

    @Autowired
    private UidGenService uidGenService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
//        List<String> serviceNames = SpringFactoriesLoader.loadFactoryNames(ITest.class,null);
//        for (String serviceName:serviceNames){
//            System.out.println(serviceName);
//        }
//
//        List<ITest> services = SpringFactoriesLoader.loadFactories(ITest.class,null);
//        for (ITest demoService:services){
//            demoService.A();
//        }

        long id = uidGenService.getUid();

        return "Hello World!";
    }
}
