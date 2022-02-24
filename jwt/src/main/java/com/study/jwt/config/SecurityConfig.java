package com.study.jwt.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 추가적인 설정을 위한 방법
 1. WebSecurityConfigurer 상속
 2. WebSecurityConfigurerAdapter 상속
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**"
                        , "/favicon.ico"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()                                // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                .antMatchers("/api/hello").permitAll()   // 해당 API에 대한 요청은 인증 없이 접근 허용
                .anyRequest().authenticated();                      // 나머지 요청들은 모두 인증되어야 함
    }
}