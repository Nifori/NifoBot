package nifori.me.nifobot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.commands.Command;
import nifori.me.persistence.services.ServerService;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class PrefixCommand extends Command {

  @Autowired
  private ServerService serverService;

  public PrefixCommand() {
    super.trigger = "prefix";
  }

  @Override
  public void run(MessageCreateEvent event) {
    final MessageChannel channel = event.getMessage()
        .getChannel()
        .block();
    String prefix = event.getMessage()
        .getContent()
        .split(" ")[1];

    serverService.setPrefix(event.getGuildId()
        .get()
        .asLong(), prefix);

    channel.createMessage("Set prefix to " + prefix)
        .onErrorResume(e -> Mono.empty())
        .subscribe();
  }
}
