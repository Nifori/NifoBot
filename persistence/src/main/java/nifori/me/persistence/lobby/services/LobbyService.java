package nifori.me.persistence.lobby.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import nifori.me.domain.model.Lobby;
import nifori.me.persistence.lobby.entities.LobbyEntity;
import nifori.me.persistence.lobby.mapper.LobbyMapper;
import nifori.me.persistence.lobby.repositories.LobbyRepository;

@Component
public class LobbyService {

  @Autowired
  private LobbyRepository lobbyRepository;

  private LobbyMapper mapper = new LobbyMapper();

  public Optional<Lobby> getLobbyById(long id) {
    Optional<LobbyEntity> lobbyEntity = lobbyRepository.findByPCID(id);
    return lobbyEntity.map(mapper::mapToDomain);
  }

  @Transactional
  public List<Lobby> getAllActiveLobbies() {
    Stream<LobbyEntity> allActive = lobbyRepository.findAllActive(Timestamp.valueOf(LocalDateTime.now().minusDays(1).minusDays(80)));
    return allActive.map(mapper::mapToDomain)
        .collect(Collectors.toList());
  }
}
