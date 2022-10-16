package nifori.me.persistence.nifobot.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.PortObservation;
import nifori.me.persistence.nifobot.entities.PortObservEntity;
import nifori.me.persistence.nifobot.mapper.PortObservMapper;
import nifori.me.persistence.nifobot.repositories.PortObservationRepository;

@Component
public class PortObservationService {

  @Autowired
  private PortObservationRepository portObservationRepository;

  private PortObservMapper mapper = new PortObservMapper();

  public Optional<PortObservation> getPortObservationById(long id) {
    Optional<PortObservEntity> portObservEntity = portObservationRepository.findByOID(id);
    return portObservEntity.map(mapper::mapToDomain);
  }

  @Transactional
  public PortObservation savePortObservation(PortObservation portObservation) {
    PortObservEntity portObservEntity = portObservationRepository.save(mapper.mapToJpa(portObservation));
    return mapper.mapToDomain(portObservEntity);
  }

  public List<PortObservation> getAllPortObservations() {
    return portObservationRepository.findAll()
        .stream()
        .map(mapper::mapToDomain)
        .toList();
  }
}
