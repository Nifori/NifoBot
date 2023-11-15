package nifori.me.nifobot.playernames;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "playernames")
public class PlayernamesConfiguration {
    private boolean enabled;
    private int refresh_rate;
    private List<String> admins;
}
