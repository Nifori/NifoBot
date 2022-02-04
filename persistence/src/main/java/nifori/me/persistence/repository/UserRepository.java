package nifori.me.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.persistence.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByOID(long oid);
}
