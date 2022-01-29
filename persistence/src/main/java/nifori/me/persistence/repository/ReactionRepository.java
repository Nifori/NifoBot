package nifori.me.persistence.repository;

import java.util.Optional;
import java.util.stream.Stream;

import nifori.me.persistence.entities.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nifori.me.persistence.entities.WarningEntity;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {

  Optional<ReactionEntity> findByOID(long oid);

  @Query("select w from ReactionEntity w where w.messageoid = :messageid AND w.reactionid = :reactionid")
  Optional<ReactionEntity> findByMessageIdAndReactionId(@Param("messageid") long messageid,
      @Param("reactionid") String reactionid);
}
