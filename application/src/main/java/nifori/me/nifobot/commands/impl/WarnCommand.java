package nifori.me.nifobot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.Warning;
import nifori.me.nifobot.commands.Command;
import nifori.me.persistence.nifobot.services.ServerService;
import nifori.me.persistence.nifobot.services.WarningService;

@Component
@Log4j2
public class WarnCommand extends Command {

  @Autowired
  private WarningService warningService;

  @Autowired
  private ServerService serverService;

  public WarnCommand() {
    super.trigger = "warn";
  }

  @Override
  public void run(MessageCreateEvent event) {
    final MessageChannel channel = event.getMessage()
        .getChannel()
        .block();
    long serverid = event.getGuildId()
        .get()
        .asLong();

    int prefix = serverService.getPrefixById(event.getGuildId()
        .get()
        .asLong())
        .length();
    int triggerlenght = super.trigger.length();
    Snowflake userToWarn = event.getMessage()
        .getUserMentionIds()
        .stream()
        .findFirst()
        .get();
    int mentionConstant = 5;

    String message = event.getMessage()
        .getContent()
        .substring(prefix + triggerlenght + userToWarn.asString()
            .length() + mentionConstant)
        .trim();

    Warning warning = Warning.builder()
        .userid(userToWarn.asLong())
        .serverid(serverid)
        .message(message)
        .build();

    warningService.saveWarning(warning);
    String response = String.format("<@!%s> has %x warnings", userToWarn.asString(),
        warningService.countWarnings(serverid, userToWarn.asLong()));

    channel.createMessage(response)
        .subscribe();
  }
}
