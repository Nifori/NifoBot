package nifori.me.nifobot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;

public abstract class Command {
    protected String trigger;

    public abstract void run(MessageCreateEvent event);

    public String getTrigger() {
        return trigger;
    }
}