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

    System.out.println("----------");
    System.out.println(event);
    System.out.println("----------");
    System.out.println(event.getEmoji());
    System.out.println("----------");
    System.out.println(event.getEmoji()
        .asEmojiData());
    System.out.println("----------");
    System.out.println(event.getEmoji()
        .asUnicodeEmoji());
    System.out.println("----------");
    System.out.println(event.getEmoji()
        .asCustomEmoji());
    System.out.println("----------");
    System.out.println();
    System.out.println("----------");

    reactionService.saveReaction(Reaction.builder()
        .reactionid(Integer.toString(event.getEmoji()
                .asEmojiData().hashCode()))
        .build());

    long messageid = event.getMessageId()
        .asLong();
    String reactionid = Integer.toString(event.getEmoji()
        .asEmojiData().hashCode());

    log.info("Searching for message {} and reaction {}", messageid, reactionid);
    Optional<Reaction> reaction = reactionService.findReactionByMessageIdAndReactionId(messageid, reactionid);

    reaction.ifPresent((reaction1) -> {
      event.getMember()
          .get()
          .addRole(Snowflake.of(reaction1.getRoleid()));
    });

  }

}
