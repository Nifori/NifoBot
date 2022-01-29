package nifori.me.nifobot.EventHandler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.ReactionAddEvent;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.Reaction;
import nifori.me.persistence.services.ReactionService;

@Component
@Log4j2
public class ReactionAddEventHandler {

  @Autowired
  private ReactionService reactionService;

  public void handleEvent(ReactionAddEvent event) {

    event.getEmoji()
        .asEmojiData()
        .id()
        .ifPresent(i -> {

          long messageid = event.getMessageId()
              .asLong();
          log.info("Searching for message {} and reaction {}", messageid, i.asLong());
          Optional<Reaction> reaction = reactionService.findReactionByMessageIdAndReactionId(messageid, i.asString());
          reaction.ifPresent((reaction1) -> {
            log.info("Role found");
            event.getMember()
                .get()
                .addRole(Snowflake.of(reaction1.getRoleid()))
                .subscribe();
          });
        });

  }

}
