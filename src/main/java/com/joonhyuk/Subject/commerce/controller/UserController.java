package com.joonhyuk.Subject.commerce.controller;

import com.joonhyuk.Subject.commerce.domain.repository.user.UserCustomRepository;
import com.joonhyuk.Subject.commerce.domain.repository.user.UserRepository;
import com.joonhyuk.Subject.commerce.domain.user.UserDto;
import com.joonhyuk.Subject.commerce.domain.user.form.ChangePwForm;
import com.joonhyuk.Subject.commerce.domain.user.form.FindPasswordForm;
import com.joonhyuk.Subject.commerce.domain.user.form.FindUserIdForm;
import com.joonhyuk.Subject.commerce.domain.user.form.UpdateInfoForm;
import com.joonhyuk.Subject.commerce.domain.user.form.LoginForm;
import com.joonhyuk.Subject.commerce.domain.user.User;
import com.joonhyuk.Subject.commerce.service.UserService;
import com.joonhyuk.Subject.commerce.util.JwtTokenProvider;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisTemplate<String, String> redisTemplate;
  private final UserCustomRepository repository;

  // 유저 로그인
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginForm form) {
    User user = userService.login(form);

    String secretKey = "my-secret-key-123123";
    long expiredTimeMs = 1000 * 60 * 60 * 12; // access 토큰 유효 시간 = 12시간

    String token = jwtTokenProvider.createToken(user.getEmail(), secretKey, expiredTimeMs);
    redisTemplate.opsForValue().set("ACCESS_TOKEN:" + user.getEmail(), token);
    return ResponseEntity.ok(token);
  }

  // 유저 로그아웃
  @PostMapping(value = "/userlogout")
  public ResponseEntity<String> logout(Authentication auth) {
    userService.logout(auth.getName());
    return ResponseEntity.ok("로그아웃 되었습니다.");
  }

  // 유저 로그인 후 정보 조회
  @GetMapping("/info")
  public String getUserInfo(Authentication auth) {
    User user = userService.getLoginUserByLoginId(auth.getName());
    return String.format("로그인이메일 : %s\n 이름 : %s\n 회원구분 : %s",
        user.getEmail(), user.getName(), user.getRole());
  }

  // 유저 아이디 찾기
  @PostMapping("/find/userId")
  public ResponseEntity<String> findUserId(@RequestBody FindUserIdForm form) {
    String userId = userService.findId(form);
    return ResponseEntity.ok(form.getName() + "님의 가입하신 아이디(이메일)은 " + userId + " 입니다.");
  }

  // 유저 비밀번호 찾기 이메일 인증 링크 보내기
  @PostMapping("/find/userPassword")
  public ResponseEntity<String> findUserPassword(@RequestBody FindPasswordForm form) {
    return ResponseEntity.ok(userService.findPassword(form));
  }

  // 유저 비밀번호 찾기 이메일 인증하기
  @GetMapping("find/userPassword/verify")
  public ResponseEntity<String> verifyUserPassword(String email, String code) {
    return ResponseEntity.ok(userService.verifyEmail(email, code));
  }

  // 인증완료 후 비밀번호 변경
  @PutMapping("/find/changePassword")
  public ResponseEntity<String> changePassword(@RequestHeader(name = "VERIFY-KEY") String key,
      @RequestBody ChangePwForm form) {
    return ResponseEntity.ok(userService.changePw(form, key));
  }

  // 유저 정보 변경
  @PutMapping("/updateInfo")
  public ResponseEntity<UserDto> updateInfo(Authentication auth, @RequestBody @Valid UpdateInfoForm form) {

    Long userId = repository.findIdByEmail(auth.getName());
    return ResponseEntity.ok((UserDto.from(userService.updateInfo(userId, form))));
  }
}
