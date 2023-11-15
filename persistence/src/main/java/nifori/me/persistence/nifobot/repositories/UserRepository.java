package nifori.me.persistence.nifobot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.persistence.nifobot.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByOID(long oid);
}
