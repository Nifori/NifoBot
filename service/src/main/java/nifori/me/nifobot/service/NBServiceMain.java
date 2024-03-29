package nifori.me.nifobot.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.EventHandler.AbstractEventHandler;
import nifori.me.nifobot.gateway.GatewayUser;

@SpringBootApplication(scanBasePackages = {"nifori.me"})
//@EnableJpaRepositories(basePackages = {"nifori.me.persistence.nifobot.repositories", "nifori.me.persistence.lobby.repositories", "nifori.me.persistence.globalname.repositories"})
//@EntityScan(basePackages =  {"nifori.me.persistence.nifobot.entities", "nifori.me.persistence.lobby.entities", "nifori.me.persistence.globalname.entities"})
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

  @Autowired(required = false)
  private List<? extends GatewayUser> gatewayUsers = Collections.emptyList();


  @Bean
  public CommandLineRunner run(ApplicationContext ctx) {
    return (args) -> {
      final DiscordClient client = DiscordClient.create(id);
      final GatewayDiscordClient gateway = client.login()
          .block();

      gatewayUsers.forEach(gatewayUser -> {
        System.out.println(gatewayUser);
        gatewayUser.setGateway(gateway);
      });

      eventHandler.forEach(handler -> {
        gateway.on(handler.getEventClass())
            .subscribe(handler::handleEvent);
      });

      gateway.onDisconnect()
          .block();
    };
  }
}
