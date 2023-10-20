package com.redpig.config;

import com.redpig.common.Constant;
import com.redpig.security.auth.MyAuthorizationManager;
import com.redpig.security.filter.JwtAuthenticationTokenFilter;
import com.redpig.security.filter.LoginAuthenticationFilter;
import com.redpig.security.handler.LoginFailureHandler;
import com.redpig.security.handler.LoginSuccessHandler;
import com.redpig.security.handler.NoAuthAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    NoAuthAccessDeniedHandler noAuthAccessDeniedHandler;

    @Autowired
    private MyAuthorizationManager authorizationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(e->e.requestMatchers(Constant.annos).permitAll());
        http.authorizeHttpRequests(e->e.requestMatchers(RegexRequestMatcher.regexMatcher("^\\S*[css|js]$")).permitAll());
        http.authorizeHttpRequests(e->e.anyRequest().access(authorizationManager));

        //将JWT拦截器添加到UsernamePasswordAuthenticationFilter之前
        //登录之前验证token 从token中获取到登录凭证
        http.addFilterBefore(new JwtAuthenticationTokenFilter(),UsernamePasswordAuthenticationFilter.class);
        //重写登录
        http.addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.formLogin(e->e.successHandler(new LoginSuccessHandler()).failureHandler(new LoginFailureHandler()));
        http.exceptionHandling(e->e.accessDeniedHandler(noAuthAccessDeniedHandler));
        http.csrf(CsrfConfigurer::disable);
        http.cors(CorsConfigurer::disable);

        return http.build();
    }

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailureHandler());
        //LoginAuthenticationFilter 中需要使用到AuthenticationManager 不加会出现空指针
        filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
