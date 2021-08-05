package nifori.me.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Server {
    private long OID;
    private String name;

    @Builder.Default
    private String prefix = "!";

    @Builder.Default
    private List<Channel> channels = new ArrayList<>();
}
