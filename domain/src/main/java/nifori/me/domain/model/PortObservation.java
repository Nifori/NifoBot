package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortObservation {
  private long OID;
  private String channelNameTemplate;
  private long channelOID;
  private int port;
  private int lastCount;
}
