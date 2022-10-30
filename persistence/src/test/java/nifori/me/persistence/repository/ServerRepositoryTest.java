package nifori.me.persistence.repository;

import static nifori.me.persistence.TestConstants.CHANNELNAME;
import static nifori.me.persistence.TestConstants.SERVERNAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nifori.me.persistence.nifobot.entities.ChannelEntity;
import nifori.me.persistence.nifobot.entities.ServerEntity;
import nifori.me.persistence.nifobot.repositories.ServerRepository;

@SpringBootTest
public class ServerRepositoryTest {

  @Autowired
  ServerRepository serverRepository;

  private final AtomicLong serveroid = new AtomicLong();
  private final AtomicLong channeloid = new AtomicLong();

  @Test
  public void testStoreOneEntity() {
    Assertions.assertDoesNotThrow(() -> serverRepository.save(getEntity(SERVERNAME)));
  }

  @Test
  public void testStoredEntityHasId() {
    ServerEntity storedEntity = serverRepository.save(getEntity(SERVERNAME));
    assertThat(storedEntity).isNotNull();
    assertThat(storedEntity.getOID()).isNotNull();
  }

  @Test
  public void testEntityIdIncreases() {
    ServerEntity firstEntity = serverRepository.save(getEntity(SERVERNAME));
    ServerEntity secondEntity = serverRepository.save(getEntity("Second_Testserver"));

    assertThat(serverRepository.count()).isEqualTo(2);
    assertThat(firstEntity.getOID()).isLessThan(secondEntity.getOID());
  }

  private ServerEntity getEntity(String servername) {
    ChannelEntity channel = ChannelEntity.builder()
        .OID(channeloid.incrementAndGet())
        .channelname(CHANNELNAME)
        .build();
    return ServerEntity.builder()
        .OID(serveroid.incrementAndGet())
        .servername(servername)
        .build();
  }
}
