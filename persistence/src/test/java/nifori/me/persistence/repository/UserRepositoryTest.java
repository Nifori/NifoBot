package nifori.me.persistence.repository;

import static nifori.me.persistence.TestConstants.CHANNELNAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nifori.me.persistence.nifobot.entities.UserEntity;
import nifori.me.persistence.nifobot.repositories.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  private final AtomicLong useroid = new AtomicLong();

  @Test
  public void testStoreOneEntity() {
    Assertions.assertDoesNotThrow(() -> userRepository.save(getEntity(CHANNELNAME)));
  }

  @Test
  public void testEntityIdIncreases() {
    UserEntity firstEntity = userRepository.save(getEntity(CHANNELNAME));
    UserEntity secondEntity = userRepository.save(getEntity("Second_Testuser"));

    assertThat(userRepository.count()).isEqualTo(2);
  }

  private UserEntity getEntity(String username) {
    return UserEntity.builder()
        .OID(useroid.incrementAndGet())
        .name(username)
        .build();
  }
}
