package nifori.me.persistence.mapper;

import static nifori.me.persistence.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import nifori.me.persistence.nifobot.mapper.WarningMapper;
import org.junit.jupiter.api.Test;

import nifori.me.domain.model.Warning;
import nifori.me.persistence.nifobot.entities.WarningEntity;

public class WarningMapperTest {

  private WarningMapper mapper = new WarningMapper();

  @Test
  public void testMapToJpa() {
    Warning warning = Warning.builder()
        .OID(123)
        .message(DEFAULT_MESSAGE)
        .serverid(SERVER_OID)
        .userid(USER_OID)
        .build();

    WarningEntity entity = mapper.mapToJpa(warning);

    assertThat(entity.getOID()).isEqualTo(warning.getOID());
    assertThat(entity.getMessage()).isEqualTo(warning.getMessage());
    assertThat(entity.getServeroid()).isEqualTo(warning.getServerid());
    assertThat(entity.getUseroid()).isEqualTo(warning.getUserid());
  }

  @Test
  public void testMapToDomain() {
    WarningEntity entity = WarningEntity.builder()
        .OID(Long.valueOf(123))
        .message(DEFAULT_MESSAGE)
        .serveroid(SERVER_OID)
        .useroid(USER_OID)
        .build();

    Warning warning = mapper.mapToDomain(entity);

    assertThat(warning.getOID()).isEqualTo(entity.getOID());
    assertThat(warning.getMessage()).isEqualTo(entity.getMessage());
    assertThat(warning.getServerid()).isEqualTo(entity.getServeroid());
    assertThat(warning.getUserid()).isEqualTo(entity.getUseroid());
  }

}
