package nifori.me.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SERVER_T01")
public class ServerEntity {

    @Id
    @GeneratedValue
    @SequenceGenerator(name = "SERVER_Q01")
    private Long OID;

    private String servername;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ChannelEntity> channels;

    
}
