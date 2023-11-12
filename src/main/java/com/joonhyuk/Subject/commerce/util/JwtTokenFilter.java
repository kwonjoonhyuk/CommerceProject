package com.joonhyuk.Subject.commerce.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joonhyuk.Subject.commerce.domain.user.User;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import com.joonhyuk.Subject.commerce.exception.MessageDto;
import com.joonhyuk.Subject.commerce.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
public class JwtTokenFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final String secretKey;
  private final JwtTokenProvider provider;
  private final RedisTemplate<String, String> redisTemplate;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
    if (authorizationHeader == null) {

      if (request.getRequestURI().equals("/login")) {
        filterChain.doFilter(request, response);
      }

      if (request.getRequestURI().startsWith("/signup")){
        filterChain.doFilter(request, response);
      }
      if (request.getRequestURI().startsWith("/find")){
        filterChain.doFilter(request, response);
      }

      jwtExceptionHandler(response, new CustomException(ErrorCode.NOT_LOGIN_TOKEN));
      return;
    }
    // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
    if (!authorizationHeader.startsWith("Bearer ")) {
      jwtExceptionHandler(response, new CustomException(ErrorCode.TOKEN_INCORRECT));
      return;
    }

    // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
    String token = authorizationHeader.split(" ")[1];

    // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
    if (provider.validateToken(token, secretKey)) {
      jwtExceptionHandler(response, new CustomException(ErrorCode.TOKEN_EXPIRED));
      return;
    }

    // Jwt Token에서 loginId 추출
    String loginEmail = provider.getLoginEmail(token, secretKey);

    // 전송받은 jwt 토큰이 redis에 저장되어 있지않다면 로그아웃된것 => 로그인 재 요청
    if (redisTemplate.opsForValue().get("ACCESS_TOKEN:"+loginEmail).equals("logout")) {
      jwtExceptionHandler(response, new CustomException(ErrorCode.ALREADY_LOGOUT_USER));
      return;
    }

    // 추출한 loginId로 User 찾아오기
    User loginUser = userService.getLoginUserByLoginId(loginEmail);

    // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginUser.getEmail(), null,
        List.of(new SimpleGrantedAuthority(loginUser.getRole())));
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    // 권한 부여
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    filterChain.doFilter(request, response);
  }

  public void jwtExceptionHandler(HttpServletResponse response, CustomException errorCode) {
    response.setStatus(errorCode.getStatus());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    try {
      String json = new ObjectMapper().writeValueAsString(
          MessageDto.of(errorCode.getStatus(), errorCode.getMessage()));
      response.getWriter().write(json);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
