package invalid.bt.my_budget.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
    Very nice solution here:

        https://stackoverflow.com/questions/43913753/spring-boot-with-redirecting-with-single-page-angular2

    Defined routes have a higher priority, so they will still get served.

    Also, Angular's router will complain with a similar message if it also doesn't know a given route.

        Error: Cannot match any routes. URL Segment: 'hello2'

    So I should be able to display a nice 404 error page for the user.
 */

@Controller
public class AngularController implements ErrorController {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/error")
    public String error() {
        return "forward:/index.html";
    }

}
