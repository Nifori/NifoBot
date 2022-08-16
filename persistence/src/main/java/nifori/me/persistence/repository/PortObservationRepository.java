package nifori.me.persistence.repository;

import java.util.Optional;

import nifori.me.persistence.entities.PortObservEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.persistence.entities.UserEntity;

public interface PortObservationRepository extends JpaRepository<PortObservEntity, Long> {

  Optional<PortObservEntity> findByOID(long oid);
}
