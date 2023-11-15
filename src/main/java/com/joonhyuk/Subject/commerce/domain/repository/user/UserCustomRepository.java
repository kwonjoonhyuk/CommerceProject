package com.joonhyuk.Subject.commerce.domain.repository.user;

import com.joonhyuk.Subject.commerce.domain.user.User;

public interface UserCustomRepository {

  String findPasswordByEmail(String email);
  String findPhoneByName(String name);
  Long findIdByEmail(String email);
  User findById(Long id);

}
