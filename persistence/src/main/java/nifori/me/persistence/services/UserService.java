package nifori.me.persistence.services;

import java.util.Optional;

import javax.transaction.Transactional;

import nifori.me.domain.model.User;
import nifori.me.persistence.entities.UserEntity;
import nifori.me.persistence.mapper.UserMapper;
import nifori.me.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.Server;
import nifori.me.persistence.entities.ServerEntity;
import nifori.me.persistence.mapper.ServerMapper;
import nifori.me.persistence.repository.ServerRepository;

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
