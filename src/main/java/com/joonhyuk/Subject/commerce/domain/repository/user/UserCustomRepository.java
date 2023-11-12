package com.joonhyuk.Subject.commerce.domain.repository.user;

public interface UserCustomRepository {

  String findPasswordByEmail(String email);
  String findPhoneByName(String name);
  Long findIdByEmail(String email);

}
