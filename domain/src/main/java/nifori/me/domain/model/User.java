package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

  private long OID;
  private String name;
}
