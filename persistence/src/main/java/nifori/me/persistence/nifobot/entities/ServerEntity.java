package nifori.me.persistence.nifobot.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
