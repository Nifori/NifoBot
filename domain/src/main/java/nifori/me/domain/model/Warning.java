package nifori.me.domain.model;

import java.util.ArrayList;
import java.util.List;

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
