package nifori.me.nifobot.commands.impl;

import org.springframework.stereotype.Component;

import discord4j.core.event.domain.message.MessageCreateEvent;
import nifori.me.nifobot.commands.Command;

@Component
public class RepostLiveCommand extends Command {

  public RepostLiveCommand() {
    super.trigger = "live";
  }

  @Override
  public void run(MessageCreateEvent event) {
    System.out.println("Live!");
  }

}
