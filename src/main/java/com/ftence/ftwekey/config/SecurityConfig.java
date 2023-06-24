package com.ftence.ftwekey.config;

import com.ftence.ftwekey.config.jwt.JwtAuthenticationFilter;
import com.ftence.ftwekey.config.jwt.JwtUtil;
import com.ftence.ftwekey.config.oauth.CustomOAuth2UserService;
import com.ftence.ftwekey.constant.ErrorMessage;
import com.ftence.ftwekey.exception.login.JwtExceptionFilter;
import com.ftence.ftwekey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Value("${front.uri}")
    private String frontUri;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userRepository), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_GOD')")
                .antMatchers("/subject/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_GOD')")
                .antMatchers("/comment/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_GOD')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_GOD')")
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        http
                .oauth2Login()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        log.info("oauth2 로그인 성공 {}", authentication.getName());
                        String jwtToken = jwtUtil.makeAuthToken(authentication);

                        response.sendRedirect(frontUri + "/?token=" + jwtToken);
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {

                        log.error("oauth2 로그인 실패 {}", exception.getMessage());
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.UNAUTHORIZED);
                    }
                })
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
}
