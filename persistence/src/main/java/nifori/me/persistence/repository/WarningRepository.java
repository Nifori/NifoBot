package nifori.me.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.persistence.entities.WarningEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.stream.Stream;

public interface WarningRepository extends JpaRepository<WarningEntity, Long> {

  Optional<WarningEntity> findByOID(long oid);

  @Query("select count(w) from WarningEntity w where w.serveroid = :serverid AND w.useroid = :userid")
  long countByServerIdAndUserId(@Param("serverid") long serverid, @Param("userid") long userid);

  @Query("select w from WarningEntity w where w.serveroid = :serverid AND w.useroid = :userid")
  Stream<WarningEntity> findAllByOID(@Param("serverid") long serverid, @Param("userid") long userid);
}
