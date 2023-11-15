package com.joonhyuk.Subject.commerce.config;

import com.joonhyuk.Subject.commerce.service.UserService;
import com.joonhyuk.Subject.commerce.util.JwtTokenFilter;
import com.joonhyuk.Subject.commerce.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserService userService;
  private static String secretKey = "my-secret-key-123123";
  private final JwtTokenProvider provider;
  private final RedisTemplate<String,String> redisTemplate;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(new JwtTokenFilter(userService,secretKey,provider,redisTemplate),
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }

}
