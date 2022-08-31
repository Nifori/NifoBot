package nifori.me.nifobot.ports;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelEditSpec;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.PortObservation;
import nifori.me.persistence.services.PortObservationService;

@Component
@RequiredArgsConstructor
@Log4j2
public class PortObservationUpdater {

  private final PortObservationService portObservationService;
  private NetStatUtil netStatUtil = new NetStatUtil();
  @Setter
  private GatewayDiscordClient gateway;

  @Scheduled(fixedRateString = "${port_observation.refresh_rate:300000}")
  public void update() {
    log.info("checking connections");

    if (gateway == null)
      return;

    portObservationService.getAllPortObservations()
        .forEach(observation -> {
          int connections = netStatUtil.readConnections(observation.getPort());

          if (connections != observation.getLastCount()) {
            renameChannel(observation, connections);
            updateObservation(observation, connections);
          }

        });

  }

  private void updateObservation(PortObservation observation, int connections) {
    observation.setLastCount(connections);
    portObservationService.savePortObservation(observation);
  }

  private void renameChannel(PortObservation observation, int connections) {
    try {
      VoiceChannel voiceChannel = gateway.getChannelById(Snowflake.of(observation.getChannelOID()))
          .cast(VoiceChannel.class)
          .block();
      String newName = observation.getChannelNameTemplate()
          .replace("{count}", Integer.toString(connections));
      log.info("Updating channel {} to {}", observation, newName);

      voiceChannel.edit(VoiceChannelEditSpec.builder()
          .name(newName)
          .build())
          .subscribe()
          .dispose();
    } catch (Exception e) {
      log.error(e, e);
    }
  }

}
