package com.joonhyuk.Subject.commerce.service;

import com.joonhyuk.Subject.commerce.client.MailgunClient;
import com.joonhyuk.Subject.commerce.client.mailgunApi.SendMailForm;
import com.joonhyuk.Subject.commerce.domain.repository.UserRepository;
import com.joonhyuk.Subject.commerce.domain.user.SignupForm;
import com.joonhyuk.Subject.commerce.domain.user.User;
import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

  private final MailgunClient mailgunClient;
  private final UserRepository userRepository;

  // 고객 , 판매자 회원가입 진행
  @Transactional
  public String signup(SignupForm form, String type) {
    // 1. 이메일, 닉네임, 휴대폰번호 중복확인
    if (userRepository.findByEmail(form.getEmail()).isPresent()) {
      throw new CustomException(ErrorCode.ALREADY_REGISTERED_EMAIL);
    }
    if (userRepository.findByNickname(form.getNick()).isPresent()) {
      throw new CustomException(ErrorCode.ALREADY_EXIST_NICKNAME);
    }
    if (userRepository.findByPhone(form.getPhone()).isPresent()) {
      throw new CustomException(ErrorCode.ALREADY_EXIST_PHONE);
    }
    // 2. 작성된 이메일로 인증코드 발송
    User user = saveUser(form, type);
    String code = getRandomCode();
    SendMailForm sendMailForm = SendMailForm.builder()
        .from("e-cummerce@CommerceNet.com")
        .to(user.getEmail())
        .subject("e-commerce 회원가입 이메일 인증")
        .text(getVerificationBody(user.getEmail(), user.getName(), type, code))
        .build();

    mailgunClient.sendEmail(sendMailForm);
    changeUserInfoValidateEmail(user.getId(), code);
    return "인증 메일이 발송되었습니다. 이메일을 확인해주세요";
  }

  public User saveUser(SignupForm form, String type) {
    return userRepository.save(User.from(form, type));
  }

  // 이메일 인증 코드 확인
  @Transactional
  public String verifyEmail(String email, String code) {

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

    /* 1. 이미 인증완료된 경우
       2. 발송 코드와 인증코드가 같지 않은 경우
       3. 인증만료기한이 넘어버린 경우
     */
    if (user.getVerify().equals("true")) {
      throw new CustomException(ErrorCode.ALREADY_VERIFY);
    }
    if (!user.getVerificationCode().equals(code)) {
      throw new CustomException(ErrorCode.WRONG_VERIFICATION);
    }
    if (user.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
      throw new CustomException(ErrorCode.EXPIRED_CODE);
    }
    user.setVerify("true");
    return "이메일 인증 완료되었습니다.";
  }

  // 회원가입 이메일 인증을 위한 랜덤코드 10자리 생성
  private String getRandomCode() {
    return RandomStringUtils.random(10, true, true);
  }

  // 회원가입 이메일 인증을 위한 url 생성
  private String getVerificationBody(String email, String name, String type, String code) {
    StringBuilder builder = new StringBuilder();
    return builder.append("반갑습니다!").append(name).append("님! 이메일 인증을 위해서 링크를 클릭해주세요.\n")
        .append("http://localhost:8080/signup/" + type + "/verify?email=")
        .append(email)
        .append("&code=")
        .append(code)
        .toString();
  }

  // 이메일 인증 링크 발송후 DB에 확인을 위한 인증코드 및 만료시간 저장
  private void changeUserInfoValidateEmail(Long userId, String verificationCode) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setVerificationCode(verificationCode);
      user.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
      return;
    }
    throw new CustomException(ErrorCode.NOT_FOUND_USER);
  }


}
