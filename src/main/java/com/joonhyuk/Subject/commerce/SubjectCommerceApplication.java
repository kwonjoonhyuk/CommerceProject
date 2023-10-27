package com.joonhyuk.Subject.commerce;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ServletComponentScan
@RequiredArgsConstructor
@EnableJpaAuditing
@EnableFeignClients
@EnableJpaRepositories
@SpringBootApplication
public class SubjectCommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SubjectCommerceApplication.class, args);

  }

}
