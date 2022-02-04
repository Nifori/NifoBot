package nifori.me.persistence.mapper;

import nifori.me.domain.model.Reaction;
import nifori.me.persistence.entities.ReactionEntity;

public class ReactionMapper {

  public ReactionEntity mapToJpa(Reaction reaction) {
    return ReactionEntity.builder()
        .OID(reaction.getOID())
        .serveroid(reaction.getServeroid())
        .messageoid(reaction.getMessageoid())
        .reactionid(reaction.getReactionid())
        .roleid(reaction.getRoleid())
        .build();
  }

  public Reaction mapToDomain(ReactionEntity reactionEntity) {
    return Reaction.builder()
        .OID(reactionEntity.getOID())
        .serveroid(reactionEntity.getServeroid())
        .messageoid(reactionEntity.getMessageoid())
        .reactionid(reactionEntity.getReactionid())
        .roleid(reactionEntity.getRoleid())
        .build();
  }
}
