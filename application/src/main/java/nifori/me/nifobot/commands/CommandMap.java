package nifori.me.nifobot.commands;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import nifori.me.nifobot.commands.impl.CommandsCommand;
import org.togglz.core.manager.FeatureManager;

import static java.util.Objects.isNull;

@Component
public class CommandMap extends HashMap<String, Command> {

  public CommandMap(List<? extends Command> commands, FeatureManager featureManager) {
    commands.stream()
        .filter(command -> isNull(command.getFeature()) || featureManager.isActive(command.getFeature()))
        .forEach(command -> {
          this.put(command.getTrigger(), command);
        });

    commands.stream()
        .filter(command -> command.getTrigger()
            .equals("commands"))
        .findAny()
        .ifPresent(command -> ((CommandsCommand) command).setCommands(this));
  }
}
