package nifori.me.persistence.nifobot.repositories;

import java.util.Optional;

import nifori.me.persistence.nifobot.entities.PortObservEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortObservationRepository extends JpaRepository<PortObservEntity, Long> {

  Optional<PortObservEntity> findByOID(long oid);
}
