package nifori.me.domain.model.aggregates;

import lombok.Builder;
import lombok.Data;
import nifori.me.domain.model.Channel;
import nifori.me.domain.model.PortObservation;

@Data
@Builder
public class PortObservationAggregate {

  private PortObservation port;
  private Channel channel;

}
