package nifori.me.nifobot.commands;

import lombok.Getter;

@Getter
public abstract class Command {
    protected String command;

    public abstract void run();
}