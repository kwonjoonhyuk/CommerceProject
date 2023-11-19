package com.joonhyuk.Subject.commerce.domain.repository.user;

import com.joonhyuk.Subject.commerce.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AuthenticationTokenRepository {

  public static String secretKey = "my-secret-key-123123";
  public static long expiredTimeMs = 1000 * 60 * 60 * 12; // access 토큰 유효 시간 = 12시간
  private final JwtTokenProvider jwtTokenProvider;


  public String createToken(String email, String secretKey, long expiredTimeMs) {
    return jwtTokenProvider.createToken(email, secretKey, expiredTimeMs);
  }
}
