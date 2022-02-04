package nifori.me.persistence.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nifori.me.domain.model.Server;
import nifori.me.persistence.entities.ServerEntity;
import nifori.me.persistence.mapper.ServerMapper;
import nifori.me.persistence.repository.ServerRepository;

@Component
public class ServerService {

  @Autowired
  private ServerRepository repository;

  private ServerMapper mapper = new ServerMapper();

  public Optional<Server> getServerById(long id) {
    Optional<ServerEntity> serverEntity = repository.findByOID(id);
    return serverEntity.map(mapper::mapToDomain);
  }

  public String getPrefixById(long id) {
    return repository.findPrefixByOID(id);
  }

  @Transactional
  public void setPrefix(long id, String prefix) {
    repository.setPrefixByOID(id, prefix);
  }

  @Transactional
  public Server saveServer(Server server) {
    ServerEntity serverEntity = repository.save(mapper.mapToJpa(server));
    return mapper.mapToDomain(serverEntity);
  }
}
