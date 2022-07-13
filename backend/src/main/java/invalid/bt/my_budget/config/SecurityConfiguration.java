package invalid.bt.my_budget.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// See the JDBC auth example here later:
//   https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
@Configuration
public class SecurityConfiguration {

    /*
    Keeping for ref:

        http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

    See:
        https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html

    Note that CSRF protection is enabled by default.
    The above snippets may allow a SPA to read the CSRF cookie with Javascript
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().and()
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/", "/demo", "/hello").permitAll()
                        .anyRequest().authenticated()
                )
//                .formLogin(withDefaults());
                .httpBasic(withDefaults())
                .build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/**/*.js", "/**/*.css", "/img/**");
    }

}
