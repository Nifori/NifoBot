package nifori.me.persistence.repository;

import static nifori.me.persistence.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import nifori.me.persistence.nifobot.entities.WarningEntity;
import nifori.me.persistence.nifobot.repositories.WarningRepository;

@SpringBootTest
public class WarningRepositoryTest {

  @Autowired
  WarningRepository warningRepository;

  private final AtomicLong warningoid = new AtomicLong();

  @Test
  public void testStoreOneEntity() {
    Assertions.assertDoesNotThrow(() -> warningRepository.save(getEntity(DEFAULT_MESSAGE)));
  }

  @Test
  public void testStoreMultipleEntities() {
    WarningEntity firstEntity = warningRepository.save(getEntity(DEFAULT_MESSAGE));
    WarningEntity secondEntity = warningRepository.save(getEntity(DEFAULT_MESSAGE));

    assertThat(warningRepository.count()).isEqualTo(2);
  }

  @Test
  public void testCountWarnings() {
    int expectedCount = 3;
    for (int i = 0; i < expectedCount; i++) {
      warningRepository.save(getEntity(DEFAULT_MESSAGE));
    }

    assertThat(warningRepository.countByServerIdAndUserId(SERVER_OID, USER_OID)).isEqualTo(expectedCount);
    assertThat(warningRepository.countByServerIdAndUserId(SERVER_OID, 2)).isEqualTo(0);
    assertThat(warningRepository.countByServerIdAndUserId(2, USER_OID)).isEqualTo(0);
  }

  @Test
  @Transactional
  public void testGetMessages() {

    warningRepository.save(getEntity("Test Message 1"));
    warningRepository.save(getEntity("Test Message 2"));
    warningRepository.save(getEntity("Test Message 3"));

    assertThat(warningRepository.findAllByOID(SERVER_OID, USER_OID)).hasSize(3);
    assertThat(warningRepository.findAllByOID(SERVER_OID, USER_OID)
        .map(WarningEntity::getMessage))
            .containsAll(Arrays.asList("Test Message 1", "Test Message 2", "Test Message 3"));
  }

  private WarningEntity getEntity(String message) {
    return WarningEntity.builder()
        .OID(warningoid.incrementAndGet())
        .message(message)
        .useroid(USER_OID)
        .serveroid(SERVER_OID)
        .build();
  }
}
