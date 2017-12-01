package com.gti619.configurations;

import com.gti619.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    public void globaConfig(AuthenticationManagerBuilder auth) throws Exception{
        //This test if the user entered the good authInfo
        auth.userDetailsService(this.userAuthService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                //This is the public ressources
            .antMatchers( "/","/css/**", "/js/**", "/img/**","/init/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
                // this is the path of our login page
            .loginPage("/")
            .permitAll()
                //When successful login, we are redirected to the /success. This endpoint refirect to the good one for each role
                // - see SuccessRedirect.java
            .defaultSuccessUrl("/success")
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
    }

}
