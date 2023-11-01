package com.rental_web.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 경로에 접근 권한 설정
public class CustomSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // 680page
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       // http.formLogin().loginPage("member/login"); // 로그인이 필요한 경우에 ~경로로 자동 리다이렉트
        //위 주석 풀시 로그인 페이지로 이동......

        http.csrf().disable();  // username과 password라는 파라미터만으로 로그인 가능

        return http.build();

    }

    @Bean // 정적 자원들은 스프링 시큐리티에서 적용 제외(css등)
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web)  -> web.ignoring().requestMatchers(PathRequest.toStaticResources()
                                                        .atCommonLocations());
    }
}
