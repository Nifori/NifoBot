package nifori.me.nifobot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.reaction.ReactionEmoji.Custom;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.Reaction;
import nifori.me.nifobot.commands.Command;
import nifori.me.persistence.services.ReactionService;

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
    System.out.println(event.getMessage()
        .getContent());
    String[] s = event.getMessage()
        .getContent()
        .split(" ");
    String emoji = s[1];

    String[] emojiArray = emoji.substring(1, emoji.length() - 1)
        .split(":");

    Custom of = ReactionEmoji.of(Long.valueOf(emojiArray[2]), emojiArray[1], "a".equals(emojiArray[0]))
        .asCustomEmoji()
        .get();

    Reaction build = Reaction.builder()
        .reactionid(of.getId()
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
    Reaction reaction = reactionService.saveReaction(build);
    log.info(reaction);
    event.getMessage()
        .delete()
        .subscribe();
    event.getMessage()
        .getReferencedMessage()
        .get()
        .addReaction(of)
        .subscribe();

  }
}
