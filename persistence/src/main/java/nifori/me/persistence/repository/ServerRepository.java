package nifori.me.persistence.repository;

import nifori.me.persistence.entities.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<ServerEntity, Long> {

}
