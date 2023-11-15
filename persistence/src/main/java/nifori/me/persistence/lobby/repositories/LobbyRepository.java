package nifori.me.persistence.lobby.repositories;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nifori.me.persistence.lobby.entities.LobbyEntity;

public interface LobbyRepository extends JpaRepository<LobbyEntity, Long> {

  Optional<LobbyEntity> findByPCID(long pcid);

  @Query("select lb from LobbyEntity lb where lb.lastPlayStartTime > lb.lastPlayEndTime AND lb.lastPlayStartTime > :minimalStartTime")
  Stream<LobbyEntity> findAllActive(Timestamp minimalStartTime);
}
