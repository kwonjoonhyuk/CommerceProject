package com.joonhyuk.Subject.commerce.service;

import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LockService {

  private final RedissonClient redissonClient;

  public void Lock(Long optionId) {
    RLock lock = redissonClient.getLock("ACLK" + optionId);
    log.info("락걸림" + optionId);
    try {
      boolean isLock = lock.tryLock(1, 15, TimeUnit.SECONDS);
      if (!isLock) {
        log.error("================Lock acquisition failed================");
        throw new CustomException(ErrorCode.OPTION_LOCK);
      }

    } catch (CustomException e) {
      throw e;
    } catch (Exception e) {
      log.error("레디스 락 실패");
    }
  }

  public void unLock(Long optionId) {
    log.info("락 해제" + optionId);
    redissonClient.getLock(getLockKey(optionId)).unlock();
  }

  private String getLockKey(Long optionId) {
    return "ACLK" + optionId;
  }
}
