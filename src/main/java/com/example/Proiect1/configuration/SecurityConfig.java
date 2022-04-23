package com.example.Proiect1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("h2")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .and()
                .withUser("guest")
                .password(passwordEncoder().encode("1234"))
                .roles("GUEST");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated()
        http.authorizeRequests()
                .antMatchers("/h2-console/login.do*").permitAll()
                .antMatchers("/listener/**").hasAnyRole("GUEST","ADMIN")
                .antMatchers("/song/list").hasAnyRole("GUEST","ADMIN")
                .antMatchers("/song/info/*").hasAnyRole("GUEST","ADMIN")
                .antMatchers("/song/update/*").hasRole("ADMIN")
                .antMatchers("/song/update").hasRole("ADMIN")
                .antMatchers("/song/delete/*").hasRole("ADMIN")
                .antMatchers("/song/add").hasRole("ADMIN")
                .antMatchers("/song/save").hasRole("ADMIN")
                .antMatchers("/artist/list").hasAnyRole("GUEST","ADMIN")
                .antMatchers("/artist/info/*").hasAnyRole("GUEST","ADMIN")
                .antMatchers("/artist/update/*").hasRole("ADMIN")
                .antMatchers("/artist/update").hasRole("ADMIN")
                .antMatchers("/artist/delete/*").hasRole("ADMIN")
                .antMatchers("/artist/add").hasRole("ADMIN")
                .antMatchers("/artist/save").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/showLogInForm")
                .loginProcessingUrl("/authUser")
                .failureUrl("/login-error").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");

//        http.headers().frameOptions().sameOrigin();

    }

}
