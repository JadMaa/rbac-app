package com.example.rbacapp.configurations;

import com.example.rbacapp.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    public void globaConfig(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(this.userAuthService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/clients/business").access("hasRole('ROLE_ROLE_BUSINESS')")
            .antMatchers("/clients/residential").access("hasRole('ROLE_RESIDENTIAL')")
            .antMatchers( "/","/css/**", "/js/**", "/img/**","/init/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/")
            .permitAll()
            .defaultSuccessUrl("/success")
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
    }

}
