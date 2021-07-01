package nifori.me.nifobot.orm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.nifobot.orm.entities.Lifechannel;

public interface LifechannelRepository extends JpaRepository<Lifechannel, Long> {
    
}