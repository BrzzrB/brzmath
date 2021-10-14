package by.brzmath.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                    .antMatchers("/login","/error","/resources/**","/", "/task/**").permitAll()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/img/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/MyAccount")
                    .failureUrl("/")
                    .and()
                .formLogin().disable()
                .logout();
    }
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

}
