package nifori.me.persistence.nifobot.mapper;

import nifori.me.domain.model.PortObservation;
import nifori.me.persistence.nifobot.entities.PortObservEntity;

public class PortObservMapper {

  public PortObservEntity mapToJpa(PortObservation portObservation) {
    return PortObservEntity.builder()
        .OID(portObservation.getOID())
        .channelnametemplate(portObservation.getChannelNameTemplate())
        .channeloid(portObservation.getChannelOID())
        .port(portObservation.getPort())
        .lastcount(portObservation.getLastCount())
        .build();
  }

  public PortObservation mapToDomain(PortObservEntity portObservEntity) {
    return PortObservation.builder()
        .OID(portObservEntity.getOID())
        .channelNameTemplate(portObservEntity.getChannelnametemplate())
        .channelOID(portObservEntity.getChanneloid())
        .port(portObservEntity.getPort())
        .lastCount(portObservEntity.getLastcount())
        .build();
  }
}
