package nifori.me.nifobot.commands.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.commands.Command;
import nifori.me.nifobot.commands.CommandMap;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class CommandsCommand extends Command {

  @Setter
  private CommandMap commands;

  public CommandsCommand() {
    super.trigger = "commands";
  }

  @Override
  public void run(MessageCreateEvent event) {
    final MessageChannel channel = event.getMessage()
        .getChannel()
        .block();
    channel.createMessage(StringUtils.collectionToDelimitedString(commands.keySet()
        .stream()
        .sorted(Comparator.naturalOrder())
        .collect(Collectors.toList()), "\n"))
        .subscribe();
  }

}
