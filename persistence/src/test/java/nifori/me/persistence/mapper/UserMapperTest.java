package nifori.me.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import nifori.me.domain.model.User;
import nifori.me.persistence.entities.UserEntity;

public class UserMapperTest {

  private UserMapper mapper = new UserMapper();

  @Test
  public void testMapToJpa() {
    User user = User.builder()
        .OID(123)
        .name("Testname")
        .build();
    UserEntity entity = mapper.mapToJpa(user);
    assertThat(entity.getOID()).isEqualTo(user.getOID());
    assertThat(entity.getName()).isEqualTo(user.getName());
  }

  @Test
  public void testMapToDomain() {
    UserEntity entity = UserEntity.builder()
        .OID(Long.valueOf(123))
        .name("Testname")
        .build();
    User user = mapper.mapToDomain(entity);
    assertThat(user.getOID()).isEqualTo(user.getOID());
    assertThat(user.getName()).isEqualTo(user.getName());
  }

}
