package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Spring Security가 관리
public class SecurityConfig {

    //암호화를 진행하는 메서드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 권한 계층 메서드:
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ADMIN > USER"); // ADMIN은 USER의 역할을 전부 수행할 수 있기 때문에

        return hierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //스프링 부트 3.1.X 버전부터는 람다형식 표현 필수: (auth) -> auth
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/join", "/joinProc").permitAll() // permitAll() 모든 사용자에게 오픈
                .requestMatchers("/admin").hasRole("ADMIN") // /admin페이지는 ADMIN에게 오픈
                .requestMatchers("/my/**").hasAnyRole("USER") // 권한계층 설정해줬기 때문에 USER만 적어놓아도 ADMIN도 권한 자동 부여
                .anyRequest().authenticated() //anyRequest() 다른 요청들은, authenticated() 로그인된 유저에게 오픈
        );

        http.formLogin((auth) -> auth
                .loginProcessingUrl("/loginProc") //html form태그에서 action 경로 설정 로그인 데이터를 넘기면 시큐리티가 로그인 처리
                .permitAll()
        );

        //csrf(Cross Site Request Forgery): 요청을 위조하여 사용자가 원하지 않아도
        //서버측으로 특정 요청을 강제로 보내는 방식 Ex.회원 정보 변경, 게시글 CRUD를 사용자 몰래 요청)
        //Spring Security는 default로 csrf가 enable임
        //http.csrf((auth) -> auth.disable());

        //다중 로그인
        http.sessionManagement((auth) -> auth
                .maximumSessions(1) //하나의 아이디에 대한 다중 로그인 허용 개수
                .maxSessionsPreventsLogin(true) // true 새로운 로그인 차단, flase: 초과시 기존 세션 하나 삭제
        );

        //세션 고정 보호
        http.sessionManagement((auth) -> auth
                .sessionFixation().changeSessionId()); //로그인 시 동일한 세션에 대한 id 변경

        //로그아웃
        http.logout((auth) -> auth
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        return http.build(); //빌드 타입으로 리턴
    }
}
