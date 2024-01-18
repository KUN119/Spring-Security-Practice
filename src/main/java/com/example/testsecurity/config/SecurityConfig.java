package com.example.testsecurity.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Spring Security가 관리
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login").permitAll() // "/", "/login"페이지는 모든 사용자에게 오픈
                .requestMatchers("/admin").hasRole("ADMIN") // /admin페이지는 ADMIN에게 오픈
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // /my의 모든 페이지는 ADMIN,USER에게 오픈
                .anyRequest().authenticated() //anyRequest() 다른 요청들은, authenticated() 로그인된 유저에게 오픈
        );

        return http.build(); //빌드 타입으로 리턴
    }
}
