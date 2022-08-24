package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Server {

  private long OID;
  private String name;

  @Builder.Default
  private String prefix = "!";

}
