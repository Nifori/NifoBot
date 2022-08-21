package nifori.me.persistence.entities;

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
@Table(name = "CHANNEL_T01")
public class ChannelEntity {

  @Id
  private Long OID;

  private String channelname;

  private Long serveroid;

}
