package nifori.me.persistence.nifobot.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "REACTIONS_T01")
public class ReactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REACTIONS_Q01")
  @SequenceGenerator(name = "REACTIONS_Q01", allocationSize = 1)
  private long OID;

  private long serveroid;
  private long messageoid;
  private String reactionid;
  private long roleid;

}
