package nifori.me.nifobot.EventHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Member;
import lombok.extern.log4j.Log4j2;
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
        .ifPresent(emojiId -> {
          long messageId = event.getMessageId()
              .asLong();
          reactionService.findReactionByMessageIdAndReactionId(messageId, emojiId.asString())
              .ifPresent(reaction -> {
                Member member = event.getMember()
                    .get();
                member.addRole(Snowflake.of(reaction.getRoleid()))
                    .subscribe();
              });
        });
  }

}
