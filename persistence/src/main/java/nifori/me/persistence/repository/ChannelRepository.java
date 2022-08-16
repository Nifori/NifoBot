package nifori.me.persistence.repository;

import nifori.me.domain.model.Channel;
import nifori.me.persistence.entities.PortObservEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.persistence.entities.ChannelEntity;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

  Optional<ChannelEntity> findByOID(long oid);
}
