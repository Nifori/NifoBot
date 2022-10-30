package nifori.me.nifobot.playernames;

import nifori.me.persistence.nifobot.services.PlayerObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.spec.MessageEditSpec;
import lombok.Data;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Data
@Configuration
@Log4j2
public class PlayerNamesUpdater {

  private static final String template = "%s | %s |  %s | %s";

  @Autowired
  private PlayerNamesApplicationService applicationService;

  @Autowired
  private PlayerObservationService playerObservationService;

  @Setter
  private GatewayDiscordClient gateway;

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
