package com.joonhyuk.Subject.commerce.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailComponents {

  private final JavaMailSender javaMailSender;

  public void sendVerifyMail(String email, String url) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("e-cummerce@CommerceNet.com");
    message.setTo(email);
    message.setSubject("안녕하세요. 이커머스 인증 메일입니다.");
    message.setText(url);
    javaMailSender.send(message);
  }
}
