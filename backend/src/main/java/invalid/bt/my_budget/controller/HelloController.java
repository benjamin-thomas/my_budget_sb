package invalid.bt.my_budget.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Temporary endpoint, for testing.
 */
@RestController
public class HelloController {

    /*
        ./manage/dev/http GET /hello/public
     */
    @GetMapping("/hello/public")
    public String getPublic() {
        return "World! (public)";
    }

    /*
        ./manage/dev/http GET /hello/private
     */
    @GetMapping("/hello/private")
    public String getPrivate() {
        return "World! (private)";
    }
}
