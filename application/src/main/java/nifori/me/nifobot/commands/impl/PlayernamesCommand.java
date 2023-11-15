package nifori.me.nifobot.commands.impl;

import nifori.me.domain.model.PlayerObservation;
import nifori.me.persistence.nifobot.services.PlayerObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.commands.Command;
import nifori.me.nifobot.playernames.PlayerNamesApplicationService;
import nifori.me.nifobot.playernames.PlayernamesConfiguration;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@ConditionalOnProperty("playernames.enabled")
public class PlayernamesCommand extends Command {

  @Autowired
  private PlayernamesConfiguration playernamesConfiguration;

  @Autowired
  private PlayerNamesApplicationService playerNamesApplicationService;

  @Autowired
  private PlayerObservationService playerObservationService;

  public PlayernamesCommand() {
    super.trigger = "playernames";
  }

  @Override
  public void run(MessageCreateEvent event) {

    final MessageChannel channel = event.getMessage()
        .getChannel()
        .block();

    if (playernamesConfiguration.getAdmins()
        .contains(event.getMember()
            .get()
            .getTag())) {

      String[] messageArguments = event.getMessage()
          .getContent()
          .split(" ");

      String subCommand = messageArguments[1].toLowerCase();

      if (subCommand.contains("check")) {
        executeCheck(channel);
      } else if (subCommand.contains("observ")) {
        executeObserv(channel);
        // } else if (subCommand.contains("update")) {
        // updater.update();
      } else {
        channel.createMessage(subCommand + " is no valid command. It has to be \"check\"")
            .onErrorResume(e -> Mono.empty())
            .subscribe();
      }

    } else {
      channel.createMessage("You do not have the permissions to use this command")
          .onErrorResume(e -> Mono.empty())
          .subscribe();
    }
  }

  private void executeObserv(MessageChannel messageChannel) {
    messageChannel.createMessage(playerNamesApplicationService.createMessage())
        .onErrorResume(e -> Mono.empty())
        .subscribe((msg) -> {
          long messageId = msg.getId()
              .asLong();
          long channelId = messageChannel.getId()
              .asLong();
          playerObservationService.savePlayerObservation(PlayerObservation.builder()
              .channeloid(channelId)
              .messageoid(messageId)
              .build());
        });
  }

  private void executeCheck(MessageChannel messageChannel) {
    messageChannel.createMessage(playerNamesApplicationService.createMessage())
        .onErrorResume(e -> Mono.empty())
        .subscribe();
  }

  @Override
  public boolean isEnabled() {
    return playernamesConfiguration.isEnabled();
  }
}
