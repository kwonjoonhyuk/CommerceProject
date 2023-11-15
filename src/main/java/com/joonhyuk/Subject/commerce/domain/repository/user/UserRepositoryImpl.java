package com.joonhyuk.Subject.commerce.domain.repository.user;

import com.joonhyuk.Subject.commerce.domain.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;
  QUser user = QUser.user;

  @Override
  public String findPasswordByEmail(String email) {
    return jpaQueryFactory.select(user.password)
        .from(user)
        .where(user.email.eq(email))
        .fetchOne();
  }

  @Override
  public String findPhoneByName(String name) {
    return jpaQueryFactory.select(user.phone)
        .from(user)
        .where(user.name.eq(name))
        .fetchOne();
  }

  @Override
  public Long findIdByEmail(String email) {
    return jpaQueryFactory.select(user.id)
        .from(user)
        .where(user.email.eq(email))
        .fetchOne();
  }

}
