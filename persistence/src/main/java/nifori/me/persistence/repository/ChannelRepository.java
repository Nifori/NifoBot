package nifori.me.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.persistence.entities.ChannelEntity;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

}
