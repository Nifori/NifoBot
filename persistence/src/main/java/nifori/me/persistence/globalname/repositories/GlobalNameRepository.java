package nifori.me.persistence.globalname.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nifori.me.persistence.globalname.entities.GlobalNameEntity;

public interface GlobalNameRepository extends JpaRepository<GlobalNameEntity, String> {

  @Query("select gn from GlobalNameEntity gn where gn.userId = :userId AND gn.nameType = 2")
  Optional<GlobalNameEntity> findByUserId(String userId);
}
