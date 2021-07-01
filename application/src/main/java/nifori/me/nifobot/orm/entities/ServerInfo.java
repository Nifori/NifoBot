package nifori.me.nifobot.orm.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerInfo {
    @Id
    private long serverid;
    
    private String prefix;
    private boolean useTags;
}