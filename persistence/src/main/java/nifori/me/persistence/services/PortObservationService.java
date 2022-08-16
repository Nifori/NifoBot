package nifori.me.persistence.services;

import java.util.Optional;

import javax.transaction.Transactional;

import nifori.me.domain.model.PortObservation;
import nifori.me.persistence.entities.PortObservEntity;
import nifori.me.persistence.mapper.PortObservMapper;
import nifori.me.persistence.repository.PortObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
  public PortObservation savePortObservation(PortObservation warportObservationing) {
    PortObservEntity portObservEntity = portObservationRepository.save(mapper.mapToJpa(warportObservationing));
    return mapper.mapToDomain(portObservEntity);
  }

}
