package nifori.me.persistence.mapper;

import nifori.me.domain.model.Warning;
import nifori.me.persistence.entities.WarningEntity;

public class WarningMapper {

  public WarningEntity mapToJpa(Warning warning) {
    return WarningEntity.builder()
        .OID(warning.getOID())
        .serveroid(warning.getServerid())
        .useroid(warning.getUserid())
        .message(warning.getMessage())
        .build();
  }

  public Warning mapToDomain(WarningEntity warningEntity) {
    return Warning.builder()
        .OID(warningEntity.getOID())
        .serverid(warningEntity.getServeroid())
        .userid(warningEntity.getUseroid())
        .message(warningEntity.getMessage())
        .build();
  }
}
