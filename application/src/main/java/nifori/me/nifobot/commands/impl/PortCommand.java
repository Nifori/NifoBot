package nifori.me.nifobot.commands.impl;

import org.springframework.stereotype.Component;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Permission;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.commands.Command;
import nifori.me.nifobot.feature.NBFeature;
import nifori.me.nifobot.ports.NetStatUtil;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class PortCommand extends Command {

  NetStatUtil util = new NetStatUtil();

  public PortCommand() {
    super.trigger = "port";
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

      String[] messageArguments = event.getMessage()
              .getContent()
              .split(" ");

      int connections = util.readConnections(Integer.parseInt(messageArguments[1]));

      channel.createMessage("Current connections: " + connections)
          .onErrorResume(e -> Mono.empty())
          .subscribe();

    } else {
      channel.createMessage("You do not have the permissions to use this command")
          .onErrorResume(e -> Mono.empty())
          .subscribe();
    }
  }

  @Override
  public NBFeature getFeature() {
    return NBFeature.PORT_OBSERVATION;
  }
}
