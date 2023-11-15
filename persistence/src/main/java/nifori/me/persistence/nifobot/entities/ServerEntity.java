package nifori.me.persistence.nifobot.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SERVER_T01")
public class ServerEntity {

  @Id
  private Long OID;

  private String servername;
  private String prefix;

}
