package nifori.me.nifobot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.EventHandler.GuildCreateEventHandler;
import nifori.me.nifobot.EventHandler.MessageCreateEventHandler;

@SpringBootApplication(scanBasePackages = {"nifori.me"})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "nifori.me.persistence.repository")
@Log4j2
public class SpringMain {

    public static void main(final String[] args) {
        SpringApplication.run(SpringMain.class, args);
    }

    @Value("${discordclient.id}")
    private String id;

    @Autowired
    private MessageCreateEventHandler messageCreateEventHandler;

    @Autowired
    private GuildCreateEventHandler guildCreateEventHandler;

    // te
    @Bean
    public CommandLineRunner run(ApplicationContext ctx) {
        return (args) -> {

            final DiscordClient client = DiscordClient.create(id);
            final GatewayDiscordClient gateway = client.login().block();

            gateway.on(Event.class).subscribe(log::info);

            gateway.on(MessageCreateEvent.class).subscribe(messageCreateEventHandler::handleEvent);
            gateway.on(GuildCreateEvent.class).subscribe(guildCreateEventHandler::handleEvent);

            gateway.onDisconnect().block();
        };
    }
}
