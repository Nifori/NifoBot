package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reaction {

  private long OID;
  private long serveroid;
  private long messageoid;
  private String reactionid;
  private long roleid;

}
