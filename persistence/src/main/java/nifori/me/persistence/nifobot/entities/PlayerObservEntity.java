package nifori.me.persistence.nifobot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PLAYER_OBSERV_T01")
public class PlayerObservEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAYER_OBSERV_Q01")
    @SequenceGenerator(name = "PLAYER_OBSERV_Q01", allocationSize = 1)
    private long OID;

    private long channeloid;
    private long messageoid;
}