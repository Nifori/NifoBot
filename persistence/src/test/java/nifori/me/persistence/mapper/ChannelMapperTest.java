package nifori.me.persistence.mapper;

import nifori.me.domain.model.Channel;
import nifori.me.persistence.entities.ChannelEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChannelMapperTest {

  private ChannelMapper mapper = new ChannelMapper();

  @Test
  public void testMapToJpa() {
    Channel channel = Channel.builder()
        .OID(123)
        .channelname("Testname")
        .build();

    ChannelEntity entity = mapper.mapToJpa(channel);

    assertThat(entity.getOID()).isEqualTo(channel.getOID());
    assertThat(entity.getChannelname()).isEqualTo(channel.getChannelname());
  }

  @Test
  public void testMapToDomain() {
    ChannelEntity entity = ChannelEntity.builder()
        .OID(Long.valueOf(123))
        .channelname("Testname")
        .build();

    Channel channel = mapper.mapToDomain(entity);

    assertThat(channel.getOID()).isEqualTo(entity.getOID());
    assertThat(channel.getChannelname()).isEqualTo(entity.getChannelname());
  }

}
