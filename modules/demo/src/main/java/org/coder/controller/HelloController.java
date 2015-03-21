package org.coder.controller;

import org.rural.ui.Model;
import org.springframework.stereotype.Controller;

/**
 * Created by yuantao on 2015/3/21.
 */
@Controller
public class HelloController {

    public String sayHello(Model model) {
        model.put("msg", "Hello World!");
        return "json";
    }

}
