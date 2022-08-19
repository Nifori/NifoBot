package nifori.me.nifobot.ports;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelEditMono;
import discord4j.core.spec.VoiceChannelEditSpec;
import discord4j.discordjson.json.ChannelData;
import discord4j.discordjson.json.ChannelModifyRequest;
import lombok.RequiredArgsConstructor;
import nifori.me.persistence.services.PortObservationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PortObservationUpdater {

  private final PortObservationService portObservationService;
  private NetStatUtil netStatUtil = new NetStatUtil();
  private GatewayDiscordClient gateway;

  @Scheduled(fixedRate = 30000)
  public void update() {

    if (gateway == null)
      return;

    System.out.println("updating");
    portObservationService.getAllPortObservations()
        .forEach(observation -> {
          System.out.println(observation);
          VoiceChannel voiceChannel = gateway.getChannelById(Snowflake.of(observation.getChannelOID()))
              .cast(VoiceChannel.class)
              .block();

          int connections = netStatUtil.readConnections(observation.getPort());
          String newName = observation.getChannelNameTemplate()
              .replace("{count}", Integer.toString(connections));
          System.out.println(newName);
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
