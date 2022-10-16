package nifori.me.nifobot.ports;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "portobservation")
public class PortConfiguration {
    private boolean enabled;
    private int refresh_rate;
    private List<String> admins;
}
