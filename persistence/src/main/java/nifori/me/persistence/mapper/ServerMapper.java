package nifori.me.persistence.mapper;

import nifori.me.domain.model.Server;
import nifori.me.persistence.entities.ServerEntity;

public class ServerMapper {

  public ServerEntity mapToJpa(Server server) {
    return ServerEntity.builder()
        .OID(server.getOID())
        .servername(server.getName())
        .prefix(server.getPrefix())
        .build();
  }

  public Server mapToDomain(ServerEntity entity) {
    return Server.builder()
        .OID(entity.getOID())
        .name(entity.getServername())
        .prefix(entity.getPrefix())
        .build();
  }
}
