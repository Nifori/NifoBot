package nifori.me.persistence.repository;

import nifori.me.persistence.entities.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<ServerEntity, Long> {

  public Optional<ServerEntity> findByOID(long oid);

  @Query("SELECT s.prefix from ServerEntity s where s.OID = ?1")
  public String findPrefixByOID(long oid);

  @Modifying
  @Query("UPDATE ServerEntity s SET s.prefix = :prefix where s.OID = :oid")
  void setPrefixByOID(@Param("oid") long oid, @Param("prefix") String prefix);
}
