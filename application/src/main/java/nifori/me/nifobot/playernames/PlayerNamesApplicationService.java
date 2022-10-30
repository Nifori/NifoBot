package nifori.me.nifobot.playernames;

import lombok.RequiredArgsConstructor;
import nifori.me.domain.model.Lobby;
import nifori.me.domain.model.enums.BladeAndSoulJob;
import nifori.me.persistence.globalname.services.GlobalNameService;
import nifori.me.persistence.lobby.services.LobbyService;
import nifori.me.persistence.nifobot.services.PlayerObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerNamesApplicationService {

  private static final String template = "%s\t|\t%s\t|\t%s\t|\t%s";

  @Autowired
  private GlobalNameService globalNameService;

  @Autowired
  private PlayerObservationService playerObservationService;

  @Autowired
  private LobbyService lobbyService;

  public String createMessage() {

    List<Lobby> activeLobbies = lobbyService.getAllActiveLobbies();

    String[][] table = new String[4][activeLobbies.size() + 1];

    table[0][0] = "CharacterName";
    table[1][0] = "AccountName";
    table[2][0] = "Class";
    table[3][0] = "IpAddress";

    for (int i = 1; i <= activeLobbies.size(); i++) {
      var lobby = activeLobbies.get(i - 1);
      String accountName = globalNameService.getAccountNameById(lobby.getGameAccountId());
      BladeAndSoulJob job = BladeAndSoulJob.getById(lobby.getJob());

      table[0][i] = lobby.getCharacterName();
      table[1][i] = accountName;
      table[2][i] = job.getNameShort();
      table[3][i] = lobby.getLastPlayIPv4Address();

    }

    int maxSizeCharacterName = Arrays.stream(table[0])
        .max(Comparator.comparing(String::length))
        .get()
        .length();
    int maxSizeAccountName = Arrays.stream(table[1])
        .max(Comparator.comparing(String::length))
        .get()
        .length();

    normalizeSize(table[0], maxSizeCharacterName);
    normalizeSize(table[1], maxSizeAccountName);
    normalizeSize(table[2], maxSizeAccountName);

    StringBuilder builder = new StringBuilder();
    builder.append("```");
    for (int i = 0; i <= activeLobbies.size(); i++) {
      builder.append(template.formatted(table[0][i], table[1][i], table[2][i], table[3][i]));
      builder.append("\n");
    }
    builder.append("```");
    return builder.toString();

  }

  private void normalizeSize(String[] entries, int maxLength) {
    for (int i = 0; i < entries.length; i++) {
      while (entries[i].length() < maxLength) {
        if (entries[i].length() % 2 == 0) {
          entries[i] = entries[i] + " ";
        } else {
          entries[i] = " " + entries[i];
        }
      }
    }
  }

}
