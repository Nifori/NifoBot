package nifori.me.nifobot.commands;

public abstract class Command {
    protected String command;

    public abstract void run();

    public String getCommand() {
        return command;
    }
}