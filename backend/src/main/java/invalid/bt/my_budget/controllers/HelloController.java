package invalid.bt.my_budget.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    @ResponseBody
    public String get() {
        return "World!";
    }
}
