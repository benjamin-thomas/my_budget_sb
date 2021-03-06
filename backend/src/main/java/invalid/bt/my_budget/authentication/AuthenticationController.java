package invalid.bt.my_budget.authentication;

import invalid.bt.my_budget.dto.TenantDTO;
import invalid.bt.my_budget.service.SignupService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final SignupService signupService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    SignupService signupService) {
        this.authenticationManager = authenticationManager;
        this.signupService = signupService;
    }

    /*
        ./manage/dev/http GET /me

        Returns the user's email address when signed in.
        Otherwise, returns "anonymousUser".
     */
    @GetMapping("/api/me")
    public String me() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/api/me-full")
    public Authentication meFull() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /*
        rm ./tmp/httpie/session && \
        # Get the CSRF token from the returned cookie (at least once before posting)
        ./manage/dev/http GET /hello/public && \
        ./manage/dev/http POST /api/login email=user@example.com password=123 && \
        ./manage/dev/http GET /api/me
     */
    @PostMapping("/api/login")
    public void login(@RequestBody TenantDTO tenant) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        tenant.email(),
                        tenant.password()
                );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /*
        ./manage/dev/http POST /api/signup email=user@example.com password=123
     */
    @PostMapping("/api/signup")
    public void signup(@RequestBody TenantDTO tenantDTO) {
        signupService.onboardTenant(tenantDTO);
    }

}
