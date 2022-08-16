package nifori.me.persistence.services;

import java.util.Optional;

import javax.transaction.Transactional;

import nifori.me.domain.model.Channel;
import nifori.me.persistence.entities.ChannelEntity;
import nifori.me.persistence.mapper.ChannelMapper;
import nifori.me.persistence.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelService {

  @Autowired
  private ChannelRepository channelRepository;

  private ChannelMapper mapper = new ChannelMapper();

  public Optional<Channel> getPortObservationById(long id) {
    Optional<ChannelEntity> portObservEntity = channelRepository.findByOID(id);
    return portObservEntity.map(mapper::mapToDomain);
  }

  @Transactional
  public Channel saveChannel(Channel warportObservationing) {
    ChannelEntity portObservEntity = channelRepository.save(mapper.mapToJpa(warportObservationing));
    return mapper.mapToDomain(portObservEntity);
  }

}
