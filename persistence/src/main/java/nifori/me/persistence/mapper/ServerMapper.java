package nifori.me.persistence.mapper;

import nifori.me.domain.model.Server;
import nifori.me.persistence.entities.ServerEntity;

import java.util.stream.Collectors;

public class ServerMapper {
    private ChannelMapper channelMapper = new ChannelMapper();

    public ServerEntity mapToJpa(Server server) {
        return ServerEntity.builder().OID(server.getOID()).servername(server.getServername()).channels(server.getChannels().stream().map(channelMapper::mapToJpa).collect(Collectors.toList())).build();
    }

    public Server mapToDomain(ServerEntity entity) {
        return Server.builder().OID(entity.getOID()).servername(entity.getServername()).channels(entity.getChannels().stream().map(channelMapper::mapToDomain).collect(Collectors.toList())).build();
    }
}
