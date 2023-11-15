package nifori.me.nifobot.playernames;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.spec.MessageEditSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.gateway.GatewayUser;
import nifori.me.persistence.nifobot.services.PlayerObservationService;

@Component
@RequiredArgsConstructor
@Log4j2
@ConditionalOnProperty("playernames.enabled")
public class PlayerNamesUpdater extends GatewayUser {

  private static final String template = "%s | %s |  %s | %s";

  private final PlayerNamesApplicationService applicationService;
  private final PlayerObservationService playerObservationService;

  @Scheduled(fixedRateString = "${playernames.refreshrate:300000}")
  public void update() {

    log.info("Updating player names");

    if (gateway == null) {
      return;
    }

    String players = applicationService.createMessage();

    playerObservationService.getAllPlayerObservations()
        .forEach(playerObservation -> {
          log.info(playerObservation);
          gateway
              .getMessageById(Snowflake.of(playerObservation.getChanneloid()),
                  Snowflake.of(playerObservation.getMessageoid()))
              .subscribe(message -> message.edit(MessageEditSpec.builder()
                  .contentOrNull(players)
                  .build())
                  .subscribe());
        });

  }

}
