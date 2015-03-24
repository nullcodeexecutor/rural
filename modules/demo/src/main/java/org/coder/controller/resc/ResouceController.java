package org.coder.controller.resc;

import org.rural.ui.Model;
import org.springframework.stereotype.Controller;

/**
 * Created by yuantao on 2015/3/24.
 */
@Controller
public class ResouceController {

    public String fineOne(Model model) {
        model.put("msg", "rescoure find one");
        return "json";
    }

}
