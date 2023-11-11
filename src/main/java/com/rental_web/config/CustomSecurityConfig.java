package com.rental_web.config;

import com.rental_web.security.handler.Custom403Handler;
import com.rental_web.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Log4j2
@Configuration  // 비밀번호 암호화
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //로그인한 사용자만 접근 권한 설정
public class CustomSecurityConfig {

    private final DataSource dataSource;

    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {


        http.formLogin((formLogin) -> formLogin
                        .loginPage("/member/login"));

        http.cors();


        // SecurityContextHolder.getContext().setAuthentication(Authentication);

        // csrf 토큰 비활성화
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        // 세션을 사용하지 않음
//        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
//            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        });

        //http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout"));


        // 자동 로그인
        http.rememberMe(httpSecurityRememberMeConfigurer -> {

            httpSecurityRememberMeConfigurer.key("12345678")
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(userDetailsService)
                    .tokenValiditySeconds(60*60*24*30);

        });

//        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
//
//            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler());
//        });



        http.oauth2Login()
                .loginPage("/member/login")
                .successHandler(authenticationSuccessHandler());

        return http.build();
    }

    @Bean   // 정적 자원들은 시큐리티 제외
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("------------web configure-------------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }

//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//
//        return new Custom403Handler();
//    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

}
