package nifori.me.persistence.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.Warning;
import nifori.me.persistence.entities.WarningEntity;
import nifori.me.persistence.mapper.WarningMapper;
import nifori.me.persistence.repository.WarningRepository;

@Component
public class WarningService {

  @Autowired
  private WarningRepository warningRepository;

  private WarningMapper mapper = new WarningMapper();

  public Optional<Warning> getWarningById(long id) {
    Optional<WarningEntity> warningEntity = warningRepository.findByOID(id);
    return warningEntity.map(mapper::mapToDomain);
  }

  @Transactional
  public Warning saveWarning(Warning warning) {
    WarningEntity warningEntity = warningRepository.save(mapper.mapToJpa(warning));
    return mapper.mapToDomain(warningEntity);
  }

  public long countWarnings(long serverid, long userid) {
    return warningRepository.countByServerIdAndUserId(serverid, userid);
  }

  @Transactional
  public List<Warning> getAllWarnings(long serverid, long userid) {
    return warningRepository.findAllByOID(serverid, userid)
        .map(mapper::mapToDomain)
        .collect(Collectors.toList());
  }
}
