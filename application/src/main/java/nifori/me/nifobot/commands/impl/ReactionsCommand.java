package nifori.me.nifobot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji.Custom;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.Reaction;
import nifori.me.nifobot.commands.Command;
import nifori.me.persistence.nifobot.services.ReactionService;

@Component
@Log4j2
public class ReactionsCommand extends Command {

  @Autowired
  private ReactionService reactionService;

  public ReactionsCommand() {
    super.trigger = "reactions";
  }

  @Override
  public void run(MessageCreateEvent event) {

    String[] messageArguments = event.getMessage()
        .getContent()
        .split(" ");

    if (messageArguments.length != 3) {
      answer(event, "You need to include the Emoji and the Role");
      return;
    }

    String[] emojiContent = messageArguments[1].substring(1, messageArguments[1].length() - 1)
        .split(":");
    Custom custom = Custom.custom(Snowflake.of(emojiContent[2]), emojiContent[1], "a".equals(emojiContent[0]));

    Reaction reaction = Reaction.builder()
        .reactionid(custom.getId()
            .asString())
        .messageoid(event.getMessage()
            .getReferencedMessage()
            .get()
            .getId()
            .asLong())
        .serveroid(event.getGuildId()
            .get()
            .asLong())
        .roleid(event.getMessage()
            .getRoleMentionIds()
            .get(0)
            .asLong())
        .build();

    reactionService.saveReaction(reaction);

    event.getMessage()
        .delete()
        .subscribe();
    event.getMessage()
        .getReferencedMessage()
        .get()
        .addReaction(custom)
        .subscribe();
  }

  private void answer(MessageCreateEvent event, String response) {
    event.getMessage()
        .getChannel()
        .block()
        .createMessage(response)
        .subscribe();
  }
}
