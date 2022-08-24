package nifori.me.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import nifori.me.domain.model.Channel;
import nifori.me.domain.model.Server;
import nifori.me.persistence.entities.ServerEntity;

public class ServerMapperTest {

  private ServerMapper mapper = new ServerMapper();

  @Test
  public void testMapToJpa() {
    Channel channel1 = Channel.builder()
        .OID(456)
        .channelname("Testchannel1")
        .build();
    Channel channel2 = Channel.builder()
        .OID(789)
        .channelname("Testchannel2")
        .build();
    List<Channel> channelList = Arrays.asList(channel1, channel2);

    Server server = Server.builder()
        .OID(123)
        .name("Testserver")
        .build();

    ServerEntity entity = mapper.mapToJpa(server);
    assertThat(entity.getOID()).isEqualTo(server.getOID());
    assertThat(entity.getServername()).isEqualTo(server.getName());
  }

  @Test
  public void testMapToDomain() {
    ServerEntity entity = ServerEntity.builder()
        .OID(Long.valueOf(123))
        .servername("Testserver")
        .build();

    Server server = mapper.mapToDomain(entity);
    assertThat(server.getOID()).isEqualTo(entity.getOID());
    assertThat(server.getName()).isEqualTo(entity.getServername());
  }

}
