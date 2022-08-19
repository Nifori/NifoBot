package nifori.me.nifobot.commands.impl;

import discord4j.common.util.Snowflake;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelCreateSpec;
import discord4j.rest.util.PermissionSet;
import nifori.me.domain.model.Channel;
import nifori.me.domain.model.PortObservation;
import nifori.me.nifobot.ports.PortObservationUpdater;
import nifori.me.persistence.services.ChannelService;
import nifori.me.persistence.services.PortObservationService;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private ChannelService channelService;
  @Autowired
  private PortObservationService portObservationService;
  @Autowired
  private PortObservationUpdater updater;

  private NetStatUtil util = new NetStatUtil();

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

      String subCommand = messageArguments[1];

      if (subCommand.toLowerCase()
          .contains("check")) {
        executeCheck(messageArguments, channel);
      } else if (subCommand.toLowerCase()
          .contains("observ")) {
        executeObserv(event, messageArguments);
      } else if (subCommand.toLowerCase()
          .contains("update")) {
        updater.update();
      } else {
        channel.createMessage(subCommand + " is no valid command. It has to be \"check\" or \"observ\"")
            .onErrorResume(e -> Mono.empty())
            .subscribe();
      }

    } else {
      channel.createMessage("You do not have the permissions to use this command")
          .onErrorResume(e -> Mono.empty())
          .subscribe();
    }
  }

  private void executeObserv(MessageCreateEvent event, String[] messageArguments) {
    String channelnametemplate = "Connections: {count}";
    int port = Integer.parseInt(messageArguments[2]);
    int connections = util.readConnections(port);
    String channelname = channelnametemplate.replace("{count}", Integer.toString(connections));

    Snowflake everyoneId = event.getGuild()
        .block()
        .getEveryoneRole()
        .block()
        .getId();
    VoiceChannel createdChannel = event.getGuild()
        .block()
        .createVoiceChannel(VoiceChannelCreateSpec.builder()
            .name(channelname)
            .addPermissionOverwrite(
                PermissionOverwrite.forRole(everyoneId, PermissionSet.of(Permission.VIEW_CHANNEL), PermissionSet.all()))
            .build())
        .block();

    long channeloid = createdChannel.getId()
        .asLong();
    Channel channel = Channel.builder()
        .OID(channeloid)
        .channelname(channelname)
        .build();
    channelService.saveChannel(channel);

    PortObservation portObservation = PortObservation.builder()
        .channelOID(channeloid)
        .channelNameTemplate(channelnametemplate)
        .port(port)
        .build();
    portObservationService.savePortObservation(portObservation);

  }

  private void executeCheck(String[] messageArguments, MessageChannel messageChannel) {
    int port = Integer.parseInt(messageArguments[2]);
    int connections = util.readConnections(port);

    messageChannel.createMessage("Current connections: " + connections)
        .onErrorResume(e -> Mono.empty())
        .subscribe();
  }

  @Override
  public NBFeature getFeature() {
    return NBFeature.PORT_OBSERVATION;
  }
}
