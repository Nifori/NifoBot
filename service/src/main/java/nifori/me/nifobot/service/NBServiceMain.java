package nifori.me.nifobot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.EventHandler.AbstractEventHandler;
import nifori.me.nifobot.playernames.PlayerNamesUpdater;
import nifori.me.nifobot.ports.PortObservationUpdater;

@SpringBootApplication(scanBasePackages = {"nifori.me"})
@EnableJpaRepositories(basePackages = "nifori.me.persistence.repository")
@EnableScheduling
@Log4j2
public class NBServiceMain {

  public static void main(final String[] args) {
    SpringApplication.run(NBServiceMain.class, args);
  }

  @Value("${discordclient.id}")
  private String id;

  @Autowired
  private List<AbstractEventHandler> eventHandler;

  @Autowired
  private PortObservationUpdater portObservationUpdater;

  @Autowired
  private PlayerNamesUpdater playerNamesUpdater;

  @Bean
  public CommandLineRunner run(ApplicationContext ctx) {
    return (args) -> {
      final DiscordClient client = DiscordClient.create(id);
      final GatewayDiscordClient gateway = client.login()
          .block();

      portObservationUpdater.setGateway(gateway);
      playerNamesUpdater.setGateway(gateway);

      eventHandler.forEach(handler -> {
        gateway.on(handler.getEventClass())
            .subscribe(handler::handleEvent);
      });

      gateway.onDisconnect()
          .block();
    };
  }
}
