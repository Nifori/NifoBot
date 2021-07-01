package nifori.me.persistence.entities;

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
@Table(name = "CHANNEL_T01")
public class ChannelEntity {

    @Id
    @GeneratedValue
    @SequenceGenerator(name = "CHANNEL_Q01")
    private Long OID;

    private String channelname;

}
