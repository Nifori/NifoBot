package nifori.me.nifobot.ports;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelEditSpec;
import lombok.RequiredArgsConstructor;
import nifori.me.persistence.services.PortObservationService;

@Component
@RequiredArgsConstructor
@EnableAsync
@Log4j2
public class PortObservationUpdater {

  private final PortObservationService portObservationService;
  private NetStatUtil netStatUtil = new NetStatUtil();
  private GatewayDiscordClient gateway;

  @Async
  @Scheduled(fixedRate = 300000)
  public void update() {

    if (gateway == null)
      return;

    portObservationService.getAllPortObservations()
        .forEach(observation -> {
          VoiceChannel voiceChannel = gateway.getChannelById(Snowflake.of(observation.getChannelOID()))
              .cast(VoiceChannel.class)
              .block();

          int connections = netStatUtil.readConnections(observation.getPort());
          String newName = observation.getChannelNameTemplate()
              .replace("{count}", Integer.toString(connections));
          log.info("Updating channel {} to {}", observation, newName);
          voiceChannel.edit(VoiceChannelEditSpec.builder()
              .name(newName)
              .build())
              .subscribe()
              .dispose();
        });
  }

  public void setGateway(GatewayDiscordClient gateway) {

    this.gateway = gateway;
  }
}
