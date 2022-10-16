package nifori.me.persistence.nifobot.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.User;
import nifori.me.persistence.nifobot.entities.UserEntity;
import nifori.me.persistence.nifobot.mapper.UserMapper;
import nifori.me.persistence.nifobot.repositories.UserRepository;

@Component
public class UserService {

  @Autowired
  private UserRepository userRepository;

  private UserMapper mapper = new UserMapper();

  public Optional<User> getUserById(long id) {
    Optional<UserEntity> userEntity = userRepository.findByOID(id);
    return userEntity.map(mapper::mapToDomain);
  }

  @Transactional
  public User saveUser(User user) {
    UserEntity userEntity = userRepository.save(mapper.mapToJpa(user));
    return mapper.mapToDomain(userEntity);
  }
}
