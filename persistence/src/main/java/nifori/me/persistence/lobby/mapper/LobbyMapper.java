package nifori.me.persistence.lobby.mapper;

import nifori.me.domain.model.Lobby;
import nifori.me.persistence.lobby.entities.LobbyEntity;

public class LobbyMapper {

  public LobbyEntity mapToJpa(Lobby lobby) {
    return LobbyEntity.builder()
        .PCID(lobby.getPCID())
        .job(lobby.getJob())
        .characterName(lobby.getCharacterName())
        .gameAccountId(lobby.getGameAccountId())
        .lastPlayIPv4Address(lobby.getLastPlayIPv4Address())
        .build();
  }

  public Lobby mapToDomain(LobbyEntity lobbyEntity) {
    return Lobby.builder()
        .PCID(lobbyEntity.getPCID())
        .job(lobbyEntity.getJob())
        .characterName(lobbyEntity.getCharacterName())
        .gameAccountId(lobbyEntity.getGameAccountId())
        .lastPlayIPv4Address(lobbyEntity.getLastPlayIPv4Address())
        .build();
  }
}
