package nifori.me.persistence.mapper;

import nifori.me.domain.model.Channel;
import nifori.me.domain.model.Server;
import nifori.me.persistence.entities.ChannelEntity;
import nifori.me.persistence.entities.ServerEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerMapperTest {

    private ServerMapper mapper = new ServerMapper();

    @Test
    public void testMapToJpa() {
        Channel channel1 = Channel.builder().OID(456).channelname("Testchannel1").build();
        Channel channel2 = Channel.builder().OID(789).channelname("Testchannel2").build();
        List<Channel> channelList = Arrays.asList(channel1, channel2);

        Server server = Server.builder().OID(123).servername("Testserver").channels(channelList).build();

        ServerEntity entity = mapper.mapToJpa(server);
        assertThat(entity.getOID()).isEqualTo(server.getOID());
        assertThat(entity.getServername()).isEqualTo(server.getServername());
        assertThat(entity.getChannels()).hasSize(channelList.size());
        assertThat(entity.getChannels()).allMatch(channelEntity -> channelList.stream().map(channel -> Long.valueOf(channel.getOID())).anyMatch(channelOid -> channelOid.equals(channelEntity.getOID())));
    }

    @Test
    public void testMapToDomain() {
        ChannelEntity channel1 = ChannelEntity.builder().OID(Long.valueOf(456)).channelname("Testchannel1").build();
        ChannelEntity channel2 = ChannelEntity.builder().OID(Long.valueOf(789)).channelname("Testchannel2").build();
        List<ChannelEntity> channelList = Arrays.asList(channel1, channel2);

        ServerEntity entity = ServerEntity.builder().OID(Long.valueOf(123)).servername("Testserver").channels(channelList).build();

        Server server = mapper.mapToDomain(entity);
        assertThat(server.getOID()).isEqualTo(entity.getOID());
        assertThat(server.getServername()).isEqualTo(entity.getServername());
        assertThat(server.getChannels()).allMatch(channelEntity -> channelList.stream().map(channel -> Long.valueOf(channel.getOID())).anyMatch(channelOid -> channelOid.equals(channelEntity.getOID())));
    }


}
