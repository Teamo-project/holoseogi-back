package com.holoseogi.holoseogi.config;

import com.holoseogi.holoseogi.security.handler.OAuth2AuthenticationFailureHandler;
import com.holoseogi.holoseogi.security.handler.OAuth2AuthenticationSuccessHandler;
import com.holoseogi.holoseogi.security.handler.JwtAccessDeniedHandler;
import com.holoseogi.holoseogi.security.jwt.JwtAuthenticationEntryPoint;
import com.holoseogi.holoseogi.filter.JwtAuthenticationFilter;
import com.holoseogi.holoseogi.security.oauth.CookieAuthorizationRequestRepository;
import com.holoseogi.holoseogi.security.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler authenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler authenticationFailureHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/oauth2/**", "/auth/**").permitAll() // 로그인, refresh
                .anyRequest().permitAll();

        http.cors()                     // CORS on
                .and()
                .csrf().disable()           // CSRF off
                .httpBasic().disable()      // Basic Auth off
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    // Session off

        http.formLogin().disable()
                .oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(cookieAuthorizationRequestRepository)// cookie사용
                .and()
                .redirectionEndpoint() // 인증이 끝나면 authorizaiton code와 redirect
                    .baseUri("/oauth2/callback/*") // 기본 login/oauth2/code/{provider}
                .and()
                .userInfoEndpoint() // 유저 정보 다룰 service class 지정
                    .userService(customOAuth2UserService)
                .and()
                .successHandler(authenticationSuccessHandler) // oauth2 로그인 성공 시 호출
                .failureHandler(authenticationFailureHandler); // oauth2 로그인 실패 시 호출

        http.exceptionHandling() // exception 처리할 클래스
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)	// 401
                .accessDeniedHandler(jwtAccessDeniedHandler);		// 403

        // 모든 request에서 JWT 검사할 filter추가
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}