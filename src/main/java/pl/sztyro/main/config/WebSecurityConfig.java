package pl.sztyro.main.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger _logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                //.antMatchers("/api/**").authenticated()
                .antMatchers("/login").permitAll()


                .and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> {

            _logger.warn(authException.getMessage());


            if (authException != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                writer.print("<!DOCTYPE html>");
                writer.print("<html>");
                writer.print("<body>");
                writer.print("<head>");
                writer.print("<title>Title of the document</title>");
                writer.print("</head>");
                writer.print("<a href=\"/login\">Login page</a>");
                writer.print("</body>");
                writer.print("</html>");
                //response.sendRedirect("/api/google/auth");
                //response.sendRedirect("/login");

            }
        })

                .and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        //.httpBasic().disable();


    }

}

