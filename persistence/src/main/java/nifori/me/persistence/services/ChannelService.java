package nifori.me.persistence.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.Channel;
import nifori.me.persistence.entities.ChannelEntity;
import nifori.me.persistence.mapper.ChannelMapper;
import nifori.me.persistence.repository.ChannelRepository;

@Component
public class ChannelService {

  @Autowired
  private ChannelRepository channelRepository;

  private ChannelMapper mapper = new ChannelMapper();

  public Optional<Channel> getPortObservationById(long id) {
    Optional<ChannelEntity> channelEntity = channelRepository.findByOID(id);
    return channelEntity.map(mapper::mapToDomain);
  }

  @Transactional
  public Channel saveChannel(Channel channel) {
    ChannelEntity channelEntity = channelRepository.save(mapper.mapToJpa(channel));
    return mapper.mapToDomain(channelEntity);
  }

}
