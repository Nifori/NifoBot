package nifori.me.nifobot.commands.impl;

import nifori.me.nifobot.commands.Command;

public class PingCommand extends Command {

    private String command = "ping";

    @Override
    public void run() {
        System.out.println("Ping!");
    }
    
}