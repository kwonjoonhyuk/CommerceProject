package com.joonhyuk.Subject.commerce.domain.user;

import com.joonhyuk.Subject.commerce.domain.BaseEntity;
import com.joonhyuk.Subject.commerce.domain.user.form.SignupForm;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class User extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String name;
  private String nickname;
  private String password;
  private String phone;

  private String verify;
  private Integer balance;
  private String role;

  public static User from(SignupForm form, String type) {
    if (type.equals("customer")) {
      type = "고객";
    }else {
      type = "판매자";
    }
    return User.builder()
        .email(form.getEmail().toLowerCase(Locale.ROOT))
        .name(form.getName())
        .password(form.getPassword())
        .nickname(form.getNick())
        .phone(form.getPhone())
        .balance(0)
        .role(type)
        .verify("false")
        .build();
  }
}
