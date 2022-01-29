package nifori.me.nifobot.EventHandler;

import nifori.me.nifobot.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.extern.log4j.Log4j2;
import nifori.me.nifobot.commands.CommandMap;
import nifori.me.persistence.services.ServerService;

@Component
@Log4j2
public class MessageCreateEventHandler {

    @Autowired
    private CommandMap commandMap;

    @Autowired
    private ServerService serverService;

    public void handleEvent(MessageCreateEvent event) {
        if (event.getMember()
                .isEmpty()
                || event.getMember()
                .get()
                .isBot())
            return;

        var content = event.getMessage()
                .getContent();

        String prefix = serverService.getPrefixById(event.getGuildId()
                .get()
                .asLong());

        if (content.startsWith(prefix)) {
            var trigger = getTrigger(event.getMessage()
                    .getContent(), prefix);
            Command command = commandMap.get(trigger);
            if (command != null) {
                command.run(event);
            }
        }
    }

    public String getTrigger(String messageContent, String prefix) {
        var beginIndex = prefix.length();
        var endIndex = messageContent.indexOf(" ");
        var trigger = messageContent.substring(beginIndex, endIndex != -1 ? endIndex : messageContent.length());
        return trigger;
    }

}
