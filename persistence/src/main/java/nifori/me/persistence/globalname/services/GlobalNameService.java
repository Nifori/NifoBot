package nifori.me.persistence.globalname.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.GlobalName;
import nifori.me.persistence.globalname.entities.GlobalNameEntity;
import nifori.me.persistence.globalname.mapper.GlobalNameMapper;
import nifori.me.persistence.globalname.repositories.GlobalNameRepository;

@Component
public class GlobalNameService {

  @Autowired
  private GlobalNameRepository globalNameRepository;

  private GlobalNameMapper mapper = new GlobalNameMapper();

  public Optional<GlobalName> getGlobalNameById(String userId) {
    Optional<GlobalNameEntity> globalNameEntity = globalNameRepository.findByUserId(userId);
    return globalNameEntity.map(mapper::mapToDomain);
  }

  public String getAccountNameById(String userId) {
    GlobalNameEntity byUserId = globalNameRepository.findByUserId(userId)
        .orElseThrow();
    String name = byUserId.getName();
    return name.substring(0, name.indexOf('@'));
  }

}
