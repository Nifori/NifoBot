package nifori.me.persistence.nifobot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nifori.me.persistence.nifobot.entities.ReactionEntity;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {

  Optional<ReactionEntity> findByOID(long oid);

  @Query("select w from ReactionEntity w where w.messageoid = :messageid AND w.reactionid = :reactionid")
  Optional<ReactionEntity> findByMessageIdAndReactionId(@Param("messageid") long messageid,
      @Param("reactionid") String reactionid);
}
