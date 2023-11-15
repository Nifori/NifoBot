package nifori.me.persistence.nifobot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.persistence.nifobot.entities.ChannelEntity;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

  Optional<ChannelEntity> findByOID(long oid);
}
