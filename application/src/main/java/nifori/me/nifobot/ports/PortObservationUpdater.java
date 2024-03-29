package nifori.me.nifobot.ports;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelEditSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.PortObservation;
import nifori.me.nifobot.gateway.GatewayUser;
import nifori.me.persistence.nifobot.services.PortObservationService;

@Component
@RequiredArgsConstructor
@Log4j2
@ConditionalOnProperty("portobservation.enabled")
public class PortObservationUpdater extends GatewayUser {

  private final PortObservationService portObservationService;
  private NetStatUtil netStatUtil = new NetStatUtil();

  @Scheduled(fixedRateString = "${portobservation.refreshrate:300000}")
  public void update() {
    log.info("Checking Connections");

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

  private void renameChannel(PortObservation observation, int connections) {
    try {

      String newName = observation.getChannelNameTemplate()
          .replace("{count}", Integer.toString(connections));
      log.info("Updating channel {} to {}", observation, newName);

      gateway.getChannelById(Snowflake.of(observation.getChannelOID()))
          .cast(VoiceChannel.class)
          .doOnError(log::error)
          .subscribe(vc -> vc.edit(VoiceChannelEditSpec.builder()
              .name(newName)
              .build())
              .subscribe()
              .dispose(), log::error)
          .dispose();

    } catch (Exception e) {
      log.error(e, e);
    }
  }

  private void updateObservation(PortObservation observation, int connections) {
    observation.setLastCount(connections);
    portObservationService.savePortObservation(observation);
  }

}
