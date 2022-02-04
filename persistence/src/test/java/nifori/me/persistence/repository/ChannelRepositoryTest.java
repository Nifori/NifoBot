package nifori.me.persistence.repository;

import static nifori.me.persistence.TestConstants.CHANNELNAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import nifori.me.persistence.entities.ChannelEntity;

@DataJpaTest
public class ChannelRepositoryTest {

  @Autowired
  ChannelRepository channelRepository;

  private final AtomicLong channeloid = new AtomicLong();

  @Test
  public void testStoreOneEntity() {
    Assertions.assertDoesNotThrow(() -> channelRepository.save(getEntity(CHANNELNAME)));
  }

  @Test
  public void testStoredEntityHasId() {
    ChannelEntity storedEntity = channelRepository.save(getEntity(CHANNELNAME));
    assertThat(storedEntity).isNotNull();
    assertThat(storedEntity.getOID()).isNotNull();
  }

  @Test
  public void testEntityIdIncreases() {
    ChannelEntity firstEntity = channelRepository.save(getEntity(CHANNELNAME));
    ChannelEntity secondEntity = channelRepository.save(getEntity("Second_Testchannel"));

    assertThat(channelRepository.count()).isEqualTo(2);
    assertThat(firstEntity.getOID()).isLessThan(secondEntity.getOID());
  }

  private ChannelEntity getEntity(String channelname) {
    return ChannelEntity.builder()
        .OID(channeloid.incrementAndGet())
        .channelname(channelname)
        .build();
  }
}
