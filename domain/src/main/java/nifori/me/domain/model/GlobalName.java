package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalName {

  private String userId;

  private String name;

}
