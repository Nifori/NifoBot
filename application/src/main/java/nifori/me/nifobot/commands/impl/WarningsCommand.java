package nifori.me.nifobot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.Warning;
import nifori.me.nifobot.commands.Command;
import nifori.me.persistence.services.ServerService;
import nifori.me.persistence.services.WarningService;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Log4j2
public class WarningsCommand extends Command {

  @Autowired
  private WarningService warningService;

  @Autowired
  private ServerService serverService;

  public WarningsCommand() {
    super.trigger = "warnings";
  }

  @Override
  public void run(MessageCreateEvent event) {
    final MessageChannel channel = event.getMessage()
        .getChannel()
        .block();
    long serverid = event.getGuildId()
        .get()
        .asLong();

    Snowflake userToSearchFor = event.getMessage()
        .getUserMentionIds()
        .stream()
        .findFirst()
        .get();

    StringBuilder responseBuilder = new StringBuilder();
    event.getMessage()
        .getUserMentionIds()
        .forEach(mention -> {
          responseBuilder.append("<@!" + mention.asLong() + ">");
          responseBuilder.append(" has ");
          responseBuilder.append(warningService.countWarnings(serverid, mention.asLong()));
          responseBuilder.append(" warnings: \n");
          warningService.getAllWarnings(serverid, userToSearchFor.asLong())
              .forEach(warning -> {
                responseBuilder.append(warning.getMessage());
                responseBuilder.append("\n");
              });
        });

    channel.createMessage(responseBuilder.toString())
        .subscribe();
  }
}
