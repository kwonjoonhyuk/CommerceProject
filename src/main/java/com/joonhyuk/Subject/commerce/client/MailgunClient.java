package com.joonhyuk.Subject.commerce.client;

import com.joonhyuk.Subject.commerce.client.mailgunApi.SendMailForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
public interface MailgunClient {

  @PostMapping("sandboxd3dad8ba4f7e45bfaf1a6d5b2034e9a7.mailgun.org/messages")
  ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);
}
