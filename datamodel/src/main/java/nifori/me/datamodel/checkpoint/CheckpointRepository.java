package nifori.me.datamodel.checkpoint;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
  Optional<Checkpoint> findByScriptOid(String scriptOid);
}
