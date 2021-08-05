package nifori.me.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private long OID;
    private String name;
}
