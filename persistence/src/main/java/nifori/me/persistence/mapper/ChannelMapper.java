package nifori.me.persistence.mapper;

import nifori.me.domain.model.Channel;
import nifori.me.persistence.entities.ChannelEntity;

public class ChannelMapper {

  public ChannelEntity mapToJpa(Channel channel) {
    return ChannelEntity.builder()
        .OID(channel.getOID())
        .channelname(channel.getChannelname())
        .serveroid(channel.getServerOID())
        .build();
  }

  public Channel mapToDomain(ChannelEntity channelEntity) {
    return Channel.builder()
        .OID(channelEntity.getOID())
        .channelname(channelEntity.getChannelname())
        .serverOID(channelEntity.getServeroid())
        .build();
  }
}
