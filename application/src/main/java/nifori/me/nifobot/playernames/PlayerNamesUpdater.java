package nifori.me.nifobot.playernames;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import discord4j.core.GatewayDiscordClient;
import lombok.Data;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import nifori.me.domain.model.Lobby;
import nifori.me.domain.model.enums.BladeAndSoulJob;
import nifori.me.persistence.globalname.services.GlobalNameService;
import nifori.me.persistence.lobby.services.LobbyService;

@Data
@Configuration
@Log4j2
public class PlayerNamesUpdater {

  private static final String template = "%s | %s |  %s | %s";

  @Autowired
  private GlobalNameService globalNameService;

  @Autowired
  private LobbyService lobbyService;

  @Setter
  private GatewayDiscordClient gateway;

  @Scheduled(fixedRate = 5000)
  public void update() {

    System.out.println("Start");

    StringBuilder builder = new StringBuilder();
    builder.append("CharacterName | AccountName | Class | IpAddress");
    builder.append("\n");

    List<Lobby> activeLobbies = lobbyService.getAllActiveLobbies();
    activeLobbies.forEach(lobby -> {
      String accountName = globalNameService.getAccountNameById(lobby.getGameAccountId());
      BladeAndSoulJob job = BladeAndSoulJob.getById(lobby.getJob());

      builder.append(template.formatted(lobby.getCharacterName(), accountName, job.getNameShort(),
          lobby.getLastPlayIPv4Address()));
      builder.append("\n");

    });
    System.out.println(builder.toString());
    System.out.println("End");

    // Optional<GlobalName> globalNameById =
    // globalNameService.getGlobalNameById("986A6E15-A7D3-44A7-91A9-E660DA8E8155");
    // System.out.println(globalNameById);

  }

}
