package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Server {
    private long OID;
    private String servername;
    private List<Channel> channels;

}
