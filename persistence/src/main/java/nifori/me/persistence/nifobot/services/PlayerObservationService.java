package nifori.me.persistence.nifobot.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.PlayerObservation;
import nifori.me.persistence.nifobot.entities.PlayerObservEntity;
import nifori.me.persistence.nifobot.mapper.PlayerObservMapper;
import nifori.me.persistence.nifobot.repositories.PlayerObservationRepository;

@Component
public class PlayerObservationService {

  @Autowired
  private PlayerObservationRepository playerObservationRepository;

  private PlayerObservMapper mapper = new PlayerObservMapper();

  public Optional<PlayerObservation> getPlayerObservationById(long id) {
    Optional<PlayerObservEntity> playerObservEntity = playerObservationRepository.findByOID(id);
    return playerObservEntity.map(mapper::mapToDomain);
  }

  @Transactional
  public PlayerObservation savePlayerObservation(PlayerObservation observation) {
    PlayerObservEntity playerObservEntity = playerObservationRepository.save(mapper.mapToJpa(observation));
    return mapper.mapToDomain(playerObservEntity);
  }

  public List<PlayerObservation> getAllPlayerObservations() {
    return playerObservationRepository.findAll()
        .stream()
        .map(mapper::mapToDomain)
        .toList();
  }
}
