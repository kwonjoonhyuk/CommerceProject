package com.joonhyuk.Subject.commerce.config;

import com.joonhyuk.Subject.commerce.util.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// JWT Bean 등록
@Configuration
public class JwtConfig {

  @Bean
  public JwtTokenProvider jwtAuthenticationProvider() {
    return new JwtTokenProvider();
  }
}
