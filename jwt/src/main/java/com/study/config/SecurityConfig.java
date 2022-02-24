package com.study.config;

import com.study.jwt.JwtAccessDeniedHandler;
import com.study.jwt.JwtAuthenticationEntryPoint;
import com.study.jwt.JwtSecurityConfig;
import com.study.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 추가적인 설정을 위한 방법
 1. WebSecurityConfigurer 상속
 2. WebSecurityConfigurerAdapter 상속
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize Annotation을 메소드 단위로 사용하기 위함
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
                .csrf().disable()                                       // CSRF(사이트 간 요청 위조) 비활성화 => 토큰을 사용하기 때문

                .exceptionHandling()                                    // 예외 처리 시 커스텀 클래스를 사용하도록 등록
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()                                                  // H2-CONSOLE 사용을 위한 설정 추가
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않으므로, `STATELESS`로 설정

                .and()
                .authorizeRequests()                                    // `HttpServletRequest`를 사용하는 요청들에 대한 접근제한을 설정
                .antMatchers("/api/hello").permitAll()       // 해당 API(INDEX, 로그인, 회원가입)에 대한 요청은 인증 없이 접근 허용
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/signup").permitAll()

                .anyRequest().authenticated()                           // 나머지 요청들은 모두 인증되어야 함

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));           // `JwtFilter`를 `addFilterBefore`로 등록했던 `JwtSecurityConfig` 클래스 적용
    }
}