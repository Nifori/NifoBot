package nifori.me.persistence.lobby.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "charac")
public class LobbyEntity {

  @Id
  private Long PCID;

  private Timestamp lastPlayStartTime;
  private Timestamp lastPlayEndTime;

  private String characterName;
  private long job;
  private String gameAccountId;
  private String lastPlayIPv4Address;

}
