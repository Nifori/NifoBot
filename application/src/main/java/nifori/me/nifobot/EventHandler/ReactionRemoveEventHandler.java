package nifori.me.nifobot.EventHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.message.ReactionRemoveEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import lombok.extern.log4j.Log4j2;
import nifori.me.persistence.services.ReactionService;

@Component
@Log4j2
public class ReactionRemoveEventHandler extends AbstractEventHandler {

  @Autowired
  private ReactionService reactionService;

  public ReactionRemoveEventHandler() {
    super(ReactionRemoveEvent.class);
  }

  @Override
  protected void executeAbstract(Event event) {
    execute((ReactionRemoveEvent) event);
  }

  public void execute(ReactionRemoveEvent event) {

    event.getEmoji()
        .asEmojiData()
        .id()
        .ifPresent(emojiId -> {
          long messageId = event.getMessageId()
              .asLong();

          reactionService.findReactionByMessageIdAndReactionId(messageId, emojiId.asString())
              .ifPresent(reaction -> {
                Guild guild = event.getGuild()
                    .block();
                User user = event.getUser()
                    .block();
                Member member = user.asMember(guild.getId())
                    .block();
                member.removeRole(Snowflake.of(reaction.getRoleid()))
                    .subscribe();
              });
        });

  }

}
