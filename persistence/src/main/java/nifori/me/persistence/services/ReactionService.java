package nifori.me.persistence.services;

import java.util.Optional;

import javax.transaction.Transactional;

import nifori.me.domain.model.Reaction;
import nifori.me.persistence.entities.ReactionEntity;
import nifori.me.persistence.mapper.ReactionMapper;
import nifori.me.persistence.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.User;
import nifori.me.persistence.entities.UserEntity;
import nifori.me.persistence.mapper.UserMapper;

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
