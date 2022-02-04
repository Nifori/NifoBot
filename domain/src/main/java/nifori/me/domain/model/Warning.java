package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Warning {

  private long OID;
  private long userid;
  private long serverid;
  private String message;

}
