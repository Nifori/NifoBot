package nifori.me.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Server {

  private long OID;
  private String name;

  @Builder.Default
  private String prefix = "!";

  @Builder.Default
  private List<Channel> channels = new ArrayList<>();
}
