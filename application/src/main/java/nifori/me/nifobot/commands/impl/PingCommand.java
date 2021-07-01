package nifori.me.nifobot.commands.impl;

import org.springframework.stereotype.Component;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Permission;
import nifori.me.nifobot.commands.Command;

@Component
public class PingCommand extends Command {

    public PingCommand() {
        super.trigger = "ping";
    }

    @Override
    public void run(MessageCreateEvent event) {
        final MessageChannel channel = event.getMessage().getChannel().block();
        event.getMember().get().getRoles().toStream().forEach(role -> {
            System.out.println(role);
            role.getPermissions().forEach(perm -> System.out.println(perm));
        });
        
        if(event.getMember().get().getRoles().toStream().anyMatch(role -> role.getPermissions().contains(Permission.ADMINISTRATOR))){
            channel.createMessage("What can I do, Sir?").block();
        }
        else {
            channel.createMessage("Pong!").block();    
        }

    }
}