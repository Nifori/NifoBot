package nifori.me.persistence.globalname.mapper;

import nifori.me.domain.model.GlobalName;
import nifori.me.persistence.globalname.entities.GlobalNameEntity;

public class GlobalNameMapper {

  public GlobalNameEntity mapToJpa(GlobalName globalName) {
    return GlobalNameEntity.builder()
        .userId(globalName.getUserId())
        .name(globalName.getName())
        .build();
  }

  public GlobalName mapToDomain(GlobalNameEntity globalNameEntity) {
    return GlobalName.builder()
        .userId(globalNameEntity.getUserId())
        .name(globalNameEntity.getName())
        .build();
  }
}
