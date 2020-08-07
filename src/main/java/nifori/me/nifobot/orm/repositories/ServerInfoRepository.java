package nifori.me.nifobot.orm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import nifori.me.nifobot.orm.entities.ServerInfo;

public interface ServerInfoRepository extends JpaRepository<ServerInfo, Long> {

}