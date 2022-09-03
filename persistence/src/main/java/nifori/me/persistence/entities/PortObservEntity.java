package nifori.me.persistence.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PORT_OBSERV_T01")
public class PortObservEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PORT_OBSERV_Q01")
  @SequenceGenerator(name = "PORT_OBSERV_Q01", allocationSize = 1)
  private long OID;

  private String channelnametemplate;
  private long channeloid;
  private int port;
  private int lastcount;
}
