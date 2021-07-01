package nifori.me.persistence.repository;

import nifori.me.persistence.entities.ChannelEntity;
import nifori.me.persistence.entities.ServerEntity;
import nifori.me.persistence.repository.ServerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static nifori.me.persistence.Testconstants.CHANNELNAME;
import static nifori.me.persistence.Testconstants.SERVERNAME;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ServerRepositoryTest {

    @Autowired
    ServerRepository serverRepository;

    @Test
    public void testStoreOneEntity() {
        Assertions.assertDoesNotThrow(() -> serverRepository.save(getEntity(SERVERNAME)));
    }

    @Test
    public void testStoredEntityHasId() {
        ServerEntity storedEntity = serverRepository.save(getEntity(SERVERNAME));
        assertThat(storedEntity).isNotNull();
        assertThat(storedEntity.getOID()).isNotNull();
    }

    @Test
    public void testEntityIdIncreases() {
        ServerEntity firstEntity = serverRepository.save(getEntity(SERVERNAME));
        ServerEntity secondEntity = serverRepository.save(getEntity("Second_Testserver"));

        assertThat(serverRepository.count()).isEqualTo(2);
        assertThat(firstEntity.getOID()).isLessThan(secondEntity.getOID());
    }

    private ServerEntity getEntity(String servername) {
        ChannelEntity channel = ChannelEntity.builder().channelname(CHANNELNAME).build();
        return ServerEntity.builder().servername(servername).channels(Collections.singletonList(channel)).build();
    }
}
