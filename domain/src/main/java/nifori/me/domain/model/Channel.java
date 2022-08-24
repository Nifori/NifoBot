package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Channel {

  private long OID;
  private long serverOID;
  private String channelname;

}
