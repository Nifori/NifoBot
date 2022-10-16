package nifori.me.nifobot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelCreateSpec;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.Channel;
import nifori.me.domain.model.PortObservation;
import nifori.me.nifobot.commands.Command;
import nifori.me.nifobot.ports.NetStatUtil;
import nifori.me.nifobot.ports.PortConfiguration;
import nifori.me.nifobot.ports.PortObservationUpdater;
import nifori.me.persistence.nifobot.services.ChannelService;
import nifori.me.persistence.nifobot.services.PortObservationService;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class PortCommand extends Command {

  private static final String DEFAULT_TEMPLATE = "Connections: {count}";

  @Autowired
  private ChannelService channelService;
  @Autowired
  private PortObservationService portObservationService;
  @Autowired
  private PortObservationUpdater updater;

  @Autowired
  private PortConfiguration portConfiguration;

  private NetStatUtil util = new NetStatUtil();

  public PortCommand() {
    super.trigger = "port";
  }

  @Override
  public void run(MessageCreateEvent event) {

    final MessageChannel channel = event.getMessage()
        .getChannel()
        .block();

    if (portConfiguration.getAdmins()
        .contains(event.getMember()
            .get()
            .getTag())) {

      String[] messageArguments = event.getMessage()
          .getContent()
          .split(" ");

      String subCommand = messageArguments[1].toLowerCase();

      if (subCommand.contains("check")) {
        executeCheck(messageArguments, channel);
      } else if (subCommand.contains("observ")) {
        executeObserv(event, messageArguments);
      } else if (subCommand.contains("update")) {
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

    int port = Integer.parseInt(messageArguments[2]);
    int connections = util.readConnections(port);

    Guild guild = event.getGuild()
        .block();

    String channelNameTemplate = getChannelNameTemplate(messageArguments);
    String channelName = channelNameTemplate.replace("{count}", Integer.toString(connections));

    Snowflake everyoneId = guild.getEveryoneRole()
        .block()
        .getId();
    VoiceChannel createdChannel = guild.createVoiceChannel(VoiceChannelCreateSpec.builder()
        .name(channelName)
        .addPermissionOverwrite(
            PermissionOverwrite.forRole(everyoneId, PermissionSet.of(Permission.VIEW_CHANNEL), PermissionSet.all()))
        .build())
        .block();

    long channelOID = createdChannel.getId()
        .asLong();
    Channel channel = Channel.builder()
        .OID(channelOID)
        .channelname(channelName)
        .serverOID(guild.getId()
            .asLong())
        .build();
    channelService.saveChannel(channel);

    PortObservation portObservation = PortObservation.builder()
        .channelOID(channelOID)
        .channelNameTemplate(channelNameTemplate)
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

  private String getChannelNameTemplate(String[] messageArguments) {

    StringBuilder builder = new StringBuilder();
    for (int i = 3; i < messageArguments.length; i++) {
      builder.append(messageArguments[i]);
      builder.append(" ");
    }

    return builder.isEmpty() ? DEFAULT_TEMPLATE : builder.toString();
  }

  @Override
  public boolean isEnabled() {
    return portConfiguration.isEnabled();
  }
}
