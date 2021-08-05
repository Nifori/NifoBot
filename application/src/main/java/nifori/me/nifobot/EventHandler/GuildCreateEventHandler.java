package nifori.me.nifobot.EventHandler;

import discord4j.core.event.domain.guild.GuildCreateEvent;
import nifori.me.domain.model.Server;
import nifori.me.persistence.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GuildCreateEventHandler {

    @Autowired
    private ServerService serverService;

    public void handleEvent(GuildCreateEvent event) {
        Optional<Server> serverById = serverService.getServerById(event.getGuild().getId().asLong());
        if (serverById.isPresent()) return;

        Server build = Server.builder().OID(event.getGuild().getId().asLong()).name(event.getGuild().getName()).build();
        serverService.saveServer(build);
    }

}
