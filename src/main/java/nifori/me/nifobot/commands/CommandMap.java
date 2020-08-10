package nifori.me.nifobot.commands;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CommandMap extends HashMap<String, Command> {
    public CommandMap(List<? extends Command> commands) {
        commands.forEach(command -> {
            this.put(command.getCommand(), command);
        });
    }
}