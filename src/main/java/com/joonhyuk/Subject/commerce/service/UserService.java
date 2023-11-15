package com.joonhyuk.Subject.commerce.service;

import static com.joonhyuk.Subject.commerce.domain.repository.user.AuthenticationTokenRepository.expiredTimeMs;
import static com.joonhyuk.Subject.commerce.domain.repository.user.AuthenticationTokenRepository.secretKey;
import static com.joonhyuk.Subject.commerce.exception.ErrorCode.NAME_DO_NOT_MATCH;
import static com.joonhyuk.Subject.commerce.exception.ErrorCode.NOT_FOUND_USER;
import static com.joonhyuk.Subject.commerce.exception.ErrorCode.PASSWORD_DO_NOT_MATCH;
import static com.joonhyuk.Subject.commerce.exception.ErrorCode.PHONE_DO_NOT_MATCH;
import static com.joonhyuk.Subject.commerce.exception.ErrorCode.PLEASE_NEW_PW;

import com.joonhyuk.Subject.commerce.components.MailComponents;
import com.joonhyuk.Subject.commerce.domain.repository.user.AuthenticationTokenRepository;
import com.joonhyuk.Subject.commerce.domain.repository.user.UserCustomRepository;
import com.joonhyuk.Subject.commerce.domain.repository.user.UserRepository;
import com.joonhyuk.Subject.commerce.domain.user.form.ChangePwForm;
import com.joonhyuk.Subject.commerce.domain.user.form.FindPasswordForm;
import com.joonhyuk.Subject.commerce.domain.user.form.FindUserIdForm;
import com.joonhyuk.Subject.commerce.domain.user.form.LoginForm;
import com.joonhyuk.Subject.commerce.domain.user.User;
import com.joonhyuk.Subject.commerce.domain.user.form.UpdateInfoForm;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final MailComponents mailComponents;
  private final UserRepository userRepository;
  private final UserCustomRepository customRepository;
  private final RedisTemplate<String, String> redisTemplate;
  private final AuthenticationTokenRepository tokenRepository;

  // 유저 정보 조회
  public User getLoginUserByLoginId(String loginEmail) {
    return userRepository.findUserByEmail(loginEmail);
  }

  // 유저 로그인
  public String login(LoginForm form) {
    if (userRepository.findByEmail(form.getEmail()).isEmpty()) {
      throw new CustomException(ErrorCode.NOT_FOUND_USER);
    }
    if (!customRepository.findPasswordByEmail(form.getEmail()).equals(form.getPassword())) {
      throw new CustomException(PASSWORD_DO_NOT_MATCH);
    }
    String token = tokenRepository.createToken(form.getEmail(), secretKey, expiredTimeMs);
    redisTemplate.opsForValue().set("ACCESS_TOKEN:" + form.getEmail(), token);
    redisTemplate.expire("ACCESS_TOKEN:" + form.getEmail(), 6, TimeUnit.HOURS);
    return token;
  }

  // 유저 로그아웃
  public void logout(String loginEmail) {
    redisTemplate.opsForValue().set("ACCESS_TOKEN:" + loginEmail, "logout");
  }

  // 유저 아이디 찾기
  public String findId(FindUserIdForm form) {
    if (userRepository.findByName(form.getName()).isEmpty()) {
      throw new CustomException(ErrorCode.NOT_FOUND_USER);
    }
    if (!customRepository.findPhoneByName(form.getName()).equals(form.getPhone())) {
      throw new CustomException(PHONE_DO_NOT_MATCH);
    }
    Optional<User> user = userRepository.findByPhone(form.getPhone());

    return user.get().getEmail();

  }

  // 유저 비밀번호 찾기 이메일 인증 메일 보내기
  public String findPassword(FindPasswordForm form) {
    if (userRepository.findByEmail(form.getEmail()).isEmpty()) {
      throw new CustomException(NOT_FOUND_USER);
    }
    User user = userRepository.findUserByEmail(form.getEmail());
    if (!user.getName().equals(form.getName())) {
      throw new CustomException(NAME_DO_NOT_MATCH);
    }
    String randomCode = getRandomCode();

    mailComponents.sendVerifyMail(user.getEmail(),
        getVerificationBody(user.getEmail(), user.getName(), randomCode));
    findPasswordRedis(user.getId(), randomCode);
    return "가입하신 이메일(아이디)로 비밀번호 변경 메일을 보냈습니다. 확인해주세요";
  }

  // 유저 비밀번호 찾기 이메일 인증
  public String verifyEmail(String email, String code) {
 /*
       1. 발송 코드와 인증코드가 같지 않은 경우
       2. 인증만료기한이 넘어버린 경우
     */

    if (userRepository.findByEmail(email).isEmpty()) {
      throw new CustomException(NOT_FOUND_USER);
    }
    if (redisTemplate.opsForValue().get("VERIFY_PASSWORD:" + email) == null) {
      throw new CustomException(ErrorCode.RESTART_VERIFY_CHANGE_PW);
    }
    if (!redisTemplate.opsForValue().get("VERIFY_PASSWORD:" + email).equals(code)) {
      throw new CustomException(ErrorCode.RESTART_VERIFY_CHANGE_PW);
    }
    redisTemplate.opsForValue().set("VERIFY_PASSWORD:" + email, email);
    return "이메일 인증 완료되었습니다. 새로운 비밀번호로 변경해주세요";
  }

  // 비밀 번호 변경
  @Transactional
  public String changePassword(ChangePwForm form, String key) {

    User user = userRepository.findUserByEmail(redisTemplate.opsForValue().get(key));

    if (!form.getPassword().equals(form.getRePassword())) {
      throw new CustomException(PASSWORD_DO_NOT_MATCH);
    }
    if (user.getPassword().equals(form.getPassword())) {
      throw new CustomException(PLEASE_NEW_PW);
    }
    user.setPassword(form.getPassword());
    return "비밀번호가 변경되었습니다. 변경된 비밀번호로 로그인해주세요";
  }

  // 인증번호 랜덤코드 10자리 생성
  private String getRandomCode() {
    return RandomStringUtils.random(10, true, true);
  }

  // 비밀번호 찾기를 위한 이메일 인증 링크 생성
  private String getVerificationBody(String email, String name, String code) {
    StringBuilder builder = new StringBuilder();
    return builder.append("반갑습니다!").append(name).append("님! 비밀번호 변경을 위해 인증 링크를 클릭해주세요.\n")
        .append("http://localhost:8080/find/userPassword/verify?email=")
        .append(email)
        .append("&code=")
        .append(code)
        .toString();
  }

  // 비밀번호 찾기 redis에 인증 번호 저장 후 자동 삭제 시간 설정(1시간)
  private void findPasswordRedis(Long userId, String verificationCode) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
    redisTemplate.opsForValue().set("VERIFY_PASSWORD:" + user.getEmail(), verificationCode);
    redisTemplate.expire("VERIFY_PASSWORD:" + user.getEmail(), 1, TimeUnit.HOURS);
  }

  // 유저 정보 변경
  @Transactional
  public User updateInfo(Long userId, UpdateInfoForm form) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    if (user.getNickname().equals(form.getNickname())) {
      throw new CustomException(ErrorCode.PLEASE_NEW_NICKNAME);
    }
    if (user.getPhone().equals(form.getPhone())) {
      throw new CustomException(ErrorCode.PLEASE_NEW_PHONE);
    }
    if (user.getPassword().equals(form.getPassword())) {
      throw new CustomException(PLEASE_NEW_PW);
    }
    user.setNickname(form.getNickname());
    user.setPhone(form.getPhone());
    user.setPassword(form.getPassword());

    return user;
  }
}
