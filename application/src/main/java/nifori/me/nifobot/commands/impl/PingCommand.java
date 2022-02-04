package nifori.me.nifobot.commands.impl;

import org.springframework.stereotype.Component;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Permission;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.commands.Command;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class PingCommand extends Command {

  public PingCommand() {
    super.trigger = "ping";
  }

  @Override
  public void run(MessageCreateEvent event) {
    final MessageChannel channel = event.getMessage()
        .getChannel()
        .block();

    if (event.getMember()
        .get()
        .getRoles()
        .toStream()
        .anyMatch(role -> role.getPermissions()
            .contains(Permission.ADMINISTRATOR))) {
      channel.createMessage("What can I do, Sir?")
          .onErrorResume(e -> Mono.empty())
          .subscribe();
    } else {
      channel.createMessage("Pong!")
          .onErrorResume(e -> Mono.empty())
          .subscribe();
    }
  }
}
