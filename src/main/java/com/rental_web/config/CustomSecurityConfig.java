package com.rental_web.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Log4j2
@Configuration  // 비밀번호 암호화
@EnableGlobalMethodSecurity(prePostEnabled = true) //로그인한 사용자만 접근 권한 설정
public class CustomSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {

//        http.formLogin(form -> {
//            form.loginPage("/member/login")
//                    .defaultSuccessUrl("/")
//                    .failureUrl("/member/login");
//        });
        http.formLogin((formLogin) -> formLogin
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/introduce/introduce"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true))
        ;

        // SecurityContextHolder.getContext().setAuthentication(Authentication);

        http.csrf(httpSecurityCsrfConfigurer ->  httpSecurityCsrfConfigurer.disable() );
        //http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout"));

        http.oauth2Login().loginPage("/member/login");

        return http.build();
    }

    @Bean   // 정적 자원들은 시큐리티 제외
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("------------web configure-------------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }

}
