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
@Table(name = "WARNING_T01")
public class WarningEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WARNING_Q01")
  @SequenceGenerator(name = "WARNING_Q01", allocationSize = 1)
  private long OID;

  private long useroid;
  private long serveroid;
  private String message;

}
