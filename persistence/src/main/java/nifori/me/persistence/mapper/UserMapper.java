package nifori.me.persistence.mapper;

import nifori.me.domain.model.User;
import nifori.me.persistence.entities.UserEntity;

public class UserMapper {

  public UserEntity mapToJpa(User user) {
    return UserEntity.builder()
        .OID(user.getOID())
        .name(user.getName())
        .build();
  }

  public User mapToDomain(UserEntity userEntity) {
    return User.builder()
        .OID(userEntity.getOID())
        .name(userEntity.getName())
        .build();
  }
}
