package invalid.bt.my_budget.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/*
    Inspired by the very nice solution here:

        https://stackoverflow.com/questions/43913753/spring-boot-with-redirecting-with-single-page-angular2

    Routes defined on the backend have a higher priority, otherwise everything is redirected to /index.html.

    The Angular will then:

        - trigger a proper client site routing event
        - or complain about an unknown URL segment
            - this should make displaying a nice 404 error page for the user doable

    Anything resembling an asset will not be handled, so that a proper 404 error can be sent from
    the server side (this will be less confusing from the client's perspective).

    If the request is assumed not be an asset (most probably HTML), I chose to return a 202/Accepted
    status as a way to hint that something unusual, but still correct, is happening.
 */

@Controller
public class AngularController implements ErrorController {

    private final String indexContent;

    public AngularController() {
        InputStream indexStream;

        // This file comes from the `frontend` module's compiled jar
        try {
            indexStream = new ClassPathResource("META-INF/resources/index.html").getInputStream();
        } catch (IOException e) {
            // Oddly, this only happens when running via IntelliJ, not via mvn spring-boot:run.
            // I could not find where the cached data would live.
            throw new RuntimeException("If in running in dev, launch this command: `./mvnw -f frontend install`. Otherwise, this should never happen!");
        }

        InputStreamReader reader = new InputStreamReader(indexStream);
        this.indexContent = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
    }

    @RequestMapping(value = "/error")
    public ResponseEntity<String> error(HttpServletRequest request) {
        if (isProbablyAnAsset(getOriginalPath(request))) {
            return new ResponseEntity<>("Resource not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(indexContent, HttpStatus.ACCEPTED);

    }

    private String getOriginalPath(HttpServletRequest request) {
        // Thanks stackoverflow
        String originalPath = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        if (originalPath == null) {
            throw new NullPointerException("`originalPath` should never be null! Maybe some framework internals changed?");
        }
        return originalPath;
    }

    private boolean isProbablyAnAsset(final String originalPath) {
        List<String> nonHtmlExtensions = List.of(".css", ".js", ".jpg", ".jpeg", ".png", ".svg");
        for (String ext : nonHtmlExtensions) {
            if (originalPath.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

}
