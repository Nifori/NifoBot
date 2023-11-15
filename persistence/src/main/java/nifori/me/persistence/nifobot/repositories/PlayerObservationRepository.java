package nifori.me.persistence.nifobot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.persistence.nifobot.entities.PlayerObservEntity;

public interface PlayerObservationRepository extends JpaRepository<PlayerObservEntity, Long> {

  Optional<PlayerObservEntity> findByOID(long oid);
}
