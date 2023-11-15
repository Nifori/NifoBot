package nifori.me.persistence.globalname.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "names")
public class GlobalNameEntity {

  @Id
  private String userId;

  private String name;

  private int nameType;

}
