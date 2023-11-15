package nifori.me.persistence.nifobot.mapper;

import nifori.me.domain.model.PlayerObservation;
import nifori.me.persistence.nifobot.entities.PlayerObservEntity;

public class PlayerObservMapper {

  public PlayerObservEntity mapToJpa(PlayerObservation observation) {
    return PlayerObservEntity.builder()
        .OID(observation.getOID())
        .channeloid(observation.getChanneloid())
        .messageoid(observation.getMessageoid())
        .build();
  }

  public PlayerObservation mapToDomain(PlayerObservEntity observEntity) {
    return PlayerObservation.builder()
        .OID(observEntity.getOID())
        .channeloid(observEntity.getChanneloid())
        .messageoid(observEntity.getMessageoid())
        .build();
  }
}
