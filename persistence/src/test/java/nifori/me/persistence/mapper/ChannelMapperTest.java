package nifori.me.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import nifori.me.persistence.nifobot.mapper.ChannelMapper;
import org.junit.jupiter.api.Test;

import nifori.me.domain.model.Channel;
import nifori.me.persistence.nifobot.entities.ChannelEntity;

public class ChannelMapperTest {

  private ChannelMapper mapper = new ChannelMapper();

  @Test
  public void testMapToJpa() {
    Channel channel = Channel.builder()
        .OID(123)
        .channelname("Testname")
        .serverOID(456L)
        .build();

    ChannelEntity entity = mapper.mapToJpa(channel);

    assertThat(entity.getOID()).isEqualTo(channel.getOID());
    assertThat(entity.getChannelname()).isEqualTo(channel.getChannelname());
    assertThat(entity.getServeroid()).isEqualTo(channel.getServerOID());
  }

  @Test
  public void testMapToDomain() {
    ChannelEntity entity = ChannelEntity.builder()
        .OID(123L)
        .channelname("Testname")
        .serveroid(456L)
        .build();

    Channel channel = mapper.mapToDomain(entity);

    assertThat(channel.getOID()).isEqualTo(entity.getOID());
    assertThat(channel.getChannelname()).isEqualTo(entity.getChannelname());
  }

}
