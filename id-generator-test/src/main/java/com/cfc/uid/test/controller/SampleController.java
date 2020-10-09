package com.cfc.uid.test.controller;

import com.cfc.uid.generate.core.UidGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangliang
 * @date 2020/9/25
 */
@Controller
public class SampleController {

    @Autowired
    private UidGenService uidGenService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        long id = uidGenService.getUid();
        return String.valueOf(id);
    }
}
