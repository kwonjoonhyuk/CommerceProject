package com.joonhyuk.Subject.commerce.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.util.StringUtils;

public class JwtTokenProvider {

  //jwt access토큰 발급
  public String createToken(String loginEmail, String key, long expiredTimeMs) {
    // Claim = jwt token에 들어갈 정보
    // Claim에 LoginId를 넣어 줌으로써 나중에 LoginId를 꺼낼 수 있음
    Claims claims = Jwts.claims();
    claims.put("loginEmail", loginEmail);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
        .signWith(SignatureAlgorithm.HS256, key)
        .compact();
  }



  // Claims에서 loginId 꺼내기
  public String getLoginEmail(String token, String secretKey) {
    return extractClaims(token, secretKey).get("loginEmail").toString();
  }

  // 발급토큰 유효 확인
  public boolean validateToken(String token,String secretKey) {
    if (!StringUtils.hasText(token)) {
      return false;
    }
    Claims claims = this.extractClaims(token,secretKey);
    return claims.getExpiration().before(new Date());
  }

  // SecretKey를 사용해 Token Parsing
  private Claims extractClaims(String token, String secretKey) {
    try {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }

  }
}
