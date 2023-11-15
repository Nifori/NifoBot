package nifori.me.datamodel.checkpoint;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "DATAMODEL_CHECKPOINT_T01")
public class Checkpoint {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATAMODEL_CHECKPOINT_Q01")
  @SequenceGenerator(name = "DATAMODEL_CHECKPOINT_Q01", allocationSize = 1)
  private long oid;
  @Column(name = "scriptoid")
  private String scriptOid;
  @Column(name = "executiontime")
  private Timestamp executionTime;

  public Checkpoint(String scriptOid, Timestamp executionTime) {
    this.scriptOid = scriptOid;
    this.executionTime = executionTime;
  }

}
