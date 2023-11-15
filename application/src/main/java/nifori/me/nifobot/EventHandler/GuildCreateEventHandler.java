package nifori.me.nifobot.EventHandler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import nifori.me.domain.model.Server;
import nifori.me.persistence.nifobot.services.ServerService;

@Component
public class GuildCreateEventHandler extends AbstractEventHandler {

  @Autowired
  private ServerService serverService;

  public GuildCreateEventHandler() {
    super(GuildCreateEvent.class);
  }

  @Override
  protected void executeAbstract(Event event) {
    execute((GuildCreateEvent) event);
  }

  private void execute(GuildCreateEvent event) {
    Optional<Server> serverById = serverService.getServerById(event.getGuild()
        .getId()
        .asLong());
    if (serverById.isPresent())
      return;

    Server build = Server.builder()
        .OID(event.getGuild()
            .getId()
            .asLong())
        .name(event.getGuild()
            .getName())
        .build();
    serverService.saveServer(build);
  }

}
