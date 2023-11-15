package nifori.me.persistence.nifobot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import nifori.me.domain.model.Reaction;
import nifori.me.persistence.nifobot.entities.ReactionEntity;
import nifori.me.persistence.nifobot.mapper.ReactionMapper;
import nifori.me.persistence.nifobot.repositories.ReactionRepository;

@Component
public class ReactionService {

  @Autowired
  private ReactionRepository reactionRepository;

  private ReactionMapper mapper = new ReactionMapper();

  public Optional<Reaction> getReactionById(long id) {
    Optional<ReactionEntity> reactionEntity = reactionRepository.findByOID(id);
    return reactionEntity.map(mapper::mapToDomain);
  }

  @Transactional
  public Reaction saveReaction(Reaction reaction) {
    ReactionEntity reactionEntity = reactionRepository.save(mapper.mapToJpa(reaction));
    return mapper.mapToDomain(reactionEntity);
  }

  public Optional<Reaction> findReactionByMessageIdAndReactionId(long messageid, String reactionid) {
    Optional<ReactionEntity> reactionEntity = reactionRepository.findByMessageIdAndReactionId(messageid, reactionid);
    return reactionEntity.map(mapper::mapToDomain);
  }
}
