package nifori.me.nifobot.commands;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import nifori.me.nifobot.commands.impl.CommandsCommand;

@Component
public class CommandMap extends HashMap<String, Command> {

  public CommandMap(List<? extends Command> commands) {
    commands.forEach(command -> {
      this.put(command.getTrigger(), command);
    });

    commands.stream()
        .filter(command -> command.getTrigger()
            .equals("commands"))
        .findAny()
        .ifPresent(command -> ((CommandsCommand) command).setCommands(this));
  }
}
