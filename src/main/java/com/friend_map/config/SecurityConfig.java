package com.friend_map.config;


import com.friend_map.business_layer.auth.PasswordEncoderService;
import com.friend_map.components.beans.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoderService passwordEncoder;

    @Autowired
    PersistentTokenRepository persistentTokenRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/**", "/assets/**").permitAll()
                .antMatchers("/register**").anonymous()
                .anyRequest().permitAll();

        http.formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .failureUrl("/status")
                .defaultSuccessUrl("/status")
                .usernameParameter("username")
                .passwordParameter("password");

        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/status")
                .invalidateHttpSession(true);

        http.csrf()
                .disable()
                .rememberMe().tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(86400000).key("remember-me");

        http.exceptionHandling().accessDeniedPage("/403");

    }

    LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/auth");

    public PasswordEncoder passwordEncoder() {
        return passwordEncoder.passwordEncoder();
    }

}
