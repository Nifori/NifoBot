package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Lobby {

  private Long PCID;
  private String characterName;
  private long job;
  private String gameAccountId;
  private String lastPlayIPv4Address;

}
